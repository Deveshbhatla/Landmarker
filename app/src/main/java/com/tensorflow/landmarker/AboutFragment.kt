package com.tensorflow.landmarker

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.fragment.app.Fragment
import com.tensorflow.landmarker.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _fragmentAboutBinding: FragmentAboutBinding? = null
    private val fragmentAboutBinding
        get() = _fragmentAboutBinding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentAboutBinding =
            FragmentAboutBinding.inflate(inflater, container, false)

        val cardviewButtonEmail=fragmentAboutBinding.cardviewButtonContactMe
        cardviewButtonEmail.setOnClickListener()
        {
            val intent = Intent(Intent.ACTION_VIEW)
            val subject="Contacting via Landmarker Applicaion"
            val data: Uri = Uri.parse("mailto:contact@deveshbhatla.com?subject=" + Uri.encode(subject))
            intent.data = data
            startActivity(intent)
        }

        val cardviewButtonWebsite=fragmentAboutBinding.cardviewButtonWebsite
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

        val cardviewButtonPrivacyPolicy=fragmentAboutBinding.cardviewButtonPrivacyPolicy
        cardviewButtonPrivacyPolicy.setOnClickListener()
        {
            dialog.setTitle("Privacy Policy")
            val privacyText=HtmlCompat.fromHtml((getString(R.string.privacyPolicy)), FROM_HTML_MODE_LEGACY)
            popupTextBox.text = privacyText
            dialog.show()

            closePopup.setOnClickListener()
            {
                dialog.dismiss()
            }
        }

        val cardviewButtonTermsAndConditions=fragmentAboutBinding.cardviewButtonTermsAndConditions
        cardviewButtonTermsAndConditions.setOnClickListener()
        {
            dialog.setTitle("Terms and Conditions")
            val termsText=HtmlCompat.fromHtml((getString(R.string.termsAndConditions)), FROM_HTML_MODE_LEGACY)
            popupTextBox.text = termsText
            dialog.show()

            closePopup.setOnClickListener()
            {
                dialog.dismiss()
            }
        }

        val cardviewButtonLicense=fragmentAboutBinding.cardviewButtonLicense
        cardviewButtonLicense.setOnClickListener()
        {
            dialog.setTitle("Apache License 2.0\n")
            val licenseText = HtmlCompat.fromHtml(getString(R.string.license), FROM_HTML_MODE_LEGACY)
            popupTextBox.text = licenseText//resources.getString( R.string.license)
            dialog.show()

            closePopup.setOnClickListener()
            {
                dialog.dismiss()
            }

        }


        return fragmentAboutBinding.root
    }

}