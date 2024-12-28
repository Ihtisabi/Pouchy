package com.example.pouchy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileFragment : Fragment(R.layout.profile) {

    private lateinit var textTittle6: TextView
    private lateinit var editUsername: ImageView
    private lateinit var export: TextView
    private lateinit var logout: TextView
    private lateinit var rootLayout: ConstraintLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        editUsername = view.findViewById(R.id.editUsername)
        export = view.findViewById(R.id.Export)
        logout = view.findViewById(R.id.Logout)
        rootLayout = view.findViewById(R.id.profileLayout)

        val userId = arguments?.getInt("USER_ID")
        val username = arguments?.getString("USERNAME")
        val textUser = view.findViewById<TextView>(R.id.textTittle6)
        textUser.text = "Hi, $username!"

        // Handle edit username
        editUsername.setOnClickListener {
            // Create EditText to replace the TextView
            val editText = EditText(requireContext())
            editText.setText(textTittle6.text)
            editText.setTextColor(textTittle6.currentTextColor)
            editText.textSize = 23f  // Explicitly set the text size to 23sp
            editText.setTypeface(textTittle6.typeface)
            editText.setBackgroundColor(android.graphics.Color.TRANSPARENT)  // Make background transparent

            // Set the same constraints and margins as the TextView
            val layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.topToBottom = R.id.profil
            layoutParams.startToStart = R.id.profileLayout
            layoutParams.endToEnd = R.id.profileLayout
            layoutParams.topMargin = 15  // Match the top margin of the TextView

            editText.layoutParams = layoutParams

            // Hide TextView and show EditText
            textTittle6.visibility = View.GONE
            editUsername.visibility = View.GONE
            rootLayout.addView(editText)


            // Save the new username when the user clicks anywhere outside the EditText
            rootLayout.setOnTouchListener { _, _ ->
                textTittle6.text = editText.text
                textTittle6.visibility = View.VISIBLE
                editUsername.visibility = View.VISIBLE
                editText.visibility = View.GONE
                true  // Consume the touch event
            }
        }

        // Navigate to export activity
        export.setOnClickListener {
            val intent = Intent(activity, Export::class.java)
            userId?.let { id -> intent.putExtra("USER_ID", id) }
            startActivity(intent)
        }

        // Navigate to login activity
        logout.setOnClickListener {
            val intent = Intent(activity, Login::class.java)
            startActivity(intent)
        }
    }
}