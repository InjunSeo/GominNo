package com.soten.gominno.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import androidx.room.Room
import androidx.vectordrawable.graphics.drawable.SeekableAnimatedVectorDrawable
import com.google.android.material.appbar.AppBarLayout
import com.soten.gominno.HomeItemUiModel
import com.soten.gominno.HomeViewModel
import com.soten.gominno.R
import com.soten.gominno.databinding.FragmentHomeBinding
import com.soten.gominno.databinding.ItemHomeBinding
import com.soten.gominno.db.AppDatabase

class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var db: AppDatabase

    private val viewModel by viewModels<HomeViewModel>()

    private var savd: SeekableAnimatedVectorDrawable? = null
    private var isTop: Boolean = true
    private val listener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        val progress = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
        binding.header.root.progress = progress
        savd?.currentPlayTime = (100 * progress).toLong()
        isTop = progress <= 0f
    }

    private lateinit var adapter: Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Room.databaseBuilder(
            requireContext(), AppDatabase::class.java, AppDatabase.DB_NAME
        ).build()

        binding = FragmentHomeBinding.bind(view).apply {
            SeekableAnimatedVectorDrawable.create(view.context, R.drawable.avd_search)?.let {
                savd = it
                header.icon.setImageDrawable(it)
            }
            header.icon.setOnClickListener {
                if (isTop) {
                    Toast.makeText(it.context, "TODO : Search", Toast.LENGTH_SHORT).show()
                } else {
                    appBar.setExpanded(true, true)
                    recyclerView.smoothScrollToPosition(0)
                }
            }

            adapter = Adapter { uiModel ->
                activity?.navigateToDetail(uiModel)
            }
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.itemAnimator = HomeItemAnimator().apply { addDuration = 200 }
            recyclerView.adapter = adapter


            showPostList()

            fab.setOnClickListener {
                startActivity(
                    Intent(context, PostingActivity::class.java)
                )
            }

            appBar.addOnOffsetChangedListener(listener)
        }

    }

    private fun showPostList() {
        Thread {
            db.PostDao().getAllPost().reversed().run {
                activity?.runOnUiThread {
                    Log.d("TestT", "$this")
                    adapter.submitList(this)
                    adapter.notifyDataSetChanged()
                }
            }
        }.start()
    }

    override fun onResume() {
        super.onResume()
        binding.appBar.addOnOffsetChangedListener(listener)
        showPostList()
    }

    override fun onPause() {
        binding.appBar.removeOnOffsetChangedListener(listener)
        super.onPause()
    }

    private fun Activity.navigateToDetail(uiModel: HomeItemUiModel) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("resId", uiModel.title)
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
            return ViewHolder(binding
            ) { _, position -> onItemClick(getItem(position)) }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getItem(position), expanded = expandedPosition == position)
        }
    }

    class ViewHolder(
        private val binding: ItemHomeBinding,
        private val onItemClick: (ItemHomeBinding, Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(binding, bindingAdapterPosition)
            }
        }

        fun bind(uiModel: HomeItemUiModel, expanded: Boolean) {
            TransitionManager.beginDelayedTransition(binding.root)
            binding.root.isActivated = expanded
            binding.title.text = uiModel.title
            binding.description.text = uiModel.description
        }
    }

}