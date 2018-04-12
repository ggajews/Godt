package com.grzesiek.godt.recipeslist.presenter

import com.grzesiek.godt.recipeslist.Model
import com.grzesiek.godt.recipeslist.Presenter
import com.grzesiek.godt.recipeslist.RecipeViewData
import com.grzesiek.godt.recipeslist.RecipesView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RecipesPresenter @Inject constructor(
        private val recipesView: RecipesView,
        private val recipesModel: Model) : Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        recipesView
                .searches()
                .debounce(400, TimeUnit.MILLISECONDS)
                .switchMapSingle {
                    recipesModel
                            .getRecipes(it.toString())
                            .subscribeOn(Schedulers.io())
                            .map { it.map { RecipeViewData(it.id, it.title, it.description, it.imageUrl, it.ingredients.joinToString { it.name }) } }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { recipesList -> recipesView.showRecipes(recipesList) },
                        { recipesView.showDataLoadingError() }
                )

    }

    override fun stop() {
        compositeDisposable.clear()
    }
}
