package api

import modal.RecipeModal
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("recipes")
    suspend fun getRecipes(): Response<RecipeModal>
}