package com.jspstudio.gamingtalent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jspstudio.gamingtalent.base.BaseViewModel
import com.jspstudio.gamingtalent.model.data.TalentData

class TalentViewModel : BaseViewModel() {
    val _items = MutableLiveData<List<TalentData>>()
    val items : LiveData<List<TalentData>> = _items

    fun setList() {
        val data = arrayListOf<TalentData>()
        data.add(TalentData("반응속도 테스트"))
        _items.postValue(data)
    }
}