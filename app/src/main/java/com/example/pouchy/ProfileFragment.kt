package com.example.pouchy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.pouchy.api.AdviceApiService
import com.example.pouchy.api.AdviceResponse
import com.example.pouchy.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.profile) {

    private lateinit var textTittle6: TextView
    private lateinit var export: TextView
    private lateinit var logout: TextView
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var adviceTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        export = view.findViewById(R.id.Export)
        logout = view.findViewById(R.id.Logout)
        rootLayout = view.findViewById(R.id.profileLayout)
        adviceTextView = view.findViewById(R.id.adviceTextView) // TextView untuk menampilkan advice

        val userId = arguments?.getInt("USER_ID")
        val username = arguments?.getString("USERNAME")
        val email = arguments?.getString("EMAIL")
        val textUser = view.findViewById<TextView>(R.id.textTittle6)
        textUser.text = "Hi, $username!"
        val textEmail = view.findViewById<TextView>(R.id.Email_name)
        textEmail.text = "$email"
        val password = view.findViewById<TextView>(R.id.Password_name)
        password.text = "********"

        // Fetch random advice and display it
        fetchAdvice()

        // Handle edit username

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

    private fun fetchAdvice() {
        val apiService = RetrofitClient.instance.create(AdviceApiService::class.java)

        apiService.getRandomAdvice().enqueue(object : Callback<AdviceResponse> {
            override fun onResponse(call: Call<AdviceResponse>, response: Response<AdviceResponse>) {
                if (response.isSuccessful) {
                    val advice = response.body()?.slip?.advice
                    adviceTextView.text = advice ?: "No advice available."
                } else {
                    Toast.makeText(requireContext(), "Failed to load advice", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AdviceResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}