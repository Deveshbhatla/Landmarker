package com.tensorflow.landmarker

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Lifecycle
import com.google.common.net.MediaType
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.imageclassifier.ImageClassifier
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService


private var imageClassifier: ImageClassifier? = null
const val OTHER_ERROR = 0
val REQUEST_CODE = 100
private lateinit var backgroundExecutor: ScheduledExecutorService
var modelName :String = "NULL"
private var tmpUri: Uri? = null

class LandmarkDetectionActivity : AppCompatActivity() {
    var continentId=0 // or other values
    var imageViewResult: ImageView? =null

    enum class MediaType {
        IMAGE, UNKNOWN
    }
    private val getContent =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            // Handle the returned Uri
            uri?.let { mediaUri ->
                when (val mediaType = loadMediaType(mediaUri)) {
                    MediaType.IMAGE -> landmarkDetectorService(modelName,mediaUri)
                    MediaType.UNKNOWN -> {
                        updateDisplayView(mediaType)
                        Toast.makeText(
                            this,
                            "Unsupported data type.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    private val getContentCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess: Boolean ->
            // Handle the returned Uri
            if(isSuccess)
            {
                tmpUri?.let { mediaUri ->
                    when (val mediaType = loadMediaType(mediaUri)) {
                        MediaType.IMAGE -> landmarkDetectorService(modelName,mediaUri)
                        MediaType.UNKNOWN -> {
                            updateDisplayView(mediaType)
                            Toast.makeText(
                                this,
                                "Unsupported data type.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmark_detection)
        imageViewResult = findViewById<ImageView>(R.id.image_view)

        val continentName: TextView = findViewById<TextView>(R.id.continent_name)
        val cameraSelectButton = findViewById<Button>(R.id.button_select_image_camera)

        val gallerySelectbutton = findViewById<Button>(R.id.button_select_image_gallery);
        gallerySelectbutton.setOnClickListener{
            getContent.launch(arrayOf("image/*"))
            updateDisplayView(MediaType.UNKNOWN)

            Log.d("GALLERY BUTTON",": SELECTED")
        }

        cameraSelectButton.setOnClickListener{

            getTmpFileUri().let { uri ->
                tmpUri = uri
                getContentCamera.launch(uri)
            }

            updateDisplayView(MediaType.UNKNOWN)
            Log.d("CAMERA BUTTON",": SELECTED")

        }


        val b = intent.extras
        if (b != null)
        {
            if (b.getInt("NorthAmerica")==1)
            {
                continentName.setText("North America Continent ")
                modelName = "north_america_landmark.tflite"
                //landmarkDetectorService(modelName)
            }
            else if(b.getInt("SouthAmerica")==2)
            {
                continentName.setText("South America Continent ")
                modelName = "south_america_landmark.tflite"
                //landmarkDetectorService(modelName)
            }
            else if(b.getInt("Asia")==3)
            {
                continentName.setText("Asia Continent ")
                modelName = "asia_landmark.tflite"
                //landmarkDetectorService(modelName)
            }
            else if(b.getInt("Europe")==4)
            {
                continentName.setText("Europe Continent")
                modelName = "europe_landmark.tflite"
                //landmarkDetectorService(modelName)
            }
            else if(b.getInt("Africa")==5)
            {
                continentName.setText("Africa Continent")
                modelName = "africa_landmark.tflite"
                //landmarkDetectorService(modelName)
            }
            else if(b.getInt("Antarctica")==6)
            {
                continentName.setText("Antartica Continent")
                modelName = "antarctica_landmark.tflite"
                //landmarkDetectorService(modelName)
            }

        }


    }
    private fun loadMediaType(uri: Uri): MediaType {
        val mimeType = this?.contentResolver?.getType(uri)
        mimeType?.let {
            if (mimeType.startsWith("image")) return MediaType.IMAGE
        }

        return MediaType.UNKNOWN
    }
    private fun landmarkDetectorService(modelName:String,uri: Uri)
    {
        backgroundExecutor = Executors.newSingleThreadScheduledExecutor()

        updateDisplayView(MediaType.IMAGE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(
                this.contentResolver, uri
            )
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(
                this.contentResolver, uri
            )
        }.copy(Bitmap.Config.ARGB_8888, true)?.let { bitmap ->
            imageViewResult?.setImageBitmap(bitmap)

            Log.d("BITMAP:: ",bitmap.toString())

            val baseOptionsBuilder = BaseOptions.builder()
            baseOptionsBuilder.setDelegate(Delegate.CPU)

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            Log.d(" C DATE is  ", currentDate)
            backgroundExecutor.execute {
                baseOptionsBuilder.setModelAssetPath(modelName)
                val optionsBuilder =
                    ImageClassifier.ImageClassifierOptions.builder()

                        .setScoreThreshold(0.7F)
                        .setRunningMode(RunningMode.IMAGE)
                        .setMaxResults(3)
                        .setBaseOptions(
                            baseOptionsBuilder.build()
                        )

                val options = optionsBuilder.build()

                imageClassifier = ImageClassifier.createFromOptions(this, options)

                val sdf1 = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val newDate = sdf1.format(Date())
                Log.d(" New Date and time  ", newDate)
            }
            val mpImage = BitmapImageBuilder(bitmap).build()

            Handler(Looper.getMainLooper()).postDelayed({
                val classifierResult = imageClassifier!!.classify(mpImage)
                Log.d("ClassifierResult",classifierResult.toString())

            }, 5000)
        }
//        gallerySelectbutton.setOnClickListener()
//        {
//            //opengallery()
//            Log.d("Gallery selected Button Clicked: ", "Selected")
//
//        }
    }

//    fun opengallery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, REQUEST_CODE)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
//            imageViewResult?.setImageURI(data?.data) // handle chosen image
//        }
//        val newUri = Uri.parse(data?.data.toString())
//        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, newUri)
//        val mpImage = BitmapImageBuilder(bitmap).build()
//
//        val classifierResult = imageClassifier!!.classify(mpImage)
//        Log.d("ClassifierResult",classifierResult.toString())
//
//    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)

    }
    private fun updateDisplayView(mediaType: MediaType) {
        imageViewResult?.visibility ?:
            if (mediaType == MediaType.IMAGE) View.VISIBLE else View.GONE

        imageViewResult?.visibility ?:
            if (mediaType == MediaType.UNKNOWN) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

}