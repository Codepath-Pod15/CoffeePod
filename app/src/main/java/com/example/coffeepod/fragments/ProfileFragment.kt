package com.example.coffeepod.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.coffeepod.R
import com.example.coffeepod.User
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.File
import java.io.IOException

class ProfileFragment : Fragment() {

    val PICK_PHOTO_CODE = 1046
    var photoFile : ParseFile? = null

    lateinit var ivProfile: ImageView
    lateinit var tvUsername: TextView
    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etLocation: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivProfile = view.findViewById(R.id.ivProfile)
        tvUsername = view.findViewById(R.id.tvUsername)
        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.etEmail)
        etLocation = view.findViewById(R.id.etLocation)

        view.findViewById<Button>(R.id.btnUploadPhoto).setOnClickListener {
            onPickPhoto(view)
        }

        view.findViewById<Button>(R.id.btnUpdateProfile).setOnClickListener {
            onUpdateProfile()
        }

        showCurrentProfile(view)
    }

    private fun showCurrentProfile(view: View) {
        val user : User = ParseUser.getCurrentUser() as User

        photoFile = user.getProfilePicture()
        if (photoFile == null) {
            Glide.with(view.context).load(R.drawable.coffeepodlogo).into(ivProfile)
        } else {
            Glide.with(view.context).load(photoFile?.url).into(ivProfile)
        }

        tvUsername.setText("#" + user.username)
        etName.setText(user.getName())
        etEmail.setText(user.email)
        etLocation.setText(user.getLocation().toString())
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
                ivProfile.setImageBitmap(selectedImage)
                photoFile = ParseFile(uriToImageFile(photoUri))
            }
        }
    }

    private fun onUpdateProfile() {
        val user = ParseUser.getCurrentUser() as User

        if (photoFile != null) {
            user.setProfilePicture(photoFile!!)
        }
        user.setName(etName.text.toString())
        user.email = etEmail.text.toString()
        var zipcode = etLocation.text.toString()
        if (zipcode.length == 5) {
            user.setLocation(zipcode.toInt())
        }

        user.saveInBackground { exception ->
            if (exception != null) {
                Log.e(TAG, "Error while saving profile information")
                exception.printStackTrace()
                Toast.makeText(requireContext(), "Error while saving profile information", Toast.LENGTH_SHORT).show()
            } else {
                Log.i(TAG, "Successfully saved post")
                Toast.makeText(requireContext(), "Profile information successfully updated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val TAG = "ProfileFragment"
    }

}