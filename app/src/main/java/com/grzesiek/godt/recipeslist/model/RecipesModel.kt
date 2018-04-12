package com.grzesiek.godt.recipeslist.model

import com.grzesiek.godt.data.Recipe
import com.grzesiek.godt.recipeslist.Model
import io.reactivex.Single
import javax.inject.Inject

class RecipesModel @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val cache: DiskCache,
        private val memoryCache: MemoryCache) : Model {
    override fun getRecipes(searchKey: CharSequence): Single<List<Recipe>> {
        return memoryCache.fetchRecipes()
                .switchIfEmpty(cache
                        .fetchRecipes()
                        .doOnSuccess { memoryCache.recipes = it })
                .switchIfEmpty(remoteDataSource
                        .fetchRecipes()
                        .doOnSuccess {
                            cache.storeData(it)
                            memoryCache.recipes = it
                        }
                )
                .map {
                    if (searchKey.isNotEmpty())
                        it.filter { isMatching(it, searchKey) }
                    else it
                }
    }

    private fun isMatching(recipe: Recipe, searchKey: CharSequence): Boolean {
        return recipe.title.contains(searchKey, true) ||
                recipe.description.contains(searchKey, ignoreCase = true) ||
                recipe.ingredients.any { it.name.contains(searchKey, ignoreCase = true) }
    }
}
