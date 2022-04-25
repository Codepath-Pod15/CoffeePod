package com.example.coffeepod

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

class DetailActivity : AppCompatActivity() {


    private lateinit var rbVoteAverage: RatingBar
    private lateinit var tvAddress: TextView
    private lateinit var tvTags: TextView
    private lateinit var tvName: TextView
    private lateinit var ivPhotos: ImageView
    private lateinit var adapter: ReviewAdapter
    var reviewArray: MutableList<Review> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val review = intent.getParcelableExtra<Review>("Review_Extra")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.backgroundColor)))

        // Gets Layout data
        rbVoteAverage = findViewById(R.id.rbVoteAverage)
        tvAddress = findViewById(R.id.tvAddress)
        tvTags = findViewById(R.id.tvTags)
        tvName = findViewById(R.id.tvName)
        ivPhotos = findViewById(R.id.ivPhotos)
        val results = mutableListOf<String>()


        /// Adapter Set up
        var rvReviews = findViewById<View>(R.id.rvReviews) as RecyclerView
        adapter = ReviewAdapter(this, reviewArray)
        rvReviews.adapter = adapter
        rvReviews.layoutManager = LinearLayoutManager(this)

        // Testing Line
        var locationID = review?.getLocation()
        queryReviews(locationID, reviewArray)


        // Update Layout data
        tvName.text = review?.getLocation()?.getName()
        tvAddress.text = review?.getLocation()?.getAddress()
        rbVoteAverage.rating = review?.getRating()?.toFloat()!!
        review.getTags(object : FindCallback<Tag> {
            override fun done(tags: MutableList<Tag>?, e: ParseException?) {
                if (e != null) {
                    Log.e("QueryReviews", "Error fetching posts")
                } else {
                    if (tags != null) {
                        for (item in tags) {
                            results.add(item.getName().toString())
                        }
                        tvTags.text = results.joinToString(", ")
                    }
                }
            }
        })

        Glide.with(this).load(review.getImage()?.url).into(ivPhotos)
        }


    open fun queryReviews(locationID: Location?, reviewArray: MutableList<Review>) {
        // Class
        val query: ParseQuery<Review> = ParseQuery.getQuery(Review::class.java)


        // Reviews
        query.whereEqualTo("location", locationID)
        query.findInBackground(object : FindCallback<Review> {
            override fun done(reviews: MutableList<Review>?, e: ParseException?) {
                if (e != null) {
                    Log.e("QueryReviews", "Error fetching posts")
                } else {
                    if (reviews != null) {
                        reviewArray.addAll(reviews)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }


}
