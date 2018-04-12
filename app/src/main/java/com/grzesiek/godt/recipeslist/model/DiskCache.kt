package com.grzesiek.godt.recipeslist.model

import android.content.SharedPreferences
import com.grzesiek.godt.data.Recipe
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Maybe
import javax.inject.Inject

open class DiskCache @Inject constructor(private val sharedPreferences: SharedPreferences, moshi: Moshi) {

    private val jsonAdapter: JsonAdapter<List<Recipe>> = moshi.adapter(Types.newParameterizedType(List::class.java, Recipe::class.java))

    open fun storeData(list: List<Recipe>) {
        val serialized = jsonAdapter.toJson(list)
        sharedPreferences.edit().putString(CACHE_KEY, serialized).apply()
    }

    open fun fetchRecipes(): Maybe<List<Recipe>> {
        if (sharedPreferences.contains(CACHE_KEY)) {
            return Maybe.just(jsonAdapter.fromJson(sharedPreferences.getString("cache", null)))
        }
        return Maybe.empty()
    }

    companion object {
        const val CACHE_KEY = "cache"
    }

}
