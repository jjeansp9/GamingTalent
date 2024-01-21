package com.jspstudio.gamingtalent.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.jspstudio.gamingtalent.custom.GridSpaceItemDecoration
import com.jspstudio.gamingtalent.databinding.FragmentTalentBinding
import com.jspstudio.gamingtalent.model.data.TalentData
import com.jspstudio.gamingtalent.util.Util
import com.jspstudio.gamingtalent.view.adapter.TalentListAdapter
import com.jspstudio.gamingtalent.viewmodel.TalentViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class TalentFragment : Fragment() {

    private lateinit var binding : FragmentTalentBinding
    private lateinit var mContext : FragmentActivity

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter : TalentListAdapter

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
        binding = FragmentTalentBinding.inflate(inflater, container, false)
        binding.vmTal = ViewModelProvider(this)[TalentViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TalentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireActivity()
        initData()
        initView()
        initObserve()
    }
    private fun initData() {
        binding.vmTal?.setList()
    }

    private fun initView() {
        adapter = TalentListAdapter(requireActivity(), onItemClick = { startTalentActivity(it) })

        binding.recycler.adapter = adapter

//        val gridSpanCount = resources.getInteger(
//            // 가로길이가 600dp 이상인 경우 태블릿 타입으로
//            if (Util.isScreenWidthGreaterThan600dp(mContext)) R.integer.grid_span_count_tablet
//            else R.integer.grid_span_count_phone
//        )
        binding.recycler.addItemDecoration(GridSpaceItemDecoration(1, Util.fromDpToPx(12).toInt()))
    }

    private fun initObserve() {
        binding.vmTal?.items?.observe(requireActivity()) {
            adapter.submitList(it)
        }
    }

    private fun startTalentActivity(item : TalentData) {
        if (item != null && item.targetClass != null) {
            val intent = Intent(mContext, item.targetClass)
            startActivity(intent)
        }
    }
}