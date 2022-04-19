package com.example.coffeepod

import android.util.Log
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

    fun getTags(): MutableList<Tag>? {
        val relation = getRelation<Tag>(KEY_TAGS)
        val query: ParseQuery<Tag> = relation.getQuery()
        return query.find()
    }

    fun getTagsName(): MutableList<String> {
        val results = mutableListOf<String>()
        val relation = getRelation<Tag>(KEY_TAGS)
        val query: ParseQuery<Tag> = relation.getQuery()
        val tags = query.find()
        for (item in tags) {
            results.add(item.getName().toString())
        }
        return results
    }

    fun setTags(tags : MutableList<Tag>) {
        val relation : ParseRelation<Tag> = this.getRelation(KEY_TAGS)
        for (tag in tags) {
            relation.add(tag)
        }
        saveInBackground()
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