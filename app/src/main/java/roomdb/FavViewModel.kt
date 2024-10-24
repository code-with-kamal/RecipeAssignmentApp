package roomdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FavViewModel(private val favrepository: FavRecipeRepository) : ViewModel() {


    private val _favoriteRecipeIds = MutableLiveData<List<Int>>()
    val favoriteRecipeIds: LiveData<List<Int>> get() = _favoriteRecipeIds

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _favoriteRecipeIds.value = favrepository.getFavoriteRecipes().map { it.recipeId }
        }
    }
    // Toggle favorite state of a recipe
    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            if (favrepository.isFavorite(recipeId)) {
                favrepository.removeFavoriteRecipe(recipeId)
            } else {
                favrepository.addFavoriteRecipe(recipeId)
            }
            loadFavorites() // Refresh favorite IDs
        }
    }
}
