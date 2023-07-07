package com.tensorflow.landmarker

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.imageclassifier.ImageClassifier
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService


private var imageClassifier: ImageClassifier? = null
const val OTHER_ERROR = 0
val REQUEST_CODE = 100
var imageViewGallery: ImageView? =null
private lateinit var backgroundExecutor: ScheduledExecutorService

class LandmarkDetectionActivity : AppCompatActivity() {
    var continentId=0 // or other values


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmark_detection)
        val continentName: TextView = findViewById<TextView>(R.id.continent_name)

        val b = intent.extras
        var modelName :String
        if (b != null)
        {
            if (b.getInt("NorthAmerica")==1)
            {
                continentName.setText("North America Continent ")
                modelName = "north_america_landmark.tflite"
                landmarkDetectorService(modelName)
            }
            else if(b.getInt("SouthAmerica")==2)
            {
                continentName.setText("South America Continent ")
                modelName = "south_america_landmark.tflite"
                landmarkDetectorService(modelName)
            }
            else if(b.getInt("Asia")==3)
            {
                continentName.setText("Asia Continent ")
                modelName = "asia_landmark.tflite"
                landmarkDetectorService(modelName)
            }
            else if(b.getInt("Europe")==4)
            {
                continentName.setText("Europe Continent")
                modelName = "europe_landmark.tflite"
                landmarkDetectorService(modelName)
            }
            else if(b.getInt("Africa")==5)
            {
                continentName.setText("Africa Continent")
                modelName = "africa_landmark.tflite"
                landmarkDetectorService(modelName)
            }
            else if(b.getInt("Antarctica")==6)
            {
                continentName.setText("Antartica Continent")
                modelName = "antarctica_landmark.tflite"
                landmarkDetectorService(modelName)
            }

        }


    }

    private fun landmarkDetectorService(ModelName: String)
    {
        backgroundExecutor = Executors.newSingleThreadScheduledExecutor()

        val baseOptionsBuilder = BaseOptions.builder()
        baseOptionsBuilder.setDelegate(Delegate.CPU)

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        Log.d(" C DATE is  ",currentDate)
        backgroundExecutor.execute {

            baseOptionsBuilder.setModelAssetPath(ModelName)
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
        imageViewGallery = findViewById<ImageView>(R.id.image_view)
        val selectbutton = findViewById<Button>(R.id.button_select_image);

        selectbutton.setOnClickListener()
        {
            opengallery()
            Log.d("Button Clicked: ", "Selected")

        }
        //val image: Bitmap = BitmapFactory.decodeFile(assets)

        //Log.d("IMAGE:", R.drawable.sampleimg.toString());
    }
    fun opengallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imageViewGallery?.setImageURI(data?.data) // handle chosen image
        }
        val newUri = Uri.parse(data?.data.toString())
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, newUri)
        val mpImage = BitmapImageBuilder(bitmap).build()

        val classifierResult = imageClassifier!!.classify(mpImage)
        Log.d("ClassifierResult",classifierResult.toString())

    }
    override fun onBackPressed() {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

}