package com.example.recipeapp

import RecipeViewModel
import adapter.IngrediateAdapter
import adapter.RecipeAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.databinding.ActivityMainBinding
import com.google.android.material.chip.Chip
import errorhandling.ResultRes
import roomdb.FavRecipeRepository
import roomdb.FavViewModel
import roomdb.RecipeDatabase
import roomdb.RecipeViewModelFactory


class MainActivity : AppCompatActivity(), RecipeAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var favRecipeRespository: FavRecipeRepository
    private lateinit var recipeAdapter: RecipeAdapter
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


                    binding.ingrediate.adapter =
                        IngrediateAdapter(recipe.data.recipes?.get(0)?.ingredients)
//                    binding.chips.

                    for (item in recipe.data.recipes?.get(0)?.ingredients!!) {
                        val chip = Chip(this)
                        chip.text = item  // Set text for the chip
                        chip.isClickable = false  // Make chip non-clickable if you want just text
                        chip.isCheckable = false  // Disable checkable behavior

                        // Add the chip to the ChipGroup
                        binding.chips.addView(chip)
                    }


                    favrecipeViewModel.favoriteRecipeIds.observe(this) {
                        recipeAdapter = RecipeAdapter(recipe.data, this@MainActivity, it)
                        binding.recipeRecycler.adapter = recipeAdapter


                    }

                }

                is ResultRes.Error -> {
                    binding.progessbar.visibility = View.INVISIBLE
                    binding.recipeRecycler.visibility = View.INVISIBLE
                    println("MainActivity.onResume..Error     ${recipe.exception}")
                    Toast.makeText(this, "Some erroe found", Toast.LENGTH_SHORT).show()
                }


            }


        }
        binding.fav.setOnClickListener {

            val intent = Intent(this, FavActivity::class.java)
            startActivity(intent)


        }
    }

    override fun addFavRecipoe(id: Int) {
        favrecipeViewModel.toggleFavorite(id)
        recipeAdapter.notifyDataSetChanged()

    }


}