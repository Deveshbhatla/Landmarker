package com.tensorflow.landmarker

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {
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
        val view: View = inflater.inflate(R.layout.fragment_about, container, false)

        val cardviewButtonEmail=view.findViewById<CardView>(R.id.cardview_button_contact_me)
        cardviewButtonEmail.setOnClickListener()
        {
            val intent = Intent(Intent.ACTION_VIEW)
            val subject="Contacting via Landmarker Applicaion"
            val data: Uri = Uri.parse("mailto:contact@deveshbhatla.com?subject=" + Uri.encode(subject))
            intent.data = data
            startActivity(intent)
        }


        val cardviewButtonWebsite=view.findViewById<CardView>(R.id.cardview_button_website)
        cardviewButtonWebsite.setOnClickListener()
        {
            val uri = Uri.parse("http://www.deveshbhatla.com")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        val rootView: View = inflater.inflate(R.layout.popup_layout, null)
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(rootView)
        val dialog = dialogBuilder.create()

        val popupTextBox : TextView=rootView.findViewById(R.id.textView_popup_dialog)
        val closePopup :Button=rootView.findViewById(R.id.button_dismiss_PopUp)


        val cardviewButtonPrivacyPolicy=view.findViewById<CardView>(R.id.cardview_button_privacy_policy)
        cardviewButtonPrivacyPolicy.setOnClickListener()
        {
            dialog.setTitle("Privacy Policy")
            popupTextBox.text = resources.getString( R.string.privacyPolicy )

            dialog.show()

            closePopup.setOnClickListener()
            {
                dialog.dismiss()
            }
        }

        val cardviewButtonTermsAndConditions=view.findViewById<CardView>(R.id.cardview_button_terms_and_conditions)
        cardviewButtonTermsAndConditions.setOnClickListener()
        {
            dialog.setTitle("Terms and Conditions")
            popupTextBox.text = resources.getString( R.string.termsAndConditions)

            dialog.show()

            closePopup.setOnClickListener()
            {
                dialog.dismiss()
            }
        }

        val cardviewButtonLicense=view.findViewById<CardView>(R.id.cardview_button_license)
        cardviewButtonLicense.setOnClickListener()
        {
            dialog.setTitle("Apache License 2.0\n")
            popupTextBox.text = resources.getString( R.string.license)

            dialog.show()

            closePopup.setOnClickListener()
            {
                dialog.dismiss()
            }

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
         * @return A new instance of fragment About.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}