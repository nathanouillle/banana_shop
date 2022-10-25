package com.example.onlinepurchase.activity

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinepurchase.databinding.ActivityRegisterBinding

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
            bitmap = rotateBitmap(bitmap,-90f)
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