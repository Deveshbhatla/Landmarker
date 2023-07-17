package com.tensorflow.landmarker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation

val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

class PermissionsFragment : Fragment() {


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(context, "Permission request granted", Toast.LENGTH_LONG).show()
                navigateToLandmark()
            } else {
                Toast.makeText(context, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions())
{
      permissions ->
    permissions.entries.forEach {
        Log.d("DEBUG", "${it.key} = ${it.value}")
    }
//    //if permissions are granted then navigate to the landmark fragment
//    val granted = permissions.entries.all {
//        it.value// same as it.value == true
//    }
//    if (granted) {
//        navigateToLandmark()
//    }
//    else
//        Toast.makeText(context,"Permissions denied",Toast.LENGTH_LONG).show()
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        when {
//            PERMISSIONS_REQUIRED.all {
//                ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
//            } -> {
//                navigateToLandmark()
//            }
//            else -> {
//                requestMultiplePermissions.launch(PERMISSIONS_REQUIRED)
//            }
//        }

        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> {
                        navigateToLandmark()
                }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    /** Method to navigate to the LandmarkDetectionFragment */
    private fun navigateToLandmark() {
        lifecycleScope.launchWhenStarted {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                PermissionsFragmentDirections.actionPermissionsToLandmark()
            )
        }
    }


    companion object {

        /** method used to check if all permissions required by this app are granted */
        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}