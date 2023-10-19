package com.example.recepti


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable
import kotlin.collections.ArrayList


class AllRecipesActivity : AppCompatActivity(), RecipeRecyclerAdapter.ContentListener {
    private lateinit var recyclerAdapter: RecipeRecyclerAdapter
    private val db = Firebase.firestore

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_recipes)

        searchView = findViewById(R.id.search)
        recyclerView = findViewById(R.id.recipeList)


        db.collection("recipes")
            .get()
            .addOnSuccessListener {
                val list: ArrayList<RecipeDataClass> = ArrayList()
                for (document in it.documents) {
                    val recipe = document.toObject(RecipeDataClass::class.java)
                    if (recipe != null) {
                        recipe.id = document.id
                        list.add(recipe)
                    }
                }
                recyclerAdapter = RecipeRecyclerAdapter(list, this@AllRecipesActivity)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@AllRecipesActivity)
                    adapter = recyclerAdapter
                }
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    recyclerAdapter.search(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    recyclerAdapter.search(newText)
                }
                return true
            }
        })
    }

    override fun onItemButtonClick(index: Int, item: RecipeDataClass, clickType: ButtonClickType) {
        if(clickType==ButtonClickType.DELETE){
            recyclerAdapter.removeItem(index)
            db.collection("recipes").document(item.id).delete()
        }
    }

    override fun onItemClick(index: Int, item: RecipeDataClass) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("recipe", item as Serializable)
        startActivity(intent)
    }
}