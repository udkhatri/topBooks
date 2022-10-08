package com.uday.topbooks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.lang.String

class RecyclerAdapter(private val dataSet: List<Books>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    lateinit var SubjectValues: Array<kotlin.String>
    //    lateinit var personArrayList: ArrayList<Person>
    lateinit var context: Context
    lateinit var view1: View
    lateinit var viewHolder1: ViewHolder
    lateinit var textView: TextView

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var textViewRank: TextView
        lateinit var textViewTitle: TextView
        lateinit var textViewAuthor: TextView
        lateinit var textViewDescription: TextView
        lateinit var textViewISBN: TextView
        lateinit var imageView: ImageView
        init {
            // Define click listener for the ViewHolder's View.
            textViewRank = view.findViewById<TextView>(R.id.textViewRank)
            textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)
            textViewAuthor = view.findViewById<TextView>(R.id.textViewAuthor)
            textViewDescription = view.findViewById<TextView>(R.id.textViewDescription)
            textViewISBN = view.findViewById(R.id.textViewisbn)
            imageView = view.findViewById(R.id.imageView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)

        // add the 3 lines of code below to show 5 recycler items in the activity at a time
        val lp = view.getLayoutParams()
        view.setLayoutParams(lp)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

//        holder.textView.setText(SubjectValues[position]);
        viewHolder.textViewRank.setText(String.valueOf(dataSet.get(position).rank))
        viewHolder.textViewAuthor.setText(dataSet.get(position).author)
        viewHolder.textViewTitle.setText(dataSet.get(position).title)
        viewHolder.textViewISBN.setText(dataSet.get(position).primary_isbn10)
        viewHolder.textViewDescription.setText(dataSet.get(position).description)
        Picasso.get().load(dataSet.get(position).book_image).into(viewHolder.imageView);
//        viewHolder.imageView.setImageResource(dataSet.get(position).book_image)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount():Int {
        return dataSet.size
    }

}