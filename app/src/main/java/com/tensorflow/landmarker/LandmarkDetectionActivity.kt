package com.tensorflow.landmarker

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.icu.util.LocaleData
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.View.inflate
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import com.google.common.net.MediaType
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.imageclassifier.ImageClassifier
import com.google.mediapipe.tasks.vision.imageclassifier.ImageClassifierResult
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService


private var imageClassifier: ImageClassifier? = null
private lateinit var backgroundExecutor: ScheduledExecutorService
var modelName :String = "NULL"
private var tmpUri: Uri? = null

class LandmarkDetectionActivity : AppCompatActivity() {
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
                //continentName.setText("North America Continent ")
                modelName = "north_america_landmark.tflite"
                supportActionBar!!.title = "North  America Continent";

                //landmarkDetectorService(modelName)
            }
            else if(b.getInt("SouthAmerica")==2)
            {
                modelName = "south_america_landmark.tflite"
                supportActionBar!!.title ="South America Continent"
                //landmarkDetectorService(modelName)
            }
            else if(b.getInt("Asia")==3)
            {
                modelName = "asia_landmark.tflite"
                supportActionBar!!.title ="Asia Continent"
                //landmarkDetectorService(modelName)
            }
            else if(b.getInt("Europe")==4)
            {
                modelName = "europe_landmark.tflite"
                supportActionBar!!.title ="Europe Continent"

                //landmarkDetectorService(modelName)
            }
            else if(b.getInt("Africa")==5)
            {
                modelName = "africa_landmark.tflite"
                supportActionBar!!.title ="Africa Continent"

                //landmarkDetectorService(modelName)
            }
            else if(b.getInt("Antarctica")==6)
            {
                modelName = "antarctica_landmark.tflite"
                supportActionBar!!.title ="Antartica Continent"

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

                        .setScoreThreshold(0.5F)
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

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setView(R.layout.progress_bar_layout)
            val dialog = dialogBuilder.create()

            dialog.show()
            dialog.setCancelable(false)

            Handler(Looper.getMainLooper()).postDelayed({
                val classifierResult = imageClassifier!!.classify(mpImage)
                Log.d("ClassifierResult",classifierResult.toString())
                updateResults(classifierResult)

                    dialog.dismiss();



            }, 5000)
        }

    }
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
    private fun updateResults(imageClassifierResult: ImageClassifierResult? = null) {
        val landmarkDescription=findViewById<EditText>(R.id.Landmark_Description)
        val shareLandmark=findViewById<Button>(R.id.button_share_landmark)

        if (imageClassifierResult != null) {
            val sortedCategories = imageClassifierResult.classificationResult()
                .classifications()[0].categories().sortedBy { it.index() }

            Log.d("SORTED CATEGORIES: ", sortedCategories.toString())
            if(sortedCategories.isNotEmpty())
            {
                val detectedLandmark=imageClassifierResult.classificationResult().classifications()[0].categories()[0].displayName()
                landmarkDescription.setText(detectedLandmark)
                shareLandmark.isEnabled=true
                Log.d("CONTINENT WITH HIGHEST CONFIDENCE: ", detectedLandmark)
            }
            else
            {
                shareLandmark.isEnabled=true
                landmarkDescription.setText(resources.getString(R.string.no_landmark_detected))
            }

            shareLandmark.setOnClickListener()
            {
                //val bitmapOfDetectedImage = imageViewResult?.getDrawable()
                val bitmapOfDetectedImage = (imageViewResult!!.getDrawable() as BitmapDrawable).getBitmap()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val fileName: String= landmarkDescription.text.toString().plus(LocalDateTime.now().format(formatter))
                Log.d("FILENAME", fileName)
                try {

                    val cachePath: File = File(this.cacheDir, "images")
                    cachePath.mkdirs() // make the directory
                    val stream = FileOutputStream(
                        File(cachePath, "$fileName.png")
                    ) // overwrites this image every time
                    bitmapOfDetectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream   )
                    stream.close()

                } catch (e: IOException) {
                    e.printStackTrace()
                }


                val imagePath: File = File(this.cacheDir, "images")
                val newFile = File(imagePath, "$fileName.png")
                val contentUri = FileProvider.getUriForFile(this, "com.tensorflow.landmarker.provider", newFile)



                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                //shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                this.grantUriPermission("android", contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
                shareIntent.putExtra(Intent.EXTRA_TITLE,landmarkDescription.text.toString())
                shareIntent.type = "image/*"
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                startActivity(Intent.createChooser(shareIntent, "Share image"))
//
//                val progressView: View = layoutInflater.inflate(R.layout.progress_bar_layout,null)
//
//                val dialogBuilder = AlertDialog.Builder(this)
//                dialogBuilder.setView(progressView)
//                val dialog = dialogBuilder.create()
//                val loadingText: TextView= progressView.findViewById(R.id.loading_message)
//                loadingText.text = "Loading.."
//
//                dialog.show()
//
//                Handler(Looper.getMainLooper()).postDelayed({
//                    //startActivity(Intent.createChooser(shareIntent, "Share text..."))
//                    dialog.dismiss()
//
//            },7000)
            }


        }
    }
    override fun onBackPressed() {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

}