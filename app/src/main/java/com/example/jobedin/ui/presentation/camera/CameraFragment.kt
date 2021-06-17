package com.example.jobedin.ui.presentation.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.jobedin.MainActivity
import com.example.jobedin.R
import java.io.File

class CameraFragment : Fragment {

    constructor() {

    }

    val PICK_IMG = 19
    private val CAMERA_REQUEST = 1888
    private var imageView: ImageView? = null
    private val MY_CAMERA_PERMISSION_CODE = 100
    var anim: AnimationDrawable? = null
    var fragContext: Context? = null
    var camera: Camera? = null
    var preview: Preview? = null
    var imageCapture: ImageCapture? = null
    lateinit var cameraFrameLayout: FrameLayout
    lateinit var captureButton: Button
    lateinit var switchCam: String
    lateinit var constraintLayout: ConstraintLayout
    lateinit var curPhoto: Uri
    lateinit var cameraView: PreviewView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragContext = view.context
        val navController: NavController = findNavController()
        view.apply {
            switchCam = "back"
            cameraView = findViewById<PreviewView>(R.id.cameraView)
            cameraFrameLayout = findViewById(R.id.cameraFrameLayout)
            captureButton = findViewById(R.id.captureButton)
            // constraintLayout = findViewById(R.id.imgContainer)


            constraintLayout = findViewById<ConstraintLayout>(R.id.imgContainer)
            anim = constraintLayout.background as AnimationDrawable
            anim?.setEnterFadeDuration(6000)
            anim?.setExitFadeDuration(2000)
            imageView = findViewById(R.id.imageView1)
            val lenseSwich: Button = findViewById(R.id.button1)

            if (PermissionChecker.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PermissionChecker.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    MY_CAMERA_PERMISSION_CODE
                )
            } else {
                startCamera(switchCam)
            }
            findViewById<Button>(R.id.btnSave).setOnClickListener {

                val action = CameraFragmentDirections.actionCameraFragmentToAddPostFragment()


                MainActivity.tempPicPath = curPhoto
                if (getFileExt(view, curPhoto) == null) {
                    MainActivity.tempFileExt = "jpg"
                } else {
                    MainActivity.tempFileExt = getFileExt(view, curPhoto)
                }

                navController.navigate(action)
            }
            findViewById<Button>(R.id.btnCancel).setOnClickListener {
                startCamera(switchCam)
            }

            captureButton.setOnClickListener {
                takePhoto()
                Glide.with(fragContext!!).load(curPhoto).into(imageView!!)
            }

            findViewById<Button>(R.id.btnGallery).setOnClickListener {
                ChooseImage(it)
                constraintLayout.visibility = View.VISIBLE

            }

            lenseSwich.setOnClickListener(View.OnClickListener {
                stopCamera()
                switchCam = if (switchCam == "front") {
                    "back"
                } else {
                    "front"
                }
                startCamera(switchCam)
            })
        }
    }

    open fun ChooseImage(view: View?) {
        val intent = Intent()
        intent.type = "image/* video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMG)
    }

    private fun takePhoto() {
        val photoFile = File(
            fragContext?.externalMediaDirs?.firstOrNull(),
            "insta_clone${System.currentTimeMillis()}.jpg"
        )
        curPhoto = Uri.fromFile(photoFile)
        Log.d("TAG", "takePhoto: " + photoFile.absolutePath.toString())
        val output = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture?.takePicture(
            output,
            ContextCompat.getMainExecutor(fragContext),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    constraintLayout.visibility = View.VISIBLE
                    stopCamera()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(fragContext, "Error Saving", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "\n\n\n onError: $exception")
                }

            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("TAG", "onRequestPermissionsResult: Granted")
                startCamera("back")
            } else {
                Log.d("TAG", "onRequestPermissionsResult: Denied")
            }
        }
    }

    private fun startCamera(lense: String) {
        constraintLayout.visibility = View.GONE
        val cameraProviderFuture = ProcessCameraProvider.getInstance(fragContext!!)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            preview = Preview.Builder()
                .build()
            preview?.setSurfaceProvider(cameraView.surfaceProvider)
            imageCapture = ImageCapture.Builder()
                .setTargetResolution(Size(720, 1280))
                .build()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(720, 1280))
                .build()

            val cameraSelector = CameraSelector.Builder()
            if (switchCam == "back") {
                cameraSelector.requireLensFacing(CameraSelector.LENS_FACING_BACK)
            } else if (switchCam == "front") {
                cameraSelector.requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            }
            // Must unbind the use-cases before rebinding them
            cameraProvider.unbindAll()

            camera =
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector.build(),
                    preview,
                    imageAnalysis,
                    imageCapture
                )
        }, ContextCompat.getMainExecutor(activity))

    }

    private fun stopCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(fragContext!!)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll()
        }, ContextCompat.getMainExecutor(activity))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMG || resultCode == Activity.RESULT_OK || data != null || data?.data != null) {
            try {
                curPhoto = data!!.data!!
                Glide.with(fragContext!!).load(curPhoto).into(imageView!!)
            } catch (e: Exception) {
                startCamera(switchCam)
                Log.d("TAG", "onActivityResult: not selected anything")
            }
        }

    }

    fun getFileExt(view: View, uri: Uri): String? {
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(fragContext!!.contentResolver?.getType(uri))
    }

    override fun onResume() {
        super.onResume()
        if (anim != null && !anim!!.isRunning) anim!!.start()
    }

    override fun onPause() {
        super.onPause()
        if (anim != null && anim!!.isRunning) anim!!.stop()
    }

}