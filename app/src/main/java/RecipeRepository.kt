import api.RetrofitClient
import modal.RecipeModal
import retrofit2.Response

class RecipeRepository {

    suspend fun getRecipes(limit:Int,skip:Int): Response<RecipeModal> {
        return RetrofitClient.api.getRecipes(limit,skip)
  }
}
