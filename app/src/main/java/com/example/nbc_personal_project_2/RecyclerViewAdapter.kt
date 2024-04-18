package com.example.nbc_personal_project_2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_personal_project_2.databinding.ProductItemBinding

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>()  {
    private lateinit var itemClickListener: OnItemClickListener
    var datalist = showProductList()

    interface OnItemClickListener{
        fun onItemClick(view: View, position:Int)
    }
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }

    inner class MyViewHolder(private val binding: ProductItemBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.cardView.setOnClickListener{
                val pos = adapterPosition
                if(pos != RecyclerView.NO_POSITION && itemClickListener != null){
                    itemClickListener.onItemClick(binding.cardView, pos)
                }
            }
        }
        fun bind(productData:Product){
            binding.productImg.setImageResource(productData.img)
            binding.productTitle.text = productData.title
            binding.productAddress.text = productData.address
            binding.productPrice.text = productData.price
            binding.productChat.text = productData.chat.toString()
            binding.productLike.text = productData.like.toString()
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.MyViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) = holder.bind(datalist[position])
    override fun getItemCount(): Int = datalist.size
}