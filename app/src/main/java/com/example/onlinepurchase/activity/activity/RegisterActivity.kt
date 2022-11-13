package com.example.onlinepurchase.activity.activity

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinepurchase.activity.data.User
import com.example.onlinepurchase.databinding.ActivityRegisterBinding
import java.io.ByteArrayOutputStream

class RegisterActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    // Asking permission to take a picture
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        granted->onPermissionResult(granted)
    }

    // Taking a picture
    private val openCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data!!
            // -90°C rotation of the picture

            var bitmap = data.extras!!.get("data") as Bitmap
            //bitmap = rotateBitmap(bitmap,-90f)
            binding.imageViewPicture.setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionTakePicture.setOnClickListener {
            //Ask for camera permission
            requestPermission.launch(Manifest.permission.CAMERA)
        }

        binding.signIn.setOnClickListener {
            val userFirstName = binding.userfirstname.text.toString()
            val userLastName = binding.userlastname.text.toString()
            val userPhone = binding.userphone.text.toString()
            val userAddress = binding.useraddress.text.toString()
            val userPassword = binding.password.text.toString()
            val userPassword2 = binding.password2.text.toString()
            val userEmail = binding.useremail.text.toString()
            val bitmap = (binding.imageViewPicture.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val userPicture = stream.toByteArray()

            if(userFirstName.isNotBlank() && userLastName.isNotBlank() && userPhone.isNotBlank() && userAddress.isNotBlank() && userPassword.isNotBlank() && userPassword2.isNotBlank()) {
                if( userPassword!=userPassword2) {
                    Toast.makeText(baseContext, "The passwords doesn't match", Toast.LENGTH_LONG).show()
                } else {
                    val user = User(
                        firstName = userFirstName,
                        lastName = userLastName,
                        phone = userPhone,
                        address = userAddress,
                        password = userPassword,
                        email = userEmail,
                        picture = userPicture
                    )
                    Toast.makeText(baseContext, "$user", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(baseContext, "You need to fill all the fields", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onPermissionResult(result: Boolean) {
        if(result) {
            binding.permissionResult.text = "Granted"
            // If permission Granted -> We take the picture
            openCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        } else{
            binding.permissionResult.text = "Not Granted"
        }
    }

    private fun rotateBitmap(source: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height, matrix, true
        )
    }
}