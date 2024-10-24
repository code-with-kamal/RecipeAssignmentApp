package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.FavSingleitemBinding
import modal.RecipeModal

class FavAdapter(recipedata: RecipeModal, val favIDList: List<Int>) :
    RecyclerView.Adapter<FavAdapter.FavVeiwHolder>() {

    private var favoriteRecipes: List<RecipeModal.Recipe>? = null

    init {

        favoriteRecipes = recipedata.recipes
            ?.filter { recipe ->
                recipe != null && favIDList.contains(recipe.id)
            }
            ?.filterNotNull()
    }

    private var binding: FavSingleitemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavVeiwHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = FavSingleitemBinding.inflate(inflater, parent, false)

        return FavVeiwHolder(binding!!)
    }

    override fun getItemCount(): Int {
        return favoriteRecipes?.size ?: 0
    }

    override fun onBindViewHolder(holder: FavVeiwHolder, position: Int) {
        val recipe = favoriteRecipes?.get(position)
        holder.bind(recipe, holder.binding.root.context)
    }

    class FavVeiwHolder(val binding: FavSingleitemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeModal.Recipe?, context: Context) {
            recipe?.let {
                Glide.with(context)
                    .load(it.image)
                    .into(binding.recipeIcon)
                binding.recipeName.text = it.name
                binding.ingredients.text = it.ingredients.toString()
            }
        }
    }
}
