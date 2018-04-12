package com.grzesiek.godt

import com.grzesiek.godt.recipeslist.RecipesListModule
import com.grzesiek.godt.recipeslist.view.RecipeListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [(RecipesListModule::class)])
    abstract fun recipeListActivity(): RecipeListActivity
}
