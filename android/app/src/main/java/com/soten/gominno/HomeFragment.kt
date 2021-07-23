package com.soten.gominno

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.HomeItemAnimator
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.SeekableAnimatedVectorDrawable
import com.google.android.material.appbar.AppBarLayout
import com.soten.gominno.databinding.FragmentHomeBinding
import com.soten.gominno.databinding.ItemHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private var savd: SeekableAnimatedVectorDrawable? = null
    private var isTop: Boolean = true
    private val listener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        val progress = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
        binding.header.root.progress = progress
        savd?.currentPlayTime = (100 * progress).toLong()
        isTop = progress <= 0f
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view).apply {
            SeekableAnimatedVectorDrawable.create(view.context, R.drawable.avd_search)?.let {
                savd = it
                header.icon.setImageDrawable(it)
            }
            header.icon.setOnClickListener {
                if (isTop) {
                    Toast.makeText(it.context, "SearchActivity 구현 해야함", Toast.LENGTH_SHORT).show()
                } else {
                    appBar.setExpanded(true, true)
                    recyclerView.smoothScrollToPosition(0)
                }
            }

            val adapter = Adapter { uiModel ->
                activity?.navigateToDetail(uiModel)
            }
            recyclerView.itemAnimator = HomeItemAnimator().apply { addDuration = 200 }
            recyclerView.adapter = adapter

            viewModel.uiModel.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it.items)
            })
            appBar.addOnOffsetChangedListener(listener)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.appBar.addOnOffsetChangedListener(listener)
    }

    override fun onPause() {
        binding.appBar.removeOnOffsetChangedListener(listener)
        super.onPause()
    }

    private fun Activity.navigateToDetail(uiModel: HomeItemUiModel) {
        val intent = Intent(this, LoginActivity::class.java) // TODO : 디테일 구현
        intent.putExtra("resId", uiModel.resId)
        startActivity(intent)
    }

    class Adapter(
        private val onItemClick: (HomeItemUiModel) -> Unit
    ) : ListAdapter<HomeItemUiModel, ViewHolder>(NoDiffCallback()) {

        class NoDiffCallback<T> : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = false
            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = false
        }

        private var expandedPosition = RecyclerView.NO_POSITION
            set(value) {
                val from = field
                field = value
                if (from != RecyclerView.NO_POSITION) notifyItemChanged(from)
                if (value != RecyclerView.NO_POSITION) notifyItemChanged(value)
            }

        init {
            stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemHomeBinding.inflate(inflater, parent, false)
            return ViewHolder(binding,
                { _, position -> onItemClick(getItem(position)) },
                { position ->
                    expandedPosition = if (expandedPosition == position) {
                        RecyclerView.NO_POSITION
                    } else {
                        position
                    }
                }
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getItem(position), expanded = expandedPosition == position)
        }
    }

    class ViewHolder(
        private val binding: ItemHomeBinding,
        private val onItemClick: (ItemHomeBinding, Int) -> Unit,
        private val onMoreClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(binding, bindingAdapterPosition)
            }
            binding.more.setOnClickListener {
                onMoreClick(bindingAdapterPosition)
            }
        }

        fun bind(uiModel: HomeItemUiModel, expanded: Boolean) {
            binding.thumbnailImage.setImageResource(uiModel.resId)

            TransitionManager.beginDelayedTransition(binding.root)
            binding.root.isActivated = expanded
            binding.thumbnailImage.isVisible = expanded
        }
    }

}