package com.grzesiek.godt.recipeslist.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.grzesiek.godt.R
import com.grzesiek.godt.recipeslist.RecipeViewData
import kotlinx.android.synthetic.main.recipe_item.view.*
import kotlin.properties.Delegates

class RecipesAdapter : RecyclerView.Adapter<RecipeViewHolder>() {
    var recipes: List<RecipeViewData> by Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemId(position: Int): Long {
        return recipes[position].id
    }

}

class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleView: TextView = itemView.title
    private val descriptionView: TextView = itemView.description
    private val imageView: ImageView = itemView.image
    private val ingredientsView: TextView = itemView.ingredients

    fun bind(recipeViewData: RecipeViewData) {
        titleView.text = recipeViewData.title
        descriptionView.text = recipeViewData.description
        ingredientsView.text = recipeViewData.ingredients
        Glide.with(itemView).load(recipeViewData.imageUrl).into(imageView)
    }
}
