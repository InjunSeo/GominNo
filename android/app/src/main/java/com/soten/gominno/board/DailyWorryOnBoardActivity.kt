package com.soten.gominno.board

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.soten.gominno.GlobalData
import com.soten.gominno.ui.MainActivity
import com.soten.gominno.R
import com.soten.gominno.databinding.ActivityDailyWorryOnBoardBinding
import com.soten.gominno.databinding.OnBoardingItemBinding
import com.soten.gominno.ui.AgeSelectActivity
import com.soten.utils.Interpolators

class DailyWorryOnBoardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDailyWorryOnBoardBinding.inflate(layoutInflater) }

    private val callback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {

            val commentList = listOf(
                "연령대를 고르세요!",
                "고민을 해결하여 램프를 얻으세요!",
                "여기서 하세요"
            )

            binding.pagerDescription.run {
                text = commentList[position]
                animateSlideUp()
            }
            binding.pagerIndicator01.isActivated = position == 0
            binding.pagerIndicator02.isActivated = position == 1
            binding.pagerIndicator03.isActivated = position == 2
            binding.startButton.isVisible = position == 2
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = Adapter()
        binding.viewPager2.adapter = adapter
        binding.nextButton.setOnClickListener {
            binding.viewPager2.currentItem += 1
        }
        binding.startButton.setOnClickListener {
            GlobalData.showOnBoarding = false // 보드 보여줄지 말지
            startActivity(Intent(this, AgeSelectActivity::class.java))
            finish()
        }
    }

    private fun View.animateSlideUp() {
        animate().cancel()
        alpha = 0f
        translationY = 40f
        animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(800)
            .setInterpolator(Interpolators.EASE_OUT_QUINT)
            .withLayer()
            .withEndAction(null)
    }

    override fun onResume() {
        super.onResume()
        binding.viewPager2.registerOnPageChangeCallback(callback)
    }

    override fun onPause() {
        super.onPause()
        binding.viewPager2.unregisterOnPageChangeCallback(callback)
    }

    override fun onBackPressed() {
        if (binding.viewPager2.currentItem > 0) {
            binding.viewPager2.currentItem -= 1
        } else {
            super.onBackPressed()
        }
    }

    private class Adapter : RecyclerView.Adapter<ViewHolder>() {

        private val items: List<Int> = listOf(
            R.drawable.on_boarding_01,
            R.drawable.on_boarding_02,
            R.drawable.splash
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ViewHolder(OnBoardingItemBinding.inflate(inflater, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size
    }

    private class ViewHolder(
        private val binding: OnBoardingItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(@DrawableRes resId: Int) {
            binding.image.setImageResource(resId)
        }
    }
}
