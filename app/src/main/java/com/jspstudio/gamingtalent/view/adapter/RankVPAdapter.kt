package com.jspstudio.gamingtalent.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jspstudio.gamingtalent.view.fragment.rank.RankCSpeedFragment
import com.jspstudio.gamingtalent.view.fragment.rank.RankRTRateFragment

class RankVPAdapter(fragment: Fragment?) : FragmentStateAdapter(fragment!!) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RankRTRateFragment()
            1 -> RankCSpeedFragment()
            else -> RankRTRateFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}