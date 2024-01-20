package com.jspstudio.gamingtalent.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes val layoutId: Int
) : AppCompatActivity() {
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }

    protected fun showToast(msg: String) {
        if (toast == null) toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        else toast!!.setText(msg)
        toast!!.show()
    }
    companion object { private var toast: Toast? = null }
}