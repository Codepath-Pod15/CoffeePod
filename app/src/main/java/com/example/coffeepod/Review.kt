package com.example.coffeepod

import com.parse.*
import org.json.JSONArray

@ParseClassName("Review")
class Review : ParseObject() {

    fun getReviewText(): String? {
        return getString(KEY_REVIEWTEXT)
    }

    fun setReviewText(reviewText: String) {
        put(KEY_REVIEWTEXT, reviewText)
    }

    fun getOrder(): String? {
        return getString(KEY_ORDER)
    }

    fun setOrder(order: String) {
        put(KEY_ORDER, order)
    }

    fun getLocation(): Location? {
        return getParseObject(KEY_LOCATION) as Location?
    }

    fun setLocation(location: Location) {
        put(KEY_LOCATION, location)
    }

    fun getTags(): JSONArray? {
        return getJSONArray(KEY_TAGS)
    }

    fun setTags(tags: JSONArray) {
        put(KEY_TAGS, tags)
    }

    fun getRating(): Int? {
        return getInt(KEY_RATING)
    }

    fun setRating(rating: Int) {
        put(KEY_RATING, rating)
    }

    fun getUser(): User? {
        return getParseUser(KEY_USER) as User?
    }

    fun setUser(user: User) {
        put(KEY_USER, user)
    }

    fun getImage(): ParseFile? {
        return getParseFile(KEY_IMAGE)
    }

    fun setImage(imgFile: ParseFile) {
        put(KEY_IMAGE, imgFile)
    }

    companion object {
        const val KEY_REVIEWTEXT = "reviewText"
        const val KEY_ORDER = "order"
        const val KEY_LOCATION = "location"
        const val KEY_TAGS = "tags"
        const val KEY_RATING = "rating"
        const val KEY_IMAGE = "image"
        const val KEY_USER = "user"
    }
}