package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.SinglerecipeLayoutBinding
import modal.RecipeModal

class RecipeAdapter(
    val recipeData: RecipeModal,
    val listener: Listener,
    val favRecipeID: List<Int>
) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    private var binding: SinglerecipeLayoutBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = SinglerecipeLayoutBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(binding!!)
    }

    override fun getItemCount(): Int {
        return recipeData.recipes!!.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeData.recipes?.get(position)
        holder.bind(recipe, holder.binding.root.context, listener, favRecipeID)
    }

    class RecipeViewHolder(val binding: SinglerecipeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            recipe: RecipeModal.Recipe?,
            context: Context,
            listener: Listener,
            favRecipeID: List<Int>
        ) {
            Glide.with(context).load(recipe?.image)
                .into(binding.recipeIcon)
            binding.recipeName.text = recipe?.name
            binding.ingredients.text= recipe?.ingredients.toString()


            binding.fav.setOnClickListener {
                listener.AddFavRecipoe(recipe?.id!!)
            }

            if (favRecipeID.contains(recipe?.id)) {
                Glide.with(context).load(R.drawable.ic_favorite)
                    .into(binding.fav)

            } else {
                Glide.with(context).load(R.drawable.ic_heart_unfilled)
                    .into(binding.fav)
            }

        }

    }
    interface Listener {
        fun AddFavRecipoe(id: Int)

    }
}