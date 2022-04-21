package com.example.coffeepod

import android.os.Parcelable
import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseUser
import kotlinx.parcelize.Parcelize

@Parcelize
class User : ParseUser() {

    fun getProfilePicture(): ParseFile? {
        return getParseFile(KEY_PROFILEPICTURE)
    }

    fun setProfilePicture(imgFile: ParseFile) {
        put(KEY_PROFILEPICTURE, imgFile)
    }

    fun getLocation(): Int? {
        return getInt(KEY_LOCATION)
    }

    fun setLocation(location: Int) {
        put(KEY_LOCATION, location)
    }

    fun getName(): String? {
        return getString(KEY_NAME)
    }

    fun setName(name: String) {
        put(KEY_NAME, name)
    }


    companion object {
        const val KEY_PROFILEPICTURE = "profilePicture"
        const val KEY_LOCATION = "location"
        const val KEY_NAME = "name"
    }

}