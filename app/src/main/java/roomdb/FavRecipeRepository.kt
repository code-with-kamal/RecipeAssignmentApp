package roomdb

class FavRecipeRepository(private val favoriteRecipeDao: FavoriteRecipeDao) {

    // Insert a new favorite recipe
    suspend fun addFavoriteRecipe(recipeId: Int) {
        favoriteRecipeDao.addFavorite(FavoriteRecipe(recipeId))
    }

    // Remove a favorite recipe
    suspend fun removeFavoriteRecipe(recipeId: Int) {
        favoriteRecipeDao.removeFavorite(FavoriteRecipe(recipeId))
    }

    // Get all favorite recipes
    suspend fun getFavoriteRecipes(): List<FavoriteRecipe> {
        return favoriteRecipeDao.getAllFavorites()
    }

    // Check if a recipe is favorite
    suspend fun isFavorite(recipeId: Int): Boolean {
        return favoriteRecipeDao.getFavoriteById(recipeId) != null
    }
}
