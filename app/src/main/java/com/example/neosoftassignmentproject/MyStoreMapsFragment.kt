package com.example.neosoftassignmentproject

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import kotlinx.android.synthetic.main.fragment_my_store_maps.*

class MyStoreMapsFragment : Fragment(), GoogleMap.OnMarkerClickListener,EasyPermissions.PermissionCallbacks {
    private lateinit var lastlocation:Location
    private lateinit var fusedLocation:FusedLocationProviderClient

    private lateinit var mapFragment:SupportMapFragment



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_store_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        if (hasLocationPermission()) {
            mapFragment?.getMapAsync(callback)
            fusedLocation=LocationServices.getFusedLocationProviderClient(requireContext())

        }else{
            requestPermission()
            fusedLocation=LocationServices.getFusedLocationProviderClient(requireContext())

        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        return false
    }


    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.uiSettings.isZoomControlsEnabled=true
        //   val sydney = LatLng(-34.0, 151.0)
        googleMap.setOnMarkerClickListener(this)
        setUpMap(googleMap)
        //  googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


    private fun setUpMap(googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
           // requestPermission()

            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),1)
            return
        }
        googleMap.isMyLocationEnabled=true
        fusedLocation.lastLocation.addOnSuccessListener {
            if (it!=null) {
                lastlocation =it
                val currentLatLong=LatLng(it.latitude,it.longitude)
                val marker=MarkerOptions().position(currentLatLong)
                marker.title("$currentLatLong")
                googleMap.addMarker(marker)

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,12F))
            }
        }

    }

    private fun hasLocationPermission()=
        EasyPermissions.hasPermissions(
            requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)



    private fun requestPermission(){
        EasyPermissions.requestPermissions(this,"you need loction permission ",1,
            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionDenied(this,perms.first())){
            SettingsDialog.Builder(requireContext()).build().show()
        }
        else{
            requestPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        mapFragment?.getMapAsync(callback)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,false)
        mapFragment?.getMapAsync(callback)

    }


}