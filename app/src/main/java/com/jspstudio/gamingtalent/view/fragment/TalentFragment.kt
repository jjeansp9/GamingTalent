package com.jspstudio.gamingtalent.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.jspstudio.gamingtalent.R
import com.jspstudio.gamingtalent.databinding.FragmentTalentBinding
import com.jspstudio.gamingtalent.view.adapter.TalentListAdapter
import com.jspstudio.gamingtalent.viewmodel.TalentViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class TalentFragment : Fragment() {

    lateinit var binding : FragmentTalentBinding

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
        initData()
        initView()
        initObserve()
    }
    private fun initData() {
        binding.vmTal?.setList()
    }

    private fun initView() {
        adapter = TalentListAdapter(requireActivity(), onItemClick = {  })

        binding.recycler.adapter = adapter

//        val gridSpanCount = resources.getInteger(
//            // 가로길이가 600dp 이상인 경우 태블릿 타입으로
//            if (Util.isScreenWidthGreaterThan600dp(context)) R.integer.grid_span_count_tablet
//            else R.integer.grid_span_count_phone
//        )
        //binding.myCoinRecycler.addItemDecoration(GridSpaceItemDecoration(gridSpanCount, Util.fromDpToPx(12).toInt()))
    }

    private fun initObserve() {
        binding.vmTal?.items?.observe(requireActivity()) {
            adapter.submitList(it)
        }
    }
}