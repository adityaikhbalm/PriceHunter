package com.adityaikhbalm.pricehunter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaikhbalm.pricehunter.databinding.ItemLayoutBinding
import com.adityaikhbalm.pricehunter.model.Product
import com.adityaikhbalm.pricehunter.utils.Utility.convertDpToPixel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class ProductAdapter : ListAdapter<Product, ProductAdapter.Holder>(ProductItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.Holder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(
        private val binding: ItemLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.run {
                val context = root.context
                Glide
                    .with(context)
                    .load(product.image)
                    .transform(CenterCrop(), RoundedCorners(15.convertDpToPixel))
                    .into(itemImage)
                itemName.text = product.name
                itemPrice.text = product.price
            }
        }
    }
}

class ProductItemDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean = oldItem == newItem
}
