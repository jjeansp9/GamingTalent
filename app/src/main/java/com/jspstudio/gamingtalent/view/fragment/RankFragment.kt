package com.jspstudio.gamingtalent.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.jspstudio.gamingtalent.R
import com.jspstudio.gamingtalent.databinding.FragmentRankBinding
import com.jspstudio.gamingtalent.databinding.FragmentTalentBinding
import com.jspstudio.gamingtalent.view.adapter.RankVPAdapter
import com.jspstudio.gamingtalent.viewmodel.RankViewModel
import com.jspstudio.gamingtalent.viewmodel.TalentViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RankFragment : Fragment() {

    private lateinit var binding : FragmentRankBinding
    private lateinit var mContext : FragmentActivity

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankBinding.inflate(inflater, container, false)
        binding.vmRank = ViewModelProvider(this)[RankViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireActivity()
        initView()
    }

    private fun initView() {
        val pagerAdapter = RankVPAdapter(this)
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = pagerAdapter

        val tabTitle = listOf("반응속도", "손가락 스피드")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]

        }.attach()
    }

}