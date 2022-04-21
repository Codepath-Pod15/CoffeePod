package com.example.coffeepod.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.coffeepod.Location
import com.example.coffeepod.R
import com.example.coffeepod.Review
import com.example.coffeepod.Tag
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

class ComposeFragment : Fragment() {

    lateinit var ivReviewPicture : ImageView
    lateinit var spinnerLocation : Spinner
    lateinit var etOrder: EditText
    lateinit var etReview: EditText
    lateinit var cgTags : ChipGroup
    lateinit var btnSubmit : Button
    lateinit var btnTakePicture : Button

    val tagMap = mutableMapOf<String, Tag>()
    val locationMap = mutableMapOf<String, Location>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivReviewPicture = view.findViewById(R.id.ivReviewPicture)
        spinnerLocation = view.findViewById(R.id.spinnerLocation)
        etOrder = view.findViewById(R.id.etOrder)
        etReview = view.findViewById(R.id.etReview)
        cgTags = view.findViewById(R.id.cgTags)
        btnSubmit = view.findViewById(R.id.btnSubmit)
        btnTakePicture = view.findViewById(R.id.btnTakePicture)

        populateTags(view)
        populateLocations(view)
    }

    fun populateTags(view : View) {
        val query: ParseQuery<Tag> = ParseQuery.getQuery(Tag::class.java)
        query.addAscendingOrder("name")
        query.findInBackground(object : FindCallback<Tag> {
            override fun done(tags: MutableList<Tag>?, e: ParseException?) {
                if (e != null) {
                    Log.e("populateTags", "Error fetching posts")
                } else {
                    if (tags != null) {
                        for (tag in tags) {
                            val name = tag.getName()
                            if (name != null) {
                                tagMap[name.toString()] = tag

                                var chip = Chip(view.context)
                                chip.isCheckable = true
                                chip.text = name.toString()
                                chip.setChipBackgroundColorResource(R.color.backgroundColor)
                                chip.setTextColor(view.resources.getColor(R.color.white))
                                cgTags.addView(chip)
                            }
                        }
                    }
                }
            }
        })
    }

    fun populateLocations(view : View) {
        val query: ParseQuery<Location> = ParseQuery.getQuery(Location::class.java)
        query.addAscendingOrder("name")
        query.findInBackground(object : FindCallback<Location> {
            override fun done(locations: MutableList<Location>?, e: ParseException?) {
                if (e != null) {
                    Log.e("populateLocations", "Error fetching posts")
                } else {
                    if (locations != null) {
                        val locationNames = mutableListOf<String>()
                        for (location in locations) {
                            val name = location.getName()
                            if (name != null) {
                                locationNames.add(name.toString())
                                locationMap.put(name.toString(), location)
                            }
                        }
                        val spinnerAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, locationNames)
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerLocation.adapter = spinnerAdapter
                    }
                }
            }
        })
    }
}