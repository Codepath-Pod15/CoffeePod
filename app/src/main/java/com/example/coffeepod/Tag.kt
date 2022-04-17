package com.example.coffeepod

import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("Tag")
class Tag : ParseObject() {

    fun getName(): String? {
        return getString(KEY_NAME)
    }

    fun setName(name: String) {
        put(KEY_NAME, name)
    }

    companion object {
        const val KEY_NAME = "name"
    }
}