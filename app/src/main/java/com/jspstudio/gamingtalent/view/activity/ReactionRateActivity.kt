package com.jspstudio.gamingtalent.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.activity.viewModels
import com.jspstudio.gamingtalent.R
import com.jspstudio.gamingtalent.base.BaseActivity
import com.jspstudio.gamingtalent.databinding.ActivityReactionRateBinding
import com.jspstudio.gamingtalent.util.LogMgr
import com.jspstudio.gamingtalent.viewmodel.ReactionRateViewModel
import java.util.Locale
import java.util.concurrent.TimeUnit

class ReactionRateActivity : BaseActivity<ActivityReactionRateBinding>(R.layout.activity_reaction_rate),
    View.OnTouchListener{

    private val TAG = "ReactionRateActivity"

    private val viewModel: ReactionRateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vmRate = viewModel
        onClick()
        observe()
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun onClick() {
        binding.root.setOnTouchListener(this)

        binding.rootSub.setOnTouchListener(OnTouchListener { v, event ->
            when(event?.action) {
                MotionEvent.ACTION_UP -> {
                    if (!isStart) {
                        isStart = true
                        countDown()
                        binding.rootSub.visibility = View.GONE
                        binding.root.visibility = View.VISIBLE
                    }
                }
            }
            return@OnTouchListener true
        })
    }
    private fun observe() {

    }

    private var isStart = false
    private var canClick = false
    private var isFail = false

    private var stack = 0

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isStart) {
                    if (canClick) {
                        countDown()
                        stack += 1
                    } else {
                        if (countDownTimer != null) {
                            countDownTimer?.cancel()
                            countDownTimer = null
                        }
                        failClick()
                        binding.root.visibility = View.GONE
                        binding.rootSub.visibility = View.VISIBLE
                    }
                }
            }
        }
        return true
    }

    private fun failClick() {
        isStart = false
        binding.rootSub.setBackgroundColor(this.getColor(R.color.tab_start_end))
        binding.tvSub.text = "너무 빨랐어요"
    }

    private var countDownTimer: CountDownTimer? = null
    private var countUpTimer: CountDownTimer? = null

    private fun countDown() {
        canClick = false

        binding.root.setBackgroundColor(this.getColor(R.color.tab_loading))
        binding.tv.text = "초록을 기다리세요.."

        val randomDelay = (1000..2000).random().toLong()
        countDownTimer = object : CountDownTimer(randomDelay, 1) {
            override fun onTick(millisUntilFinished: Long) {
                LogMgr.d(TAG, millisUntilFinished.toString())
            }

            override fun onFinish() {
                binding.root.setBackgroundColor(this@ReactionRateActivity.getColor(R.color.tab_click))
                countUp()
                canClick = true
                binding.tv.text = "클릭하세요"
            }
        }.start()
    }

    private var startTime = 0L // 시작 시간
    private val maxTime = 2000L // 최대 시간 (예: 2초)

    private fun countUp() {
        countUpTimer = object : CountDownTimer(Long.MAX_VALUE, 1) {
            override fun onTick(millisUntilFinished: Long) {
                val elapsedTime = System.currentTimeMillis() - startTime
                if (elapsedTime >= maxTime) {
                    onFinish()
                    cancel()
                } else {
                    // 경과 시간 업데이트 (예: TextView에 표시)
                    binding.tv.text = (elapsedTime).toString()
                }
            }

            override fun onFinish() {
                binding.root.setBackgroundColor(this@ReactionRateActivity.getColor(R.color.tab_click))
                // 필요한 추가 로직
            }
        }.start()

        // 타이머 시작
        startTime = System.currentTimeMillis()
    }
}