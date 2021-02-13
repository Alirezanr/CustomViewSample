package dan.nr.customviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _viewNumber = MutableLiveData<Int>().apply {
        value = 0
    }
    val viewNumber: LiveData<Int>
        get() = _viewNumber

    private var _clearScreen = MutableLiveData<Boolean>().apply {
        value = false
    }
    val clearScreen: LiveData<Boolean>
        get() = _clearScreen

    private var _isViewGone = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isViewGone : LiveData<Boolean>
        get() = _clearScreen

    fun increaseCustomViewNumbers() {
        _viewNumber.value?.let { a ->
            _viewNumber.value = a + 1
            Log.i("LOG_TAG", "increaseCustomViewNumbers: ${_viewNumber.value}")

        }
    }

    fun clearScreen() {
        _viewNumber.value = 0
        _clearScreen.value = true
    }
    fun isViewGone(){
        if(_isViewGone.value==true)
        {
            _isViewGone.value=false
        }else
        {
            _isViewGone.value=true
        }
    }
    fun clearScreenDone() {
        _clearScreen.value = false
    }
}