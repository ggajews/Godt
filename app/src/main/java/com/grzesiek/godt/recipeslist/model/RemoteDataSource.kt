package com.grzesiek.godt.recipeslist.model

import com.grzesiek.godt.data.Ingredient
import com.grzesiek.godt.data.Recipe
import com.grzesiek.godt.data.remote.GodtApiService
import com.grzesiek.godt.data.remote.RecipeApiData
import io.reactivex.Single
import javax.inject.Inject

open class RemoteDataSource @Inject constructor(private val godtApiService: GodtApiService) {
    open fun fetchRecipes(): Single<List<Recipe>> {
        return godtApiService
                .getRecipes()
                .map { it.map { convertToModel(it) } }
    }

    private fun convertToModel(recipeApiData: RecipeApiData): Recipe {
        return Recipe(
                recipeApiData.id,
                recipeApiData.title,
                recipeApiData.images.first().url,
                recipeApiData.description,
                recipeApiData.ingredients.map { Ingredient(it.name) }
        )
    }

}
