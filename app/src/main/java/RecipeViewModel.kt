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
    fun getRecipes() {
        _recipeList.value = ResultRes.Loading
        viewModelScope.launch {
            try {
                val data = repository.getRecipes(20, 0)
                if (data.isSuccessful) {
                    if (data.body() != null) {
                        _recipeList.postValue(ResultRes.Success(data.body()!!))
                    }
                } else {
                    _recipeList.postValue(ResultRes.Error("Some Error found.."))

                }
            } catch (e: Exception) {
                _recipeList.postValue(ResultRes.Error(e.message!!))
            }
        }
    }

}