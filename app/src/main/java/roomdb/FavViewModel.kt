package roomdb


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FavViewModel(private val repository: FavRecipeRepository) : ViewModel() {

    // LiveData for observing favorite recipe IDs
    private val _favoriteRecipeIds = MutableLiveData<List<Int>>()
    val favoriteRecipeIds: LiveData<List<Int>> get() = _favoriteRecipeIds

    init {
        loadFavorites()
    }

    // Load favorite recipes from the repository
    private fun loadFavorites() {
        viewModelScope.launch {
            _favoriteRecipeIds.value = repository.getFavoriteRecipes().map { it.recipeId }
        }
    }

    // Toggle favorite state of a recipe
    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            if (repository.isFavorite(recipeId)) {
                repository.removeFavoriteRecipe(recipeId)
            } else {
                repository.addFavoriteRecipe(recipeId)
            }
            loadFavorites() // Refresh favorite IDs
        }
    }
}
