package roomdb// FavoriteRecipe.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavoriteRecipe(
    @PrimaryKey val recipeId: Int
)
