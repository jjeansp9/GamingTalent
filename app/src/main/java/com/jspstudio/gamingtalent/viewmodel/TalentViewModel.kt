package com.jspstudio.gamingtalent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jspstudio.gamingtalent.base.BaseViewModel
import com.jspstudio.gamingtalent.model.data.TalentData
import com.jspstudio.gamingtalent.view.activity.ClickSpeedActivity
import com.jspstudio.gamingtalent.view.activity.ReactionRateActivity


class TalentViewModel : BaseViewModel() {
    val _items = MutableLiveData<List<TalentData>>()
    val items : LiveData<List<TalentData>> = _items

    fun setList() {
        val data = arrayListOf<TalentData>()
        data.add(TalentData("반응속도 테스트", ReactionRateActivity::class.java))
        data.add(TalentData("손가락 스피드 테스트", ClickSpeedActivity::class.java))
//        mList.add(
//            MainMenuItemData(
//                DataManager.BOARD_TIME_TABLE,
//                R.drawable.icon_menu_timetable,
//                R.string.main_menu_rollbook,
//                false,
//                MenuTimeTableActivity::class.java
//            )
//        )
        _items.postValue(data)
    }
}