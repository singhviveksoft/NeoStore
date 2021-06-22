package com.example.neosoftassignmentproject.constants.utils

import android.content.Context
import android.graphics.Bitmap
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Matcher
import java.util.regex.Pattern

class Validation {
    companion object {


        fun String.isCharValid(string: String): Boolean {
            val name = string.toString().trim()
            if (!TextUtils.isEmpty(name)) {
                if (name.isNullOrBlank() || name.length <= 3) {

                    return false
                } else {
                    return true
                }


            }


            return false

        }





        fun String.isCharactValid(string: String): Boolean {
            val name = string.toString().trim()
            if (!TextUtils.isEmpty(name)) {
                if (name.isNullOrBlank()) {

                    return false
                } else {
                    return true
                }


            }


            return false

        }



        fun String.isZipValid(string: String): Boolean {
            val name =  string.toString().trim()
            if (!TextUtils.isEmpty(name)) {
                if (name.isNullOrBlank() || name.length < 6) {

                    return false
                } else {
                    return true
                }


            }


            return false

        }

        fun String.isMobileValid(string: String): Boolean {
            val mob = string.toString().trim()

            if (!TextUtils.isEmpty(mob)) {
                if (mob.isNullOrEmpty() || mob.length < 10) {
                    return false
                } else {
                    return true
                }

            }



            return false
        }

        fun String.isimageSelected(bitmap: Bitmap): Boolean {
            val bitmap = bitmap.toString()
            val image = bitmap
            if (!TextUtils.isEmpty(image)) {
                return true
            }
            return false
        }

        fun String.isEmailId(string: String): Boolean {

            val email_id =  string.toString().trim()
            if (!TextUtils.isEmpty(email_id)) {
                return Patterns.EMAIL_ADDRESS.matcher(email_id).matches()
            }

            return false
        }

        fun String.isValidPassword(string: String): Boolean {
            val password = string.toString().trim()
            if (!TextUtils.isEmpty(password)) {
                val pattern: Pattern
                val matcher: Matcher
                val specialCharacters = "-@%\\[\\}+'!/#$^?:;,\\(\"\\)~`.*=&\\{>\\]<_"
                val PASSWORD_REGEX =
                    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$specialCharacters])(?=\\S+$).{8,20}$"
                pattern = Pattern.compile(PASSWORD_REGEX)
                matcher = pattern.matcher(password)
                return matcher.matches()
            }

            return false
        }

/*
        fun errormsg(context: Context, msg: String,edit: EditText): Boolean {
            edit.error=msg
           // textInputLayout.error=null
           // Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            return false
        }
*/
/*
        fun errormsgcheck(context: Context, msg: String): Boolean {
         //   textInputLayout.error=msg

            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            return false
        }
*/

    }


}