package com.example.neosoftassignmentproject

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.databinding.FragmentMyaccountBinding
import com.example.neosoftassignmentproject.db.DataBase
import com.example.neosoftassignmentproject.repository.CategoryRepository
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.CateyDBViewModelFactory
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.HomeViewModel
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest


class MyAccountFragment : Fragment(),EasyPermissions.PermissionCallbacks {
private lateinit var binding:FragmentMyaccountBinding
private lateinit var viewModel:HomeViewModel
private val api= Api.getInstance()
    private lateinit var db: DataBase
    private var imageUri: Uri? = null
    private var  bitmap: Bitmap? = null
    private var image:String?="data:image/jpg;base64,"
    private var _image:String?=""

    companion object{


        const val PERMISSION_CODE=0
        const val CAMERA_REQUEST_CODE=1
        const val GALLERY_REQUEST_CODE=2

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMyaccountBinding.inflate(inflater, container, false)
        db= DataBase.getInstance(requireContext())
        viewModel=ViewModelProvider(this, CateyDBViewModelFactory(CategoryRepository(api,db))).get(HomeViewModel::class.java)
          return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        UserPreferences(requireContext()).getAccessToken.asLiveData().observe(viewLifecycleOwner){
            viewModel.getProduct(it) // api call
            viewModel._access_token.value=it

        }

// observer

        viewModel.getProduct.observe(requireActivity(), Observer {
        binding.viewModel=    it.data.user_data
            if (it.data.user_data.profile_pic!= null)   binding.profileImg.load(it.data.user_data.profile_pic!!){
               crossfade(true)
                crossfade(2000)
                transformations(CircleCropTransformation())
            }

        })


//oserver
        viewModel._updateProfile.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "${it.user_msg}", Toast.LENGTH_SHORT).show()
        })

// btn click
        binding.editProfileBtn.setOnClickListener {
            val email=binding.emailEditText.text.toString()
            val phone_no=binding.mobileEditText.text.toString()
            val dob=binding.dobEditText.text.toString()
            val firstName=binding.firstNameTxt.text.toString()
            val lastName=binding.lastNameTxt.text.toString()
            UserPreferences(requireContext()).getAccessToken.asLiveData().observe(requireActivity()){
                viewModel.updateProfile(it,firstName,lastName,email,dob,phone_no,_image!!)   // api call
            }

        }


        binding.resetPwdBtn.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountFragment_to_changePwdFragment)
        }

// user image permission btn click
        binding.profileImg.setOnClickListener {
            requestPermission()
        }

    }

    fun requestPermission() {
        EasyPermissions.requestPermissions(
            this, "camera permission needed", PERMISSION_CODE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA
        )
    }




    private fun alertBox() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose any one")
        builder.setMessage("Upload your photo")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("Camera") { dialog, which ->
            dispatchTakePictureIntent()
        }

        builder.setNegativeButton("Gallery") { dialog, which ->
            openGallery()
        }

        builder.show()

    }


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, MyAccountFragment.CAMERA_REQUEST_CODE)
        } catch (e: Exception) {
            // display error state to the user
        }
    }


    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, GALLERY_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,false)
    }








    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {
                    try {
                        bitmap = data?.extras?.get("data") as Bitmap
                        //  userViewModel.profile_photo.value=bitmap
                        //we are using coroutine image loader (coil)
                        binding.profileImg.load(bitmap) {
                            crossfade(true)
                            crossfade(1000)
                            transformations(CircleCropTransformation())
                        }
                        _image=    image.plus(encodeImage(bitmap!!))
                     //   Toast.makeText(requireContext(), "$image", Toast.LENGTH_SHORT).show()

                    } catch (ex: Exception) {
                    }

                }

                GALLERY_REQUEST_CODE -> {
                    imageUri = data?.data
                    try {

                        bitmap= MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageUri)
                       binding.profileImg.load(data?.data) {
                            crossfade(true)
                            crossfade(1000)

                            transformations(CircleCropTransformation())
                        }
                        _image=    image.plus(  encodeImage(bitmap!!))
                             //  Toast.makeText(requireContext(), "$image", Toast.LENGTH_SHORT).show()

                    }catch (ex:Exception){}


                }
            }

        }


    }


    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
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
        alertBox()
    }


}