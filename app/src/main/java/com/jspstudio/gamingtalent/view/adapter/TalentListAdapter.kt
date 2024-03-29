package com.jspstudio.gamingtalent.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jspstudio.gamingtalent.R
import com.jspstudio.gamingtalent.databinding.ListTalentItemBinding
import com.jspstudio.gamingtalent.model.data.TalentData
import com.jspstudio.gamingtalent.util.UtilAnim

class TalentListAdapter(
    private val context: Context,
    private val onItemClick : (item: TalentData) -> Unit,
) : ListAdapter<TalentData, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TalentData>() {
            override fun areItemsTheSame(
                oldItem: TalentData,
                newItem: TalentData
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: TalentData,
                newItem: TalentData
            ): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    inner class HomeViewHolder(private val binding: ListTalentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: TalentData, position: Int) {
            binding.item = item

            binding.root.setOnTouchListener {v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        UtilAnim.btnClickEffect(v, R.drawable.bg_corner_12, 75, 0.98f, 0.98f, true)
                        true
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        UtilAnim.btnClickEffect(v, R.drawable.bg_corner_12, 100, 1f, 1f, false)
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        UtilAnim.btnClickEffect(v, R.drawable.bg_corner_12, 100, 1f, 1f, false)
                        if (binding.item != null) onItemClick(binding.item!!)
                        true
                    }
                    else -> false
                }
            }


            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListTalentItemBinding.inflate(inflater, parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) as TalentData
        (holder as HomeViewHolder).bind(item, position)
    }
}