import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import errorhandling.ResultRes
import kotlinx.coroutines.launch
import modal.RecipeModal

class RecipeViewModel : ViewModel() {
    private val repository = RecipeRepository()
    val _recipeList = MutableLiveData<ResultRes<RecipeModal>>()
    val recipeList: LiveData<ResultRes<RecipeModal>> = _recipeList
    private var currentPage = 0  // Track the current page
    private val limitPerPage = 20  // Number of items per page
    private var isLoading = false  // Flag to prevent multiple requests
    fun getRecipes() {
        if (isLoading) return
        _recipeList.value = ResultRes.Loading
        isLoading = true
        viewModelScope.launch {
            try {
                val data = repository.getRecipes(20,0)
                if (data.isSuccessful) {
                    if (data.body() != null) {
                        _recipeList.postValue(ResultRes.Success(data.body()!!))
                    }
                } else {
                    _recipeList.postValue(ResultRes.Error(" Some Error found.."))

                }
            } catch (e: Exception) {
                _recipeList.postValue(ResultRes.Error(e.message!!))
            }
        }
    }

}