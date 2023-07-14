package com.tensorflow.landmarker

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tensorflow.landmarker.databinding.ActivityMainBinding


//val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView;
    private val REQUEST_CODE = 1
    private lateinit var activityMainBinding: ActivityMainBinding


//    val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
//                navigateToLandmarkDetection()
//
//                // Permission is granted. Continue the action or workflow in your
//                // app.
//            } else {
//                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
//
//                // Explain to the user that the feature is unavailable because the
//                // feature requires a permission that the user has denied. At the
//                // same time, respect the user's decision. Don't link to system
//                // settings in an effort to convince the user to change their
//                // decision.
//            }
//        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //bottomNav = findViewById(R.id.bottomNav)
        //checkMultiplePermissions();

        //permissionLauncherMultiple.launch(PERMISSIONS_REQUIRED)


//        when {
//            PERMISSIONS_REQUIRED.all {
//                ContextCompat.checkSelfPermission(this, it) ==PackageManager.PERMISSION_GRANTED}
//            -> {
//                // You can use the API that requires the permission.
//                //navigateToLandmarkDetection()
//            }
//
//            else -> {
//                // You can directly ask for the permission.
//                requestPermissions(
//                    PERMISSIONS_REQUIRED,
//                    REQUEST_CODE)
//            }
//        }

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        activityMainBinding.navigation.setupWithNavController(navController)
        activityMainBinding.navigation.setOnClickListener() {

            // ignore the reselection
        }
    }
//    override fun onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            REQUEST_CODE -> {
//                // If request is cancelled, the result arrays are empty.
//                if ((grantResults.isNotEmpty() &&
//                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    // Permission is granted. Continue the action or workflow
//                    // in your app.
//                    navigateToLandmarkDetection()
//                } else {
//                    // Explain to the user that the feature is unavailable because
//                    // the feature requires a permission that the user has denied.
//                    // At the same time, respect the user's decision. Don't link to
//                    // system settings in an effort to convince the user to change
//                    // their decision.
//                    Toast.makeText(this, "All or some permissions denied...", Toast.LENGTH_SHORT).show()
//                    loadFragment(AboutFragment())
//
//                }
//
//                return
//            }
//
//            // Add other 'when' lines to check for other
//            // permissions this app might request.
//            else -> {
//                // Ignore all other requests.
//            }
//        }
//    }




//    private val permissionLauncherMultiple = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { result ->
//        //here we will check if permissions were now (from permission request dialog) or already granted or not
//
//        var allAreGranted = true
//        for (isGranted in result.values) {
//            Log.d(TAG, "onActivityResult: isGranted: $isGranted")
//            allAreGranted = allAreGranted && isGranted
//        }
//
//        if (allAreGranted) {
//            //All Permissions granted now do the required task here or call the function for that
//            multiplePermissionsGranted()
//        } else {
//            //All or some Permissions were denied so can't do the task that requires that permission
//            loadFragment(AboutFragment())
//            //unLoadFragment(LandmarkDetectionFragment())
//
//            Log.d(TAG, "onActivityResult: All or some permissions denied...")
//            Toast.makeText(this, "All or some permissions denied...", Toast.LENGTH_SHORT).show()
//        }
//    }
//    private fun multiplePermissionsGranted()
//    {
//        navigateToLandmarkDetection()
//    }

//    private fun checkMultiplePermissions() {
//
//        val permissionsNeeded: MutableList<String> = ArrayList()
//        val permissionsList: MutableList<String> = ArrayList()
//        if (!addPermission(permissionsList, Manifest.permission.CAMERA)) {
//            permissionsNeeded.add("Camera")
//        }
//        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            permissionsNeeded.add("Read Storage")
//        }
//        if (permissionsList.size > 0) {
//            requestPermissions(
//                permissionsList.toTypedArray(),
//                REQUEST_CODE
//            )
//            return
//        }
//        navigateToLandmarkDetection()
//    }
//    private fun addPermission(permissionsList: MutableList<String>, permission: String): Boolean {
//        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
//            permissionsList.add(permission)
//
//            // Check for Rationale Option
//            if (!shouldShowRequestPermissionRationale(permission))
//                return false
//        }
//        return true
//    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            REQUEST_CODE -> {
//                val perms: MutableMap<String, Int> = HashMap()
//                // Initial
//                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
//                perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
//
//                // Fill with results
//                var i = 0
//                while (i < permissions.size) {
//                    perms[permissions[i]] = grantResults[i]
//                    i++
//                }
//                if (perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
//                    && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
//                ) {
//                    navigateToLandmarkDetection()
//                    // All Permissions Granted
//                    return
//                } else {
//                    // Permission Denied
//                    Toast.makeText(
//                        applicationContext,
//                        """
//                        Landmarker cannot run without Camera and Storage Permissions.
//                        """.trimIndent(),
//                        Toast.LENGTH_LONG
//                    ).show()
//                    AlertDialog.Builder(this)
//                        .setTitle("Permission Needed")
//                        .setMessage("Kindly give camera and Storage Permissions")
//                        .setPositiveButton("OK"
//                            ) { dialog, which ->
//
//                            }
//                            .setNegativeButton("Cancel"
//                            ) { dialog, which ->
//                                run {
//                                    dialog.dismiss()
//                                    finish()
//                                }
//                            }.create()
//                        .show()
//                    //finish()
//                }
//            }
//            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        }
//    }

//    private fun navigateToLandmarkDetection()
//    {
//        loadFragment(LandmarkDetectionFragment())
//
//        bottomNav.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.landmarkDetection -> {
//                    loadFragment(LandmarkDetectionFragment())
//                    true
//                }
//                R.id.aboutInfo -> {
//                    loadFragment(AboutFragment())
//                    true
//                }
//
//                else -> {true}
//            }
//
//        }
//
//    }
//
//    private  fun loadFragment(fragment: Fragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragment_container, fragment)
//        transaction.commit()
//    }
//    private fun unLoadFragment(fragment: Fragment)
//    {
//        val transaction = supportFragmentManager.beginTransaction()
//
//    }

    override fun onBackPressed() {
        finish()
    }

}