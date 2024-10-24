package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.IngreItemBinding

class IngrediateAdapter(val ingredients: List<String?>?) :RecyclerView.Adapter<IngrediateAdapter.IngrViewholder>() {

    private var binding: IngreItemBinding? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngrViewholder {
        val inflater = LayoutInflater.from(parent.context)
        binding = IngreItemBinding.inflate(inflater, parent, false)

        return IngrViewholder(binding!!)
    }

    override fun getItemCount(): Int {
        return ingredients!!.size
    }

    override fun onBindViewHolder(holder: IngrViewholder, position: Int) {
      holder.bind(ingredients?.get(position))
    }
    class IngrViewholder(val binding: IngreItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(inge: String?) {
            binding.ingretxt.text=inge
        }
    }

}