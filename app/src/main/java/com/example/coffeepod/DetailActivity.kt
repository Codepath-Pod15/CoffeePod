package com.example.coffeepod

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.parse.FindCallback
import com.parse.ParseException

class DetailActivity : AppCompatActivity() {


    private lateinit var rbVoteAverage: RatingBar
    private lateinit var tvAddress: TextView
    private lateinit var tvTags: TextView
    private lateinit var tvName: TextView
    private lateinit var ivPhotos: ImageView


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

        // Testing Line
        Log.i("TESTING", "review is ${review?.getLocation()?.getName()}")

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



    }