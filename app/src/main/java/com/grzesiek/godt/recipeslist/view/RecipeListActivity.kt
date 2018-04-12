package com.grzesiek.godt.recipeslist.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.grzesiek.godt.R
import com.grzesiek.godt.recipeslist.Presenter
import com.grzesiek.godt.recipeslist.RecipeViewData
import com.grzesiek.godt.recipeslist.RecipesView
import com.jakewharton.rxbinding2.support.v7.widget.queryTextChanges
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_recipe_list.*
import javax.inject.Inject


class RecipeListActivity : AppCompatActivity(), RecipesView {
    private lateinit var recipesAdapter: RecipesAdapter
    private lateinit var searchView: SearchView
    private var searchInitialized = false
    private var viewInitialized = false

    @Inject
    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_recipe_list)
        recipesAdapter = RecipesAdapter()
        recyclerView.adapter = recipesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.recipes_menu, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchInitialized = true
        startPresenterIfReady()
        return true
    }

    override fun onResume() {
        super.onResume()
        viewInitialized = true
        startPresenterIfReady()
    }

    private fun startPresenterIfReady() {
        if (viewInitialized && searchInitialized) {
            presenter.start()
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.stop()
    }

    override fun showRecipes(recipesList: List<RecipeViewData>) {
        recipesAdapter.recipes = recipesList
    }

    override fun searches(): Observable<CharSequence> = searchView.queryTextChanges()

    override fun showDataLoadingError() {
        Toast.makeText(this, R.string.loading_data_error, Toast.LENGTH_LONG).show()
    }
}
