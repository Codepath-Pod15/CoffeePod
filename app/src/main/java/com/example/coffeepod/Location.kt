package com.example.coffeepod

import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("Location")
class Location : ParseObject() {

    fun getAddress(): String? {
        return getString(KEY_ADDRESS)
    }

    fun setAddress(address: String) {
        put(KEY_ADDRESS, address)
    }

    fun getName(): String? {
        return getString(KEY_NAME)
    }

    fun setName(name: String) {
        put(KEY_NAME, name)
    }

    companion object {
        const val KEY_ADDRESS = "address"
        const val KEY_NAME = "name"
    }
}