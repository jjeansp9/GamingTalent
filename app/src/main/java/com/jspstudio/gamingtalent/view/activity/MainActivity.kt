package com.jspstudio.gamingtalent.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.jspstudio.gamingtalent.R
import com.jspstudio.gamingtalent.base.BaseActivity
import com.jspstudio.gamingtalent.databinding.ActivityMainBinding
import com.jspstudio.gamingtalent.view.fragment.CommunityFragment
import com.jspstudio.gamingtalent.view.fragment.RankFragment
import com.jspstudio.gamingtalent.view.fragment.SettingFragment
import com.jspstudio.gamingtalent.view.fragment.TalentFragment
import com.jspstudio.gamingtalent.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vmMain = viewModel

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.bnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.talent_test -> {
                    replaceFragment(TalentFragment())
                    true
                }
                R.id.rank -> {
                    replaceFragment(RankFragment())
                    true
                }
//                R.id.community -> {
//                    replaceFragment(CommunityFragment())
//                    true
//                }
//                R.id.setting -> {
//                    replaceFragment(SettingFragment())
//                    true
//                }
                else -> false
            }
        }

        // 기본적으로 첫 번째 탭 선택
        binding.bnv.selectedItemId = R.id.talent_test
    }
    // TODO : 일단 화면 간단하게 만들기

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}