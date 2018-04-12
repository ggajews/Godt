package com.grzesiek.godt.recipeslist.model

import com.grzesiek.godt.data.Ingredient
import com.grzesiek.godt.data.Recipe
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RecipesModelTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var diskCache: DiskCache

    @Mock
    lateinit var memoryCache: MemoryCache

    lateinit var recipesModel: RecipesModel


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        recipesModel = RecipesModel(remoteDataSource, diskCache, memoryCache)
    }

    @Test
    fun shouldLoadDataFromApiWhenCacheIsNotAvailable() {
        `when`(diskCache.fetchRecipes()).thenReturn(Maybe.empty())
        `when`(memoryCache.fetchRecipes()).thenReturn(Maybe.empty())
        `when`(remoteDataSource.fetchRecipes()).thenReturn(Single.just(emptyList()))

        recipesModel
                .getRecipes("")
                .test()
                .assertValue(emptyList())

    }

    @Test
    fun shouldLoadDataFromDiskCacheWhenAvailable() {
        val cacheList = listOf(Recipe(2, "cache", "img", "", emptyList()))
        val apiList = listOf(Recipe(1, "api", "img", "", emptyList()))

        `when`(diskCache.fetchRecipes()).thenReturn(Maybe.just(cacheList))
        `when`(memoryCache.fetchRecipes()).thenReturn(Maybe.empty())
        `when`(remoteDataSource.fetchRecipes()).thenReturn(Single.just(apiList))

        recipesModel
                .getRecipes("")
                .test()
                .assertValue(cacheList)
                .assertValueCount(1)
    }

    @Test
    fun shouldLoadDataFromMemoryCacheWhenAvailable() {
        val memoryCacheList = listOf(Recipe(3, "memory", "img", "", emptyList()))
        val diskCacheList = listOf(Recipe(2, "cache", "img", "", emptyList()))
        val apiList = listOf(Recipe(1, "api", "img", "", emptyList()))

        `when`(diskCache.fetchRecipes()).thenReturn(Maybe.just(diskCacheList))
        `when`(memoryCache.fetchRecipes()).thenReturn(Maybe.just(memoryCacheList))
        `when`(remoteDataSource.fetchRecipes()).thenReturn(Single.just(apiList))

        recipesModel
                .getRecipes("")
                .test()
                .assertValue(memoryCacheList)
                .assertValueCount(1)
    }

    @Test
    fun shouldFilterByTitle() {
        val recipe1 = Recipe(1, "title1", "img", "description bla bla", listOf(Ingredient("ingredient1")))
        val recipe2 = Recipe(2, "title2", "img", "description2 bla", listOf(Ingredient("ingredient2")))
        val recipe3 = Recipe(1, "title3", "img", "description3 bla bla", listOf(Ingredient("ingredient3")))


        val list = listOf(
                recipe1,
                recipe2,
                recipe3
        )

        `when`(diskCache.fetchRecipes()).thenReturn(Maybe.empty())
        `when`(memoryCache.fetchRecipes()).thenReturn(Maybe.empty())
        `when`(remoteDataSource.fetchRecipes()).thenReturn(Single.just(list))

        recipesModel
                .getRecipes("title1")
                .test()
                .assertValue(listOf(recipe1))
                .assertValueCount(1)
    }

    @Test
    fun shouldFilterByDescription() {
        val recipe1 = Recipe(1, "title1", "img", "description1 bla bla", listOf(Ingredient("ingredient1")))
        val recipe2 = Recipe(2, "title2", "img", "description2 bla", listOf(Ingredient("ingredient2")))
        val recipe3 = Recipe(1, "title3", "img", "description3 bla bla", listOf(Ingredient("ingredient3")))


        val list = listOf(
                recipe1,
                recipe2,
                recipe3
        )

        `when`(diskCache.fetchRecipes()).thenReturn(Maybe.empty())
        `when`(memoryCache.fetchRecipes()).thenReturn(Maybe.empty())
        `when`(remoteDataSource.fetchRecipes()).thenReturn(Single.just(list))

        recipesModel
                .getRecipes("description2")
                .test()
                .assertValue(listOf(recipe2))
                .assertValueCount(1)
    }

    @Test
    fun shouldFilterByIngredient() {
        val recipe1 = Recipe(1, "title1", "img", "description1 bla bla", listOf(Ingredient("ingredient1")))
        val recipe2 = Recipe(2, "title2", "img", "description2 bla", listOf(Ingredient("ingredient2")))
        val recipe3 = Recipe(1, "title3", "img", "description3 bla bla", listOf(Ingredient("ingredient3")))


        val list = listOf(
                recipe1,
                recipe2,
                recipe3
        )

        `when`(diskCache.fetchRecipes()).thenReturn(Maybe.empty())
        `when`(memoryCache.fetchRecipes()).thenReturn(Maybe.empty())
        `when`(remoteDataSource.fetchRecipes()).thenReturn(Single.just(list))

        recipesModel
                .getRecipes("ingredient3")
                .test()
                .assertValue(listOf(recipe3))
                .assertValueCount(1)
    }
}
