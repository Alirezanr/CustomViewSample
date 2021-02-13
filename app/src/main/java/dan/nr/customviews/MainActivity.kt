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
import androidx.recyclerview.widget.LinearLayoutManager
import dan.nr.customviews.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnTouchListener {

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

    //an object of CustomAdapter class to use as RecyclerView custom adapter
    private lateinit var customViewAdapter: CustomAdapter

    //holds RecyclerView's list items
    var listItems = ArrayList<Int>()

    @RequiresApi(Build.VERSION_CODES.N)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initialize DataBinding
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //initialize mainViewModel variable in activity_main.xml
        binding.mainViewModel = viewModel
        //sets up RecyclerView's first attributes
        setupRecyclerView()
        //figures out which one of RecyclerView or RelativeLayout is gone
        viewModel.isViewGone.observe(this, Observer {
            if (it == false) {
                binding.activityMainRelativeLayout.visibility = View.VISIBLE
                binding.activityMainRecyclerView.visibility = View.GONE
            } else {
                binding.activityMainRelativeLayout.visibility = View.GONE
                binding.activityMainRecyclerView.visibility = View.VISIBLE
            }
        })

        //figures out RecyclerView's orientation
        viewModel.isRecyclerViewHorizontal.observe(this, Observer {
            if (it == true) {
                binding.activityMainRecyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            } else {
                binding.activityMainRecyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            }
        })

        //adds new circle views to ui and gets number of drawn circle views in ui
        viewModel.viewNumber.observe(this, Observer { viewNumber ->
            if (viewNumber > 0) {
                if (listItems.size == 0) {
                    for (i in 0 until viewNumber) {
                        addView()
                        listItems.add(i)
                    }
                } else {
                    addView()
                    listItems.add(1)
                }
                customViewAdapter.notifyDataSetChanged()
            }
        })
        //figures out when to clean screen
        viewModel.clearScreen.observe(this, Observer {
            if (it == true) {
                if ((binding.activityMainRelativeLayout).childCount > 0) {
                    binding.activityMainRelativeLayout.removeAllViews()
                }
                clearScreen()
            }
        })
        //sets click listener to AlignVertically button.
        binding.activityMainBtnAlignVertically.setOnClickListener {
            //set RecyclerView orientation to vertical
            viewModel.setRecyclerViewOrientation(false)

            //makes RelativeLayout gone
            viewModel.makeViewGoneOrVisible(true)

            //changes RecyclerViews orientation to vertical
            binding.activityMainRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.activityMainBtnAlignHorizontally.setOnClickListener {
            //set RecyclerView orientation to horizontal
            viewModel.setRecyclerViewOrientation(true)

            //makes RelativeLayout gone
            viewModel.makeViewGoneOrVisible(true)

            //changes RecyclerViews orientation to horizontal
            binding.activityMainRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }
    }
    //removes all of circle views from RelativeLayout and RecyclerViews list
    private fun clearScreen() {
        //removes all of circle views RecyclerViews list
        listItems.removeAll(listItems)
        customViewAdapter.notifyDataSetChanged()

        //makes RelativeLayout visible and RecyclerView gone
        viewModel.makeViewGoneOrVisible(false)
        viewModel.clearScreenDone()
    }
    //adds new circle view to RelativeLayout
    fun addView() {
        val view = layoutInflater.inflate(R.layout.circle_item, null)
        // Here you can access all views inside the child layout and assign them values
        circleView = view.findViewById(R.id.circle_view)
        view.layoutParams = RelativeLayout.LayoutParams(150, 50).apply {
            width = 120
            height = 120
            leftMargin = (10..990).random()
            topMargin = (10..990).random()
            bottomMargin = -250
            rightMargin = -250
        }
        view.setOnTouchListener(this)
        binding.activityMainRelativeLayout.addView(view)
    }

    //on touch and drag and drop configs
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val X = event.rawX.toInt()
        val Y = event.rawY.toInt()
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                view.bringToFront();
                val lParams = view.layoutParams as RelativeLayout.LayoutParams
                _xDelta = X - lParams.leftMargin
                _yDelta = Y - lParams.topMargin
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
            }
            MotionEvent.ACTION_POINTER_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
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
    //sets up RecyclerView's first attributes
    private fun setupRecyclerView() = binding.activityMainRecyclerView.apply {
        customViewAdapter = CustomAdapter(listItems)

        adapter = customViewAdapter

        layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun onDestroy() {
        //set binding to null to reduce memory leaks
        _binding=null
        super.onDestroy()
    }
}