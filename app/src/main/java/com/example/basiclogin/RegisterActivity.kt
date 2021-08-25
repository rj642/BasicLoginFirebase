package com.example.basiclogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPass: EditText
    private lateinit var btnRegister: Button
    private lateinit var txtLogin: TextView
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        toolbar = findViewById(R.id.toolbar)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPass = findViewById(R.id.etConfirmPass)
        btnRegister = findViewById(R.id.btnRegister)
        txtLogin = findViewById(R.id.txtLogin)

        txtLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        btnRegister.setOnClickListener {
            if (etEmail.length() == 0 || etPassword.length() == 0 || etConfirmPass.text.toString() != etPassword.text.toString()) {
                if (etEmail.length() == 0) {
                    etEmail.error = "Field can't be blank"
                }
                if (etPassword.length() == 0) {
                    etPassword.error = "Field can't be blank"
                }
                if (etConfirmPass.text.toString() != etPassword.text.toString()) {
                    etConfirmPass.error = "Password didn't match"
                }
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, "Registration Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

}