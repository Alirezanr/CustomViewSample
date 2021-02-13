package dan.nr.customviews

import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dan.nr.customviews.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    lateinit var _root: RelativeLayout
    private lateinit var circleView: CircleView

    private var _xDelta = 0
    private var _yDelta = 0

    private val TAG = "LOG_TAG"
    private lateinit var binding: ActivityMainBinding

    private lateinit var customViewAdapter: CustomAdapter2

    var listItems = ArrayList<Int>()

    @RequiresApi(Build.VERSION_CODES.N)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainViewModel = viewModel
        _root = binding.root
        setupRecyclerView()
        viewModel.viewNumber.observe(this, Observer { viewNumber ->
            if (viewNumber > 0) {
                addView()
                listItems.add(1)
                customViewAdapter.notifyDataSetChanged()
            }
        })
        viewModel.clearScreen.observe(this, Observer {
            if (it == true) {
                if ((_root).childCount > 0)
                    binding.root.removeAllViews()
                clearScreen()
            }
        })
        binding.activityMainBtnAlignVertically.setOnClickListener {
            _root.visibility = View.GONE
            binding.activityMainRecyclerView.visibility = View.VISIBLE
            binding.activityMainRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.activityMainBtnAlignHorizontally.setOnClickListener {
            _root.visibility = View.GONE
            binding.activityMainRecyclerView.visibility = View.VISIBLE
            binding.activityMainRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun clearScreen() {
        listItems.removeAll(listItems)
        customViewAdapter.notifyDataSetChanged()
        _root.visibility = View.GONE
        viewModel.clearScreen()
        viewModel.clearScreenDone()
    }

    fun addView() {
        val view = layoutInflater.inflate(R.layout.circle_item, null)
        // Here you can access all views inside your child layout and assign them values
        circleView = view.findViewById(R.id.circle_view)
        view.layoutParams = RelativeLayout.LayoutParams(150, 50).apply {
            width = 120
            height = 120
            leftMargin = 50
            topMargin = 50
            bottomMargin = -250
            rightMargin = -250
        }
        view.setOnTouchListener(this)
        _root.addView(view)
    }


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
        _root.invalidate()
        return true
    }

    private fun setupRecyclerView() = binding.activityMainRecyclerView.apply {
        listItems = getSomeIntegers(0)
        customViewAdapter = CustomAdapter2(listItems)

        adapter = customViewAdapter
        layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    fun getSomeIntegers(numContacts: Int): ArrayList<Int> {
        val items = ArrayList<Int>()
        for (i in 1..numContacts) {
            items.add(i)
        }
        return items
    }
}