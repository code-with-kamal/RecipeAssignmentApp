import api.RetrofitClient
import modal.RecipeModal
import retrofit2.Response

class RecipeRepository {

    suspend fun getRecipes(): Response<RecipeModal> {
        return RetrofitClient.api.getRecipes()
  }
}
