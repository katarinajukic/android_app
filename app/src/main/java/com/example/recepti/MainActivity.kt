package com.example.recepti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.recepti.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dodajBtn.setOnClickListener{
            val intent = Intent(this,AddRecipeActivity::class.java)
            startActivity(intent)
        }

        binding.receptiBtn.setOnClickListener{
            val intent = Intent(this,AllRecipesActivity::class.java)
            startActivity(intent)
        }

    }
}