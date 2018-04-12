package com.grzesiek.godt.recipeslist.model

import com.grzesiek.godt.data.Recipe
import io.reactivex.Maybe
import javax.inject.Inject

open class MemoryCache @Inject constructor() {
    open var recipes: List<Recipe>? = null

    open fun fetchRecipes(): Maybe<List<Recipe>> = recipes?.let { Maybe.just(it) } ?: Maybe.empty()
}
