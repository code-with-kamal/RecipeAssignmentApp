package com.example.recipeapp


import RecipeViewModel
import adapter.RecipeAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.databinding.ActivityMainBinding
import errorhandling.ResultRes
import roomdb.FavRecipeRepository
import roomdb.FavViewModel
import roomdb.RecipeDatabase
import roomdb.RecipeViewModelFactory


class MainActivity : AppCompatActivity(), RecipeAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var favRecipeRespository: FavRecipeRepository
    private val favrecipeViewModel: FavViewModel by viewModels {
        RecipeViewModelFactory(
            favRecipeRespository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recipeViewModel.getRecipes()

        val database = RecipeDatabase.getDatabase(this)
        favRecipeRespository = FavRecipeRepository(database.favoriteRecipeDao())

    }

    override fun onResume() {
        super.onResume()
        recipeViewModel.recipeList.observe(this) { recipe ->
            when (recipe) {
                is ResultRes.Loading -> {
                    binding.progessbar.visibility = View.VISIBLE
                    binding.recipeRecycler.visibility = View.INVISIBLE
                    println("MainActivity.onResume..loading")
                }

                is ResultRes.Success -> {
                    binding.progessbar.visibility = View.INVISIBLE
                    binding.recipeRecycler.visibility = View.VISIBLE
                    println("MainActivity.onResume..sucess..${recipe.data}")

                    favrecipeViewModel.favoriteRecipeIds.observe(this) {
                        binding.recipeRecycler.adapter =
                            RecipeAdapter(recipe.data, this@MainActivity, it)

                    }

                }

                is ResultRes.Error -> {
                    binding.progessbar.visibility = View.INVISIBLE
                    binding.recipeRecycler.visibility = View.INVISIBLE
                    println("MainActivity.onResume..Error     ${recipe.exception}")
                }


            }


        }
    }

    override fun AddFavRecipoe(id: Int) {
        favrecipeViewModel.toggleFavorite(id)
    }




}