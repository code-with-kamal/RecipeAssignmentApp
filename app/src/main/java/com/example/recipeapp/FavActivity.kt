package com.example.recipeapp

import RecipeViewModel
import adapter.FavAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.databinding.ActivityFavBinding
import errorhandling.ResultRes
import roomdb.FavRecipeRepository
import roomdb.FavViewModel
import roomdb.RecipeDatabase
import roomdb.RecipeViewModelFactory

class FavActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFavBinding

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var favRecipeRespository: FavRecipeRepository
    private val favrecipeViewModel: FavViewModel by viewModels {
        RecipeViewModelFactory(
            favRecipeRespository
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        recipeViewModel.getRecipes()

        val database = RecipeDatabase.getDatabase(this)
        favRecipeRespository = FavRecipeRepository(database.favoriteRecipeDao())

    }

    override fun onResume() {
        super.onResume()

        recipeViewModel.recipeList.observe(this) { recipe ->
            when (recipe) {
                is ResultRes.Success -> {
                    println("MainActivity.onResume..sucess..${recipe.data}")
                    favrecipeViewModel.favoriteRecipeIds.observe(this) {
                        binding.favrecipeRecycler.adapter =
                            FavAdapter(recipe.data,  it)

                        if (it.isEmpty()){
                            binding.noItem.visibility=View.VISIBLE
                            binding.favrecipeRecycler.visibility=View.INVISIBLE
                            binding.progessbar.visibility=View.INVISIBLE
                        }
                        else{
                            binding.noItem.visibility=View.GONE
                            binding.favrecipeRecycler.visibility=View.VISIBLE
                            binding.progessbar.visibility=View.GONE
                        }

                    }
                }

                is ResultRes.Error -> {
                    binding.progessbar.visibility=View.INVISIBLE
                    binding.favrecipeRecycler.visibility=View.INVISIBLE
                    binding.noItem.visibility=View.VISIBLE
                    binding.noItem.text= R.string.internet.toString()
                }

                ResultRes.Loading -> {
                    binding.progessbar.visibility=View.VISIBLE
                    binding.favrecipeRecycler.visibility=View.INVISIBLE
                    binding.noItem.visibility=View.INVISIBLE

                }
            }
        }

        favrecipeViewModel.favoriteRecipeIds.observe(this) {

        }
    }

}