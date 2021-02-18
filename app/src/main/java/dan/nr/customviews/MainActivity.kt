package dan.nr.customviews

import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dan.nr.customviews.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnTouchListener
{
    private val MAXIMUM_LEFT_MARGIN = 970
    private val MAXIMUM_TOP_MARGIN = 1200

    private val VERTICAL_ORIENTATION = 1
    private val HORIZONTAL_ORIENTATION = 2
    private val FREE_ORIENTATION = 3

    //views count holder:
    private var viewsCount = 0

    //creates an object of MainViewModel class in first access to this variable (lazily).
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var circleView: CircleView

    //calculate and use to save circle views position in RelativeLayout after they created or changed
    private var _xDelta = 0
    private var _yDelta = 0

    //enables DataBinding for MainActivity
    private var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding
        get() = _binding!!

    //holds existing views count in ui
    var isFirstRun: Boolean = true


    //view base margins:
    var baseTopMargin = 50
    var baseLeftMargin = 50

    @RequiresApi(Build.VERSION_CODES.N)
    public override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //initialize DataBinding
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //initialize mainViewModel variable in activity_main.xml
        binding.mainViewModel = viewModel


        //adds new circle views to ui (if exists) and gets number of drawn circle views in ui
        viewModel.viewNumber.observe(this, Observer { viewNumber ->
            viewsCount = viewNumber
            //check if there is any circle view that added before
            if (viewNumber > 0)
            {
                //check if views are aligned vertically
                if (viewModel.isVertical())
                {
                    if (this.isFirstRun)
                    {
                        alignVertically()
                        this.isFirstRun = false
                    }
                    else
                    {
                        alignVertically()
                    }
                }
                //check if views are aligned horizontally
                else if (viewModel.isHorizontal())
                {
                    if (this.isFirstRun)
                    {
                        alignHorizontally()
                        this.isFirstRun = false
                    }
                    else
                    {
                        alignHorizontally()
                    }
                }
                //if views are not aligned in any direction
                else
                {
                    if (this.isFirstRun)
                    {
                        addView()
                        this.isFirstRun = false
                    }
                    else
                    {
                        addView()
                    }
                }
            }

        })

        //figures out when to clean screen
        viewModel.clearScreen.observe(this, Observer
        {
            if (it == true)
            {
                clearScreen()
            }
        })

        //sets click listener to AlignVertically button
        binding.activityMainBtnAlignVertically.setOnClickListener {
            alignVertically()
        }

        //sets click listener to AlignHorizontally button.
        binding.activityMainBtnAlignHorizontally.setOnClickListener {
            alignHorizontally()
        }
    }

    /**
     * aligns custom views in vertical direction
     * */
    private fun alignVertically()
    {
        viewModel.setVerticalAlign(true)
        //remove all added views from ui and redraws them in vertical direction
        clearScreen()
        for (i in 0 until viewsCount)
        {
            val view = createView(VERTICAL_ORIENTATION)
            //check if screen orientation is portrait or landscape
            //is portrait
            if (this.resources.configuration.orientation == 1)
            {
                if ((baseTopMargin + 120) < MAXIMUM_TOP_MARGIN)
                {
                    baseTopMargin += 120
                }
                else
                {
                    baseLeftMargin += 120
                    baseTopMargin = 50
                }
            }
            //is landscape
            else if (this.resources.configuration.orientation == 2)
            {
                if ((baseTopMargin + 120) < 600)
                {
                    baseTopMargin += 120
                }
                else
                {
                    baseLeftMargin += 120
                    baseTopMargin = 50
                }
            }
            addViewToUi(view)
        }
        resetBaseMarginsToDefault()
    }

    /**
     * aligns custom views in horizontal direction
     * */
    private fun alignHorizontally()
    {
        viewModel.setHorizontalAlign(true)
        //remove all added views from ui and redesigns them in horizontal direction
        clearScreen()
        for (i in 0 until viewsCount)
        {
            val view = createView(HORIZONTAL_ORIENTATION)
            //check if screen orientation is portrait or landscape
            //is portrait
            if (this.resources.configuration.orientation == 1)
            {
                if ((baseLeftMargin + 120) < MAXIMUM_LEFT_MARGIN)
                {
                    baseLeftMargin += 120
                }
                else
                {
                    baseTopMargin += 120
                    baseLeftMargin = 50
                }
            }
            //is landscape
            else if (this.resources.configuration.orientation == 2)
            {
                if ((baseLeftMargin + 120) < 1700)
                {
                    baseLeftMargin += 120
                }
                else
                {
                    baseTopMargin += 120
                    baseLeftMargin = 50
                }
            }
            addViewToUi(view)
        }
        resetBaseMarginsToDefault()
    }

    /**
     * resets base margins to default value
     * */
    fun resetBaseMarginsToDefault()
    {
        baseTopMargin = 50
        baseLeftMargin = 50
    }

    /**
     * Adds new circle view to RelativeLayout
     * */
    fun addView()
    {
        val view = createView(FREE_ORIENTATION)
        // Here you can access all views inside the child layout and assign them values like circleView.setViewColor(Color.BLACK)
        circleView = view.findViewById(R.id.circle_view)
        addViewToUi(view)
    }

    /**
     * draws a single View object to ui
     * */
    fun addViewToUi(view:View)
    {
        binding.activityMainRelativeLayout.addView(view)
    }

    /**
     * returns an object of a circle view with selected orientation
     * */
    private fun createView(viewOrientation: Int): View
    {
        val view = layoutInflater.inflate(R.layout.circle_item, null)
        view.setOnTouchListener(this)
        when (viewOrientation)
        {
            VERTICAL_ORIENTATION ->
            {
                view.layoutParams = RelativeLayout.LayoutParams(150, 50).apply {
                    width = 120
                    height = 120
                    leftMargin = baseLeftMargin
                    topMargin = baseTopMargin
                    bottomMargin = -250
                    rightMargin = -250
                }
            }
            HORIZONTAL_ORIENTATION ->
            {
                view.layoutParams = RelativeLayout.LayoutParams(150, 50).apply {
                    width = 120
                    height = 120
                    leftMargin = baseLeftMargin
                    topMargin = baseTopMargin
                    bottomMargin = -250
                    rightMargin = -250
                }
            }
            FREE_ORIENTATION ->
            {
                view.layoutParams = RelativeLayout.LayoutParams(150, 50).apply {
                    width = 120
                    height = 120
                    leftMargin = (10..990).random()
                    topMargin = (10..990).random()
                    bottomMargin = -250
                    rightMargin = -250
                }
            }
        }
        return view
    }

    /**
     * removes all of circle views from ui
     * */
    private fun clearScreen()
    {
        if ((binding.activityMainRelativeLayout).childCount > 0)
        {
            binding.activityMainRelativeLayout.removeAllViews()
        }
        viewModel.clearScreenDone()
    }

    /**
     * on touch and drag and drop configs
     * */
    override fun onTouch(view: View, event: MotionEvent): Boolean
    {
        val X = event.rawX.toInt()
        val Y = event.rawY.toInt()
        when (event.action and MotionEvent.ACTION_MASK)
        {
            MotionEvent.ACTION_DOWN ->
            {
                view.bringToFront();
                val lParams = view.layoutParams as RelativeLayout.LayoutParams
                _xDelta = X - lParams.leftMargin
                _yDelta = Y - lParams.topMargin
            }
            MotionEvent.ACTION_UP ->
            {
            }
            MotionEvent.ACTION_POINTER_DOWN ->
            {
            }
            MotionEvent.ACTION_POINTER_UP ->
            {
            }
            MotionEvent.ACTION_MOVE ->
            {
                val layoutParams = view.layoutParams as RelativeLayout.LayoutParams
                layoutParams.apply {
                    leftMargin = X - _xDelta
                    topMargin = Y - _yDelta
                    rightMargin = -250
                    bottomMargin = -250
                }
                view.layoutParams = layoutParams
            }
        }
        binding.activityMainRelativeLayout.invalidate()
        return true
    }

    override fun onDestroy()
    {
        //set binding to null to reduce memory leaks
        _binding = null
        super.onDestroy()
    }
}