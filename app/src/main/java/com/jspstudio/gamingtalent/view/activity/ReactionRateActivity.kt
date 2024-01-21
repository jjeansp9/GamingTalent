package com.jspstudio.gamingtalent.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.activity.viewModels
import com.jspstudio.gamingtalent.R
import com.jspstudio.gamingtalent.base.BaseActivity
import com.jspstudio.gamingtalent.databinding.ActivityReactionRateBinding
import com.jspstudio.gamingtalent.util.LogMgr
import com.jspstudio.gamingtalent.viewmodel.ReactionRateViewModel

class ReactionRateActivity : BaseActivity<ActivityReactionRateBinding>(R.layout.activity_reaction_rate){

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
        binding.root.setOnTouchListener(OnTouchListener { v, event ->
            when(event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (isStart) {
                        if (canClick) {
                            stack += 1
                            binding.root.setBackgroundColor(this@ReactionRateActivity.getColor(R.color.tab_loading))
                            countDown()

                        } else {
                            if (countDownTimer != null) {
                                countDownTimer?.cancel()
                                countDownTimer = null
                            }
                            failClick()
                            binding.root.visibility = View.GONE
                            binding.rootSub.visibility = View.VISIBLE
                            binding.tvFinish.visibility = View.GONE
                        }
                    }
                }
            }
            return@OnTouchListener true
        })

        binding.rootSub.setOnTouchListener(OnTouchListener { v, event ->
            when(event?.action) {
                MotionEvent.ACTION_UP -> {
                    if (!isStart) {
                        if (stack <= 2) {
                            isStart = true
                            countDown()
                            binding.rootSub.visibility = View.GONE
                            binding.root.visibility = View.VISIBLE
                            binding.tvFinish.visibility = View.GONE
                        }
                    }
                }
            }
            return@OnTouchListener true
        })

        binding.tvRestart.setOnClickListener {
            if (!isStart) {
                if (countDownTimer != null) {
                    countDownTimer?.cancel()
                    countDownTimer = null
                }
                if (countUpTimer != null) {
                    countUpTimer?.cancel()
                    countUpTimer = null
                }
                stack = 0
                score.clear()
                binding.root.setBackgroundColor(this.getColor(R.color.tab_loading))
                binding.tv.text = "초록을 기다리세요.."
                isStart = true
                countDown()
                binding.rootSub.visibility = View.GONE
                binding.root.visibility = View.VISIBLE
                it.visibility = View.GONE
            }
        }

        binding.tvFinish.setOnClickListener { finish() }
        binding.imgBack.setOnClickListener{ finish() }
    }
    private fun observe() {

    }

    private var isStart = false
    private var canClick = false
    private var isFail = false

    private var stack = 0

    private fun failClick() {
        isStart = false
        binding.rootSub.setBackgroundColor(this.getColor(R.color.tab_start_end))
        binding.tvSub.text = "너무 빨랐어요"
    }

    private var countDownTimer: CountDownTimer? = null
    private var countUpTimer: CountDownTimer? = null

    private fun countDown() {
        canClick = false

        if (stack >= 3) {
            isStart = false
            binding.root.visibility = View.GONE
            binding.rootSub.visibility = View.VISIBLE
            binding.tvRestart.visibility = View.VISIBLE
            binding.tvFinish.visibility = View.VISIBLE
            binding.imgBack.visibility = View.VISIBLE
            var total = 0L
            for (i in 0 until 3) { total += score[i] }
            var average = total / 3
            binding.tvSub.text = "결과를 확인하세요\n\n${score[0]}\n${score[1]}\n${score[2]}\n\n당신의 평균기록은 ${average}ms 입니다"

        } else {
            binding.root.setBackgroundColor(this.getColor(R.color.tab_loading))
            binding.tv.text = "초록을 기다리세요.."
        }

        val randomDelay = (2000..3500).random().toLong()
        countDownTimer = object : CountDownTimer(randomDelay, 1) {
            override fun onTick(millisUntilFinished: Long) {
                LogMgr.d(TAG, millisUntilFinished.toString())
            }

            override fun onFinish() {
                countUp()
                canClick = true
            }
        }.start()
    }

    private var startTime = 0L // 시작 시간

    private var score = arrayListOf<Long>()

    private fun countUp() {
        object : CountDownTimer(Long.MAX_VALUE, 1) {
            override fun onTick(millisUntilFinished: Long) {
                val elapsedTime = System.currentTimeMillis() - startTime
//                if (!canClick) {
//                    score.add(elapsedTime)
//                }
                when(stack) {
                    0 -> {
                        score.add(0, elapsedTime)
                        onFinish()
                    }
                    1 -> {
                        score.set(1, elapsedTime)
                        onFinish()
                    }
                    2 -> {
                        score.set(2, elapsedTime)
                        onFinish()
                    }
                }
            }

            override fun onFinish() {
                // 필요한 추가 로직
            }
        }.start()
        binding.root.setBackgroundColor(this@ReactionRateActivity.getColor(R.color.tab_click))
        binding.tv.text = "클릭하세요"

        // 타이머 시작
        startTime = System.currentTimeMillis()
    }
}