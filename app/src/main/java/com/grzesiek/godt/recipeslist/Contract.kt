package com.grzesiek.godt.recipeslist

import com.grzesiek.godt.data.Recipe
import io.reactivex.Observable
import io.reactivex.Single

interface Presenter {
    fun start()
    fun stop()
}

interface RecipesView {
    fun showRecipes(recipesList: List<RecipeViewData>)
    fun searches(): Observable<CharSequence>
    fun showDataLoadingError()
}

interface Model {
    fun getRecipes(searchKey: CharSequence): Single<List<Recipe>>
}

data class RecipeViewData(val id: Long,
                          val title: String,
                          val description: String,
                          val imageUrl: String?,
                          val ingredients: String)
