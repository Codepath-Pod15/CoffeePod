package com.example.coffeepod.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeepod.PostAdapter
import com.example.coffeepod.R
import com.example.coffeepod.Review
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

class FeedFragment : Fragment() {

    lateinit var postsRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter
    var allReviews: MutableList<Review> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postsRecyclerView = view.findViewById(R.id.postRecyclerView)
        adapter = PostAdapter(requireContext(), allReviews)
        postsRecyclerView.adapter = adapter
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        queryReviews()
    }

    open fun queryReviews() {
        val query: ParseQuery<Review> = ParseQuery.getQuery(Review::class.java)

        query.include(Review.KEY_USER)
        query.include(Review.KEY_LOCATION)
        query.include(Review.KEY_TAGS)
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Review> {
            override fun done(reviews: MutableList<Review>?, e: ParseException?) {
                if (e != null) {
                    Log.e("QueryReviews", "Error fetching posts")
                } else {
                    if (reviews != null) {
                        for (review in reviews) {
                            val tags = review.getTags()
                            Log.i("QueryReviews", "Post by: " + review.getUser()?.getUsername() + " for " + review.getLocation()?.getAddress() + " Tags " + review.getTagsName())
                        }

                        allReviews.addAll(reviews)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

}