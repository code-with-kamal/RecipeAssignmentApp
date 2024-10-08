package roomdb// FavoriteRecipeDao.kt
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteRecipeDao {

    @Insert
    suspend fun addFavorite(favoriteRecipe: FavoriteRecipe)

    @Delete
    suspend fun removeFavorite(favoriteRecipe: FavoriteRecipe)

    @Query("SELECT * FROM favorite_recipes")
    suspend fun getAllFavorites(): List<FavoriteRecipe>

    @Query("SELECT * FROM favorite_recipes WHERE recipeId = :id")
    suspend fun getFavoriteById(id: Int): FavoriteRecipe?
}
