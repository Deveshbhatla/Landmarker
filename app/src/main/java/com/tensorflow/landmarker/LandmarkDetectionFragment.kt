package com.tensorflow.landmarker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.tensorflow.landmarker.databinding.FragmentLandmarkDetectionBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

class LandmarkDetectionFragment : Fragment() {
    companion object {
        private const val TAG = "Image Classifier"
    }
    private var _fragmentLandmarkDetectionBinding: FragmentLandmarkDetectionBinding? = null
    private val fragmentLandmarkDetectionBinding
        get() = _fragmentLandmarkDetectionBinding!!

    override fun onResume() {
        super.onResume()

        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(
                requireActivity(), R.id.fragment_container
            ).navigate(LandmarkDetectionFragmentDirections.actionLandmarkToPermissions())
        }
    }
    override fun onDestroyView() {
        _fragmentLandmarkDetectionBinding = null
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentLandmarkDetectionBinding =
            FragmentLandmarkDetectionBinding.inflate(inflater, container, false)

        return fragmentLandmarkDetectionBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Classifications
        val landmarkClassificationNorthAmericaCardviewButton = fragmentLandmarkDetectionBinding.LandmarkDetectionNorthAmericaCardview
        landmarkClassificationNorthAmericaCardviewButton.setOnClickListener()
        {
            Log.d("landmarkClassificationNorthAmericaCardviewButton: ", "Selected")
            val intent = Intent (activity, LandmarkDetectionActivity::class.java)
            val b = Bundle()
            b.putInt("NorthAmerica", 1) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

            //val intent = Intent (getActivity(), NorthAmericaLandmarkClassificationActivity::class.java)
            //startActivity(intent)

        }

        val landmarkClassificationSouthAmericaCardviewButton = fragmentLandmarkDetectionBinding.LandmarkDetectionSouthAmericaCardview
        landmarkClassificationSouthAmericaCardviewButton.setOnClickListener()
        {
            Log.d("landmarkClassificationSouthAmericaCardviewButton: ", "Selected")

            val intent = Intent (getActivity(), LandmarkDetectionActivity::class.java)
            val b = Bundle()
            b.putInt("SouthAmerica", 2) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

        }

        val landmarkClassificationAsiaCardviewButton = fragmentLandmarkDetectionBinding.LandmarkDetectionAsiaCardview
        landmarkClassificationAsiaCardviewButton.setOnClickListener()
        {
            Log.d("landmarkClassificationAsiaCardviewButton: ", "Selected")
            val intent = Intent (getActivity(), LandmarkDetectionActivity::class.java)
            val b = Bundle()
            b.putInt("Asia", 3) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

        }



        val landmarkClassificationEuropeCardviewButton = fragmentLandmarkDetectionBinding.LandmarkDetectionEuropeCardview
        landmarkClassificationEuropeCardviewButton.setOnClickListener()
        {
            Log.d("object Detection cardview: ", "Selected")
            val intent = Intent (getActivity(), LandmarkDetectionActivity::class.java)

            val b = Bundle()
            b.putInt("Europe", 4) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

        }

        val landmarkClassificationAfricaCardviewButton = fragmentLandmarkDetectionBinding.LandmarkDetectionAfricaCardview
        landmarkClassificationAfricaCardviewButton.setOnClickListener()
        {
            Log.d("object Detection cardview: ", "Selected")
            val intent = Intent (getActivity(), LandmarkDetectionActivity::class.java)
            val b = Bundle()
            b.putInt("Africa", 5) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

        }

        val landmarkClassificationAntarticaCardviewButton = fragmentLandmarkDetectionBinding.LandmarkDetectionAntarticaCardview
        landmarkClassificationAntarticaCardviewButton.setOnClickListener()
        {
            Log.d("landmarkClassificationAntarticaCardviewButton: ", "Selected")
            val intent = Intent (getActivity(), LandmarkDetectionActivity::class.java)
            val b = Bundle()
            b.putInt("Antarctica", 6) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

        }
    }


}