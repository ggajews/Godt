package com.grzesiek.godt.data.remote

import io.reactivex.Single
import retrofit2.http.GET

interface GodtApiService {
    @GET("getRecipesListDetailed?tags=&size=thumbnail-medium&ratio=1&limit=50&from=0")
    fun getRecipes(): Single<List<RecipeApiData>>
}

data class RecipeApiData(
        val id: Long,
        val title: String,
        val description: String,
        val ingredients: List<IngredientApiData>,
        val images: List<Image>)

data class IngredientApiData(val name: String)
data class Image(val url: String)

//https://www.godt.no/api/getRecipesListDetailed?tags=&size=thumbnail-medium&ratio=1&limit=50&from=0
