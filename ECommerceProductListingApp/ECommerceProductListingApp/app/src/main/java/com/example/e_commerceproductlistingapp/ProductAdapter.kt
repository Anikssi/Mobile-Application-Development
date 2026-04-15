package com.example.e_commerceproductlistingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ProductAdapter(
    private var products: MutableList<Product>,
    private val onAddToCart: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isGridView: Boolean = false

    companion object {
        const val VIEW_TYPE_LIST = 0
        const val VIEW_TYPE_GRID = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridView) VIEW_TYPE_GRID else VIEW_TYPE_LIST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = if (viewType == VIEW_TYPE_GRID) R.layout.item_product_grid else R.layout.item_product_list
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return if (viewType == VIEW_TYPE_GRID) GridViewHolder(view) else ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        if (holder is ListViewHolder) holder.bind(product)
        else if (holder is GridViewHolder) holder.bind(product)
    }

    override fun getItemCount() = products.size

    fun updateList(newList: List<Product>) {
        val diffCallback = ProductDiffCallback(this.products, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.products.clear()
        this.products.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun moveItem(from: Int, to: Int) {
        Collections.swap(products, from, to)
        notifyItemMoved(from, to)
    }

    fun removeItem(position: Int): Product {
        val item = products[position]
        products.removeAt(position)
        notifyItemRemoved(position)
        return item
    }

    fun insertItem(position: Int, item: Product) {
        products.add(position, item)
        notifyItemInserted(position)
    }

    // ViewHolders
    inner class ListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(p: Product) {
            v.findViewById<TextView>(R.id.tvName).text = p.name
            v.findViewById<TextView>(R.id.tvPrice).text = "$${p.price}"
            v.findViewById<Button>(R.id.btnAddToCart).setOnClickListener { onAddToCart(p) }
        }
    }

    inner class GridViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(p: Product) {
            v.findViewById<TextView>(R.id.tvGridName).text = p.name
            v.findViewById<ImageButton>(R.id.btnGridCart).setOnClickListener { onAddToCart(p) }
        }
    }
}

class ProductDiffCallback(
    private val oldList: List<Product>,
    private val newList: List<Product>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldPos: Int, newPos: Int) = oldList[oldPos].id == newList[newPos].id
    override fun areContentsTheSame(oldPos: Int, newPos: Int) = oldList[oldPos] == newList[newPos]
}