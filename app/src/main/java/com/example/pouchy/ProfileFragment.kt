package com.example.pouchy

import android.content.Intent
import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            //Ambil Export
            val exportLayout = view.findViewById<TextView>(R.id.exportLayout)

            // Set onClick listener
            exportLayout.setOnClickListener {
                val intent = Intent(requireContext(), Export::class.java)
                startActivity(intent)

            }
        }

    }