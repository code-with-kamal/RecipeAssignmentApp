package com.example.recipeapp

import RecipeViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
open class BaseActivity: AppCompatActivity() {


        lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]


    }

}