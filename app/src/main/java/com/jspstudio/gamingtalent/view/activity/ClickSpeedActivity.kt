package com.jspstudio.gamingtalent.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jspstudio.gamingtalent.R
import com.jspstudio.gamingtalent.base.BaseActivity
import com.jspstudio.gamingtalent.databinding.ActivityClickSpeedBinding
import com.jspstudio.gamingtalent.util.LogMgr
import com.jspstudio.gamingtalent.viewmodel.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class ClickSpeedActivity : BaseActivity<ActivityClickSpeedBinding>(R.layout.activity_click_speed) {
    private val TAG = "ClickSpeedActivity"

    private val viewModel: GameViewModel by viewModels()
    private var num = 50
    lateinit var item : ArrayList<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vmTal = viewModel
        initData()
        onClick()
        initObserve()

        item.forEach { positionTextViewRandomly(it) }
    }

    private fun initData() {
        item = arrayListOf(
            binding.tv1,
            binding.tv2,
            binding.tv3,
            binding.tv4,
            binding.tv5
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClick() {
        item.forEach { textView ->
            textView.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    handleTextViewTouch(textView)
                }
                true
            }
        }
    }
    private fun handleTextViewTouch(textView: TextView) {
        if (textView.text == num.toString()) {
            if (num <= 0) {
                textView.visibility = View.GONE
            } else {
                positionTextViewRandomly(textView)
                textView.text = (num - 5).toString()
                num -= 1
                val getText = textView.text.toString().toInt()
                if (getText <= 0) textView.visibility = View.GONE
            }
        }
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