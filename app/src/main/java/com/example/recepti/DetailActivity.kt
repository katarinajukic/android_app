package com.example.recepti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imageView=findViewById<ImageView>(R.id.slikaDetalji)
        val title=findViewById<TextView>(R.id.nazivDetalji)
        val ingredients=findViewById<TextView>(R.id.sastojciUbaci)
        val preparation=findViewById<TextView>(R.id.pripremaUbaci)

        val recipeDataClass = intent.getSerializableExtra("recipe") as RecipeDataClass

        title.text = recipeDataClass.dataTitle
        ingredients.text = recipeDataClass.dataIngredients
        preparation.text = recipeDataClass.dataPreparation
        Glide.with(this)
            .load(recipeDataClass.dataImage)
            .into(imageView)
    }
}