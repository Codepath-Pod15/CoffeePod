package com.example.coffeepod.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.coffeepod.*
import com.example.coffeepod.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.parse.*
import java.io.File
import java.io.IOException

class ComposeFragment : Fragment() {

    val PICK_PHOTO_CODE = 1046
    var photoFile : ParseFile? = null

    lateinit var ivReviewPicture : ImageView
    lateinit var spinnerLocation : Spinner
    lateinit var etOrder: EditText
    lateinit var etReview: EditText
    lateinit var cgTags : ChipGroup
    lateinit var ratingBar: RatingBar
    lateinit var btnSubmit : Button
    lateinit var btnUploadPhoto : Button

    val tagMap = mutableMapOf<String, Tag>()
    val locationMap = mutableMapOf<String, Location>()
    val chipList = mutableListOf<Chip>()

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
        ratingBar = view.findViewById(R.id.ratingBar)
        btnSubmit = view.findViewById(R.id.btnSubmit)
        btnUploadPhoto = view.findViewById(R.id.btnUploadPhoto)

        btnUploadPhoto.setOnClickListener {
            onPickPhoto(view)
        }

        btnSubmit.setOnClickListener {
            submitReview(view)
        }

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
                                chipList.add(chip)
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

    fun onPickPhoto(view: View?) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_PHOTO_CODE)
    }

    fun loadFromUri(photoUri: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) {
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(requireContext().contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    fun uriToImageFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireContext().contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && requestCode == PICK_PHOTO_CODE) {
            val photoUri: Uri? = data.data
            if (photoUri != null) {
                val selectedImage = loadFromUri(photoUri)
                ivReviewPicture.setImageBitmap(selectedImage)
                photoFile = ParseFile(uriToImageFile(photoUri))
            }
        }
    }

    fun submitReview(view : View) {
        val review = Review()

        if (photoFile == null) {
            Log.e(TAG, "Photo file is null")
            Toast.makeText(requireContext(), "Must upload a picture to submit a review", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedTags = mutableListOf<Tag>()
        for (chip in chipList) {
            if (chip.isChecked) {
                val tag = tagMap[chip.text]
                if (tag != null) {
                    selectedTags.add(tag)
                }
            }
        }

        review.setImage(photoFile!!)
        review.setLocation(locationMap[spinnerLocation.selectedItem]!!)
        review.setUser(ParseUser.getCurrentUser() as User)
        review.setOrder(etOrder.text.toString())
        review.setReviewText(etReview.text.toString())
        review.setTags(selectedTags)
        review.setRating(ratingBar.rating.toInt())

        review.saveInBackground { exception ->
            if (exception != null) {
                Log.e(ProfileFragment.TAG, "Error while saving review")
                exception.printStackTrace()
                Toast.makeText(requireContext(), "Error while saving review", Toast.LENGTH_SHORT).show()
            } else {
                Log.i(ProfileFragment.TAG, "Successfully saved review")
                Toast.makeText(requireContext(), "Review successfully posted", Toast.LENGTH_SHORT).show()

                spinnerLocation.setSelection(0)
                Glide.with(view.context).load(R.drawable.coffeepodlogo).into(ivReviewPicture)
                photoFile = null
                for (chip in chipList) {
                    chip.isChecked = false
                }
                etOrder.setText("")
                etReview.setText("")
                ratingBar.rating = 0.0F
            }
        }
    }

    companion object {
        const val TAG = "ComposeFragment"
    }
}