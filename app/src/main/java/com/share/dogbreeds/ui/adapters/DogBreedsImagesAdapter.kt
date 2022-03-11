package com.share.dogbreeds.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.share.dogbreeds.R
import com.share.dogbreeds.databinding.ViewDogBreedImageItemBinding
import com.share.dogbreeds.utils.loadImageFromUrl
import com.share.domain.model.DogBreedImage

/**
 * @author Juan Sebastian Niño
 */
class DogBreedsImagesAdapter(
    private var onSelected: (DogBreedImage) -> Unit,
    var data: List<DogBreedImage> = emptyList()
) : RecyclerView.Adapter<DogBreedsImagesAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ViewDogBreedImageItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = getItem(position)
            binding.apply {
                binding.imageViewFavorite.clearAnimation()
                binding.imageViewFavorite.setImageDrawable(null)
                loadImageFromUrl(root.context, imageViewBreed, item.imageUrl)
                imageViewFavorite.setImageDrawable(
                    ContextCompat.getDrawable(root.context,
                        if (item.isFavorite) {
                            R.drawable.ic_liked_breed
                        } else  {
                            R.drawable.ic_unliked_breed
                        }
                    )
                )
                root.setOnClickListener {
                    onSelected.invoke(item)
                    if (item.isFavorite.not()) {
                        constraintLayout2.transitionToEnd()
                        constraintLayout2.addTransitionListener(object: MotionLayout.TransitionListener{
                            override fun onTransitionStarted(
                                motionLayout: MotionLayout?,
                                startId: Int,
                                endId: Int
                            ) = Unit

                            override fun onTransitionChange(
                                motionLayout: MotionLayout?,
                                startId: Int,
                                endId: Int,
                                progress: Float
                            ) = Unit

                            override fun onTransitionCompleted(
                                motionLayout: MotionLayout?,
                                currentId: Int
                            ) {
                                notifyDataSetChanged()
                            }

                            override fun onTransitionTrigger(
                                motionLayout: MotionLayout?,
                                triggerId: Int,
                                positive: Boolean,
                                progress: Float
                            ) = Unit

                        })
                    } else {
                        imageViewFavorite.setImageResource( R.drawable.ic_unliked_breed)
                        notifyDataSetChanged()
                    }
                    item.isFavorite = item.isFavorite.not()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogBreedsImagesAdapter.MyViewHolder {
        return MyViewHolder(
            ViewDogBreedImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DogBreedsImagesAdapter.MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = data.size

    private fun getItem(position: Int): DogBreedImage = data[position]

    fun updateList(data: List<DogBreedImage>) {
        this.data = data
        notifyDataSetChanged()
    }
}