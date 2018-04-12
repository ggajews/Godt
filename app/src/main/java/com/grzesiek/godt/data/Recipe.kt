package com.grzesiek.godt.data

data class Recipe(val id: Long,
                  val title: String,
                  val imageUrl: String,
                  val description: String,
                  val ingredients: List<Ingredient>)

data class Ingredient(val name: String)


