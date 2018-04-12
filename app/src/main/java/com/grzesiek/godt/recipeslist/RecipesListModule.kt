package com.grzesiek.godt.recipeslist

import com.grzesiek.godt.recipeslist.model.RecipesModel
import com.grzesiek.godt.recipeslist.presenter.RecipesPresenter
import com.grzesiek.godt.recipeslist.view.RecipeListActivity
import dagger.Binds
import dagger.Module

@Module
abstract class RecipesListModule {

    @Binds
    abstract fun bindPresenter(recipesPresenter: RecipesPresenter): Presenter

    @Binds
    abstract fun bindModel(recipesModel: RecipesModel): Model

    @Binds
    abstract fun bindView(recipeListActivity: RecipeListActivity): RecipesView
}
