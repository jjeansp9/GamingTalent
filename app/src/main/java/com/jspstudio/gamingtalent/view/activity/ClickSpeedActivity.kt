package com.jspstudio.gamingtalent.view.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jspstudio.gamingtalent.R
import com.jspstudio.gamingtalent.base.BaseActivity
import com.jspstudio.gamingtalent.databinding.ActivityClickSpeedBinding
import com.jspstudio.gamingtalent.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class ClickSpeedActivity : BaseActivity<ActivityClickSpeedBinding>(R.layout.activity_click_speed) {
    private val TAG = "ClickSpeedActivity"

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vmTal = viewModel
        onClick()
        initObserve()
        val item = arrayListOf(
            binding.tv1,
            binding.tv2,
            binding.tv3,
            binding.tv4,
            binding.tv5
        )
        lifecycleScope.launch {
            while (true) {
                item.forEach { positionTextViewRandomly(it) }
                delay(1000)
            }
        }
    }

    private fun onClick() {

    }

    private fun initObserve() {

    }

    private fun positionTextViewRandomly(tv : TextView) {
        val parent = tv.parent as View
        val random = Random()

        tv.post {
            val x = random.nextInt(parent.width - tv.width)
            val y = random.nextInt(parent.height - tv.height)
            tv.x = x.toFloat()
            tv.y = y.toFloat()
        }
    }
}