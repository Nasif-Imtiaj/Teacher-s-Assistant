package com.ni.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ni.ui.common.baseClasses.Inflate

abstract class AbstractAdapter<T, V : ViewBinding>(private val inflate: Inflate<V>) :
    RecyclerView.Adapter<AbstractAdapter.ViewHolder<V>>() {
    private val items = arrayListOf<T>()
    abstract fun bind(itemBinding: V, item: T, position: Int)
    private var inflater: LayoutInflater? = null
    open fun getInflater(parent: ViewGroup): LayoutInflater {
        inflater = inflater ?: LayoutInflater.from(parent.context)
        return inflater!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<V> {
        return ViewHolder(inflate.invoke(getInflater(parent), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) {
        if (items.size <= position) return
        bind(holder.binding, items[position], position)
    }

    open fun getViewType(position: Int): Int = 0
    override fun getItemCount(): Int = items.size
    class ViewHolder<V : ViewBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)

    fun setItems(params: Iterable<T>) {
        var itSz = items.size
        items.clear()
        notifyItemRangeRemoved(0, itSz)
        items.addAll(params)
        notifyItemRangeInserted(0, items.size)
    }

    private var activeItemCnt: Int = 0
    fun clear() {
        activeItemCnt = items.size
        items.clear()
        notifyItemRangeRemoved(0, activeItemCnt)
    }

    fun updateSelectionByPredicate(function: (T) -> Boolean) {
        val changes = arrayListOf<Int>()
        items.forEachIndexed { index, it ->
            if (function(it)) changes.add(index)
        }
        changes.forEach { notifyItemChanged(it) }
    }
}