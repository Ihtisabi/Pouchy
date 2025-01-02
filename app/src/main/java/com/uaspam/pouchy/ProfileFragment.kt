package com.uaspam.pouchy

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.uaspam.pouchy.R
import com.uaspam.pouchy.api.AdviceApiService
import com.uaspam.pouchy.api.AdviceResponse
import com.uaspam.pouchy.api.RetrofitClient
import com.uaspam.pouchy.data.AppDatabase
import com.uaspam.pouchy.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.profile) {

    private lateinit var textTittle6: TextView
    private lateinit var export: TextView
    private lateinit var logout: TextView
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var adviceTextView: TextView
    private lateinit var editIcon: View // Ikon untuk mengedit username
    private lateinit var userViewModel: UserViewModel // ViewModel untuk Room

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        textTittle6 = view.findViewById(R.id.textTittle6)
        export = view.findViewById(R.id.Export)
        logout = view.findViewById(R.id.Logout)
        rootLayout = view.findViewById(R.id.profileLayout)
        adviceTextView = view.findViewById(R.id.adviceTextView)
        editIcon = view.findViewById(R.id.editIcon)

        // Initialize ViewModel
        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val repository = UserRepository(userDao)
        val factory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        // Ambil data user dari arguments
        val userId = arguments?.getInt("USER_ID")
        val username = arguments?.getString("USERNAME")
        val email = arguments?.getString("EMAIL")

        // Set data user ke UI
        textTittle6.text = "Hi, $username!"
        val textEmail = view.findViewById<TextView>(R.id.Email_name)
        textEmail.text = email ?: "$email"
        val password = view.findViewById<TextView>(R.id.Password_name)
        password.text = "********"

        // Fetch random advice and display it
        fetchAdvice()

        // Handle edit username
        editIcon.setOnClickListener {
            showEditUsernameDialog(userId)
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

    private fun showEditUsernameDialog(userId: Int?) {
        if (userId == null) {
            Toast.makeText(requireContext(), "User ID not found!", Toast.LENGTH_SHORT).show()
            return
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update Username")

        val input = EditText(requireContext())
        input.hint = "Enter new username"
        builder.setView(input)

        builder.setPositiveButton("Update") { _, _ ->
            val newUsername = input.text.toString().trim()
            if (newUsername.isNotEmpty()) {
                // Jalankan coroutine untuk memperbarui username
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        userViewModel.updateUsername(userId, newUsername)

                        // Update UI setelah berhasil
                        textTittle6.text = "Hi, $newUsername!"
                        Toast.makeText(requireContext(), "Username updated to $newUsername", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Failed to update username: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }
}
