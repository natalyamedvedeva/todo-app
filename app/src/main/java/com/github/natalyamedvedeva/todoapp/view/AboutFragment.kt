package com.github.natalyamedvedeva.todoapp.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.natalyamedvedeva.todoapp.BuildConfig
import com.github.natalyamedvedeva.todoapp.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return AboutPage(requireContext())
            .setImage(R.mipmap.ic_launcher)
            .isRTL(false)
            .setDescription("Todo Application")
            .addItem(Element("Version " + BuildConfig.VERSION_NAME, R.drawable.ic_info_outline_black_24dp))
            .addGroup("Connect with us")
            .addEmail("natalya.medvedeva@gmail.com", "natalya.medvedeva@gmail.com")
            .addEmail("nickitakuchur@gmail.com", "nickitakuchur@gmail.com")
            .addGitHub("natalyamedvedeva", "natalyamedvedeva")
            .addGitHub("nikitakuchur","nikitakuchur")
            .create()
    }


}
