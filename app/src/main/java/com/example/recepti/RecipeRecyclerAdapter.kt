package com.example.recepti


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

enum class ButtonClickType{
    DELETE
}

class RecipeRecyclerAdapter(var items: ArrayList<RecipeDataClass>, val listener: ContentListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecipeViewHolder -> {
                holder.bind(position, items[position], listener)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private val originalList = items.toList()
    fun search(query: String) {
        val filteredList = originalList.filter {
            it.dataTitle!!.contains(query, ignoreCase = true)
        }
        updateData(filteredList)
    }
    private fun updateData(filteredData: List<RecipeDataClass>) {
        items.clear()
        items.addAll(filteredData)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int){
        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, items.size)
    }


    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeImage = itemView.findViewById<ImageView>(R.id.image)
        private val recipeTitle = itemView.findViewById<TextView>(R.id.title)
        //val cardView = itemView.findViewById<CardView>(R.id.card)
        private val deleteBtn = itemView.findViewById<ImageButton>(R.id.deleteButton)
        //private val recipeIngredients = itemView.findViewById<TextView>(R.id.pripremaUbaci)//dopuni
        //private val recipePreparation = itemView.findViewById<TextView>(R.id.sastojciUbaci)//dopuni


        fun bind(index: Int, recipe:RecipeDataClass, listener: ContentListener){
            Glide.with(itemView.context).load(recipe.dataImage).into(recipeImage)
            recipeTitle.setText(recipe.dataTitle)

            deleteBtn.setOnClickListener{
                listener.onItemButtonClick(index, recipe, ButtonClickType.DELETE)
            }

            itemView.setOnClickListener{
                listener.onItemClick(index, recipe)
            }
        }
    }

    interface ContentListener{
        fun onItemButtonClick(index: Int, item: RecipeDataClass, clickType: ButtonClickType)
        fun onItemClick(index: Int, item: RecipeDataClass)
    }

}