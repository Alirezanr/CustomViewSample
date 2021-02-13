package dan.nr.customviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter2 (private val listItems: ArrayList<Int>) : RecyclerView.Adapter<CustomAdapter2.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val circleView: CircleView=view.findViewById(R.id.circle_view)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val context = viewGroup.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.circle_item, viewGroup, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val circleView = viewHolder.circleView
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listItems.size

}