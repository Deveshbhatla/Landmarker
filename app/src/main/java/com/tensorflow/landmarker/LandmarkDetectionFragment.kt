package com.tensorflow.landmarker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LandmarkDetectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LandmarkDetectionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_landmark_detection, container, false)
        //  view.findViewById<Button>(R.id.button1).setOnClickListener()

        //Classifications
        val landmarkClassificationNorthAmericaCardviewButton = view.findViewById<CardView>(R.id.LandmarkDetectionNorthAmericaCardview);
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

        val landmarkClassificationSouthAmericaCardviewButton = view.findViewById<CardView>(R.id.LandmarkDetectionSouthAmericaCardview);
        landmarkClassificationSouthAmericaCardviewButton.setOnClickListener()
        {
            Log.d("landmarkClassificationSouthAmericaCardviewButton: ", "Selected")

            val intent = Intent (getActivity(), LandmarkDetectionActivity::class.java)
            val b = Bundle()
            b.putInt("SouthAmerica", 2) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

        }

        val landmarkClassificationAsiaCardviewButton = view.findViewById<CardView>(R.id.LandmarkDetectionAsiaCardview);
        landmarkClassificationAsiaCardviewButton.setOnClickListener()
        {
            Log.d("landmarkClassificationAsiaCardviewButton: ", "Selected")
            val intent = Intent (getActivity(), LandmarkDetectionActivity::class.java)
            val b = Bundle()
            b.putInt("Asia", 3) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

        }



        val landmarkClassificationEuropeCardviewButton = view.findViewById<CardView>(R.id.LandmarkDetectionEuropeCardview);
        landmarkClassificationEuropeCardviewButton.setOnClickListener()
        {
            Log.d("object Detection cardview: ", "Selected")
            val intent = Intent (getActivity(), LandmarkDetectionActivity::class.java)

            val b = Bundle()
            b.putInt("Europe", 4) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

        }

        val landmarkClassificationAfricaCardviewButton = view.findViewById<CardView>(R.id.LandmarkDetectionAfricaCardview);
        landmarkClassificationAfricaCardviewButton.setOnClickListener()
        {
            Log.d("object Detection cardview: ", "Selected")
            val intent = Intent (getActivity(), LandmarkDetectionActivity::class.java)
            val b = Bundle()
            b.putInt("Africa", 5) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

        }

        val landmarkClassificationAntarticaCardviewButton = view.findViewById<CardView>(R.id.LandmarkDetectionAntarticaCardview);
        landmarkClassificationAntarticaCardviewButton.setOnClickListener()
        {
            Log.d("landmarkClassificationAntarticaCardviewButton: ", "Selected")
            val intent = Intent (getActivity(), LandmarkDetectionActivity::class.java)
            val b = Bundle()
            b.putInt("Antarctica", 6) //Your id
            intent.putExtras(b) //Put your id to your next Intent
            startActivity(intent)

        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LandmarkDetection.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LandmarkDetectionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}