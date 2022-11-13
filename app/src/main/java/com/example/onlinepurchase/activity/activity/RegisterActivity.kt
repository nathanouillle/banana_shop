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
import androidx.appcompat.content.res.AppCompatResources
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.data.User
import com.example.onlinepurchase.activity.database.user.UserEntity
import com.example.onlinepurchase.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    // Asking permission to take a picture
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            onPermissionResult(granted)
        }

    // Taking a picture
    private val openCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data!!
                // -90°C rotation of the picture

                val bitmap = data.extras!!.get("data") as Bitmap
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

            // Check if the user has entered all the fields
            if (userFirstName.isBlank() || userLastName.isBlank() || userPhone.isBlank() || userAddress.isBlank() || userPassword.isBlank() || userPassword2.isBlank() || userEmail.isBlank()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                // Image compression
                val imageview = binding.imageViewPicture
                val drawable = imageview.drawable
                val bitmap = (drawable as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()

                val user = User(
                    userFirstName,
                    userLastName,
                    userAddress,
                    userPhone,
                    userEmail,
                    userPassword,
                    byteArray
                )
                // Check if the passwords are the same
                if (userPassword == userPassword2) {
                    // Check if the user is already in the database
                    val userDatabase: UserEntity? = runBlocking(Dispatchers.IO) {
                        OnlinePurchase.onlinePurchaseDatabase.userDao().getUserByEmail(userEmail)
                    }
                    if (userDatabase == null) {
                        // Add the user to the database
                        runBlocking {
                            launch(Dispatchers.IO) {
                                OnlinePurchase.onlinePurchaseDatabase.userDao()
                                    .addUser(user.toUserEntity())
                            }
                        }
                        // Go to the login activity
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "This email is already used", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this, "The passwords are not the same", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun onPermissionResult(result: Boolean) {
        if (result) {
            binding.permissionResult.text = "Granted"
            // If permission Granted -> We take the picture
            openCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        } else {
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