package com.example.coffeepod

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.parse.FindCallback
import com.parse.ParseException

class PostAdapter (val context: Context, val reviews: List<Review>):RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val shopImage: ImageView
        val shopName: TextView
        val shopRating: RatingBar
        val shopTags: TextView

        init {
            shopImage = itemView.findViewById(R.id.shopImage)
            shopName = itemView.findViewById(R.id.shopName)
            shopTags = itemView.findViewById(R.id.shopTags)
            shopRating = itemView.findViewById(R.id.shopRating)
            itemView.setOnClickListener(this)
        }

        fun bind(review: Review) {
            shopName.text = review.getLocation()?.getName()
            shopRating.rating = review.getRating()?.toFloat()!!

            val results = mutableListOf<String>()
            review.getTags(object : FindCallback<Tag> {
                override fun done(tags: MutableList<Tag>?, e: ParseException?) {
                    if (e != null) {
                        Log.e("QueryReviews", "Error fetching posts")
                    } else {
                        if (tags != null) {
                            for (item in tags) {
                                results.add(item.getName().toString())
                            }
                            shopTags.text = results.joinToString(", ")
                        }
                    }
                }
            })

            Glide.with(itemView.context).load(review.getImage()?.url).into(shopImage)
        }

        override fun onClick(v: View?) {
            // Get which shop was clicked
            val review = reviews[adapterPosition]
            // Navigation to detail pages
            // Toast.makeText(context, review.getLocation()?.getName(), Toast.LENGTH_SHORT).show()
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Review_Extra", review)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val review = reviews.get(position)
        holder.bind(review)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }


}