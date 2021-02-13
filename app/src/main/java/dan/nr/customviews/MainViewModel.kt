package dan.nr.customviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    //keeps number of circles that drawn in the ui screen or use as number of RecyclerView's items
    private var _viewNumber = MutableLiveData<Int>().apply {
        value = 0
    }
    //use LiveData with getter for encapsulation purposes
    val viewNumber: LiveData<Int>
        get() = _viewNumber
    //triggers clearScreen() method in MainActivity to remove all circle views from ui
    private var _clearScreen = MutableLiveData<Boolean>().apply {
        value = false
    }
    val clearScreen: LiveData<Boolean>
        get() = _clearScreen
    //keeps RelativeLayout's and RecyclerView's visibility state
    private var _isViewGone = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isViewGone: LiveData<Boolean>
        get() = _isViewGone
    //keeps RecyclerView's orientation state
    private var _isRecyclerViewHorizontal = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isRecyclerViewHorizontal: LiveData<Boolean>
        get() = _isRecyclerViewHorizontal
    //increases _viewNumber's value to add new circle view to ui
    fun increaseCustomViewNumbers() {
        _viewNumber.value?.let { a ->
            _viewNumber.value = a + 1
        }
    }
    //changes _clearScreen's value to clean up ui from all created circle views
    fun clearScreen() {
        _viewNumber.value = 0
        _clearScreen.value = true
    }
    //changes RelativeLayout's and RecyclerView's visibility state
    fun makeViewGoneOrVisible(isViewGone: Boolean) {
        _isViewGone.value = isViewGone
    }
    //changes RecyclerView's orientation to horizontal or vertical
    fun setRecyclerViewOrientation(isHorizontal: Boolean) {
        _isRecyclerViewHorizontal.value = isHorizontal
    }
    //changes _clearScreen's value back to false when screen is cleaned.
    fun clearScreenDone() {
        _clearScreen.value = false
    }

    override fun onCleared() {
        super.onCleared()

    }
}