package com.share.dogbreeds.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.share.dogbreeds.databinding.ViewDogBreedItemBinding

/**
 * @author Juan Sebastian Niño
 */
class DogBreedsAdapter(
    private var onSelected: (String) -> Unit,
    var data: List<String> = emptyList()
) : RecyclerView.Adapter<DogBreedsAdapter.MyViewHolder>()  {

    inner class MyViewHolder(private val binding: ViewDogBreedItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val dogBreedItem = getItem(position)
            binding.apply {
                textViewBreedName.text = dogBreedItem
                root.setOnClickListener {
                    onSelected.invoke(dogBreedItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogBreedsAdapter.MyViewHolder {
        return MyViewHolder(
            ViewDogBreedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DogBreedsAdapter.MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = data.size

    private fun getItem(position: Int): String = data[position]

    fun updateList(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }

}