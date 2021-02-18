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


    private var isVerticalAligned: Boolean = false
    private var isHorizontalAligned: Boolean = false

    /**
     * returns true if current orientation of views are vertical
     * @return Boolean
     */
    fun isVertical(): Boolean {
        return isVerticalAligned
    }
    /**
     * set circle views orientation to vertical
     * @return Boolean
     */
    fun setVerticalAlign(isVertical:Boolean) {
        isHorizontalAligned=false
        isVerticalAligned = isVertical
    }

    /**
     * returns true if current orientation of views are Horizontal
     * @return Boolean
     */
    fun isHorizontal(): Boolean {
        return isHorizontalAligned
    }

    /**
     * set circle views orientation to horizontal
     * @return Boolean
     */
    fun setHorizontalAlign(isVertical:Boolean) {
        isVerticalAligned=false
        isHorizontalAligned = isVertical
    }


    /**
     * increases _viewNumber's value to add new circle view to ui
     * */
    fun increaseCustomViewNumbers() {
        setVerticalAlign(false)
        setHorizontalAlign(false)
        _viewNumber.value?.let { a ->
            _viewNumber.value = a + 1
        }
    }

    /**
     * changes _clearScreen's value to clean up ui from all created circle views
     **/
    fun clearScreen() {
        setVerticalAlign(false)
        setHorizontalAlign(false)
        _viewNumber.value = 0
        _clearScreen.value = true
    }

    /**
     * changes _clearScreen's value back to false when screen is cleaned.
     */
    fun clearScreenDone() {

        _clearScreen.value = false
    }
}