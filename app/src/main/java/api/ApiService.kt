package api

import modal.RecipeModal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("recipes")
    suspend fun getRecipes(@Query("limit") limit:Int,@Query("skip") skip:Int): Response<RecipeModal>
}