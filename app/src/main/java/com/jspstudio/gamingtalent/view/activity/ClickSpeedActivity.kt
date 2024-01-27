package com.jspstudio.gamingtalent.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import com.jspstudio.gamingtalent.R
import com.jspstudio.gamingtalent.base.BaseActivity
import com.jspstudio.gamingtalent.custom.CustomToast
import com.jspstudio.gamingtalent.databinding.ActivityClickSpeedBinding
import com.jspstudio.gamingtalent.util.LogMgr
import com.jspstudio.gamingtalent.viewmodel.GameViewModel
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
        startCountUp()
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
                if (getText <= 0) {
                    textView.visibility = View.GONE
                    LogMgr.e(TAG, "num: " + num)
                    if (textView == binding.tv5) {
                        CustomToast(this, binding.tvTimer.text.toString())
                        // todo 마지막 아이템 클릭시 이부분이 실행. 추후 스코어 점수 기능 구현하기 (순위 등).
                    }
                }
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

    // 시작 시간
    private var startTime = 0L
    var countDownTimer : CountDownTimer? = null
    // 카운트다운 타이머를 시작합니다.
    private fun startCountUp() {
        // 타이머 객체를 생성합니다.
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 10) {
            override fun onTick(millisUntilFinished: Long) {

                // 현재 시간을 가져옵니다.
                val currentTime = System.currentTimeMillis()

                // 경과 시간을 계산합니다.
                val elapsedTime = currentTime - startTime

                // 경과 시간을 초 단위로 변환합니다.
                val elapsedSeconds = elapsedTime / 1000

                // 경과 시간을 분 단위로 변환합니다.
                val elapsedMinutes = String.format("%02d", elapsedSeconds / 60)

                // 경과 시간을 시 단위로 변환합니다.
                val elapsedHours = String.format("%02d", elapsedMinutes.toLong() / 60)

                // 경과 시간을 초 단위로 나머지 값을 가져옵니다.
                val remainingSeconds = String.format("%02d", elapsedSeconds % 60)

                // 경과 시간을 밀리초 단위로 나머지 값을 가져옵니다.
                val remainingMilliseconds = String.format("%02d", elapsedTime % 1000).substring(0, 2)

                if (num == 0) {
                    if (countDownTimer != null) {
                        countDownTimer?.cancel()
                        binding.tvTimer.visibility = View.GONE
                    }
                }
                // 경과 시간을 출력합니다.
                binding.tvTimer.text = "$elapsedMinutes:$remainingSeconds:$remainingMilliseconds"
            }

            override fun onFinish() {
            }
        }
        countDownTimer?.start()

        // 타이머를 시작합니다.
        startTime = System.currentTimeMillis()
    }
}