package ir.es.mohammad.netflix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class ComingSoonViewModel : ViewModel() {
    private val client by lazy { OkHttpClient() }
    val comingSoonMovies: HashSet<Movie> by lazy { readUserJSON(getJsonComingSoonMovies()) }
    val errorOccurred: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }

    private fun getJsonComingSoonMovies(): String {
        var json = ""
        val request = Request.Builder()
            .url("https://imdb-api.com/en/API/ComingSoon/k_d6pbw34n").build()
        try {
            val response = client.newCall(request).execute()
            if (response.body() == null)
                errorOccurred.value = true
            else
                json = response.body()?.string() ?: ""
        } catch (ex: IOException) {
            errorOccurred.value = true
        }
        return json.dropWhile { it != '[' }.dropLastWhile { it != ']' }
    }

    private fun readUserJSON(JSON: String): HashSet<Movie> {
        val gson = GsonBuilder().setExclusionStrategies(getStrategy()).create()
        return gson.fromJson(JSON, Array<Movie>::class.java).toHashSet()
    }

    private fun getStrategy() = object : ExclusionStrategy {
        override fun shouldSkipClass(clazz: Class<*>?) = false

        override fun shouldSkipField(field: FieldAttributes): Boolean {
            fun skipAll(vararg skipWords: String): Boolean = skipWords.any { field.name == it }
            return skipAll(
                "Id",
                "FullTitle",
                "Year",
                "ReleaseState",
                "RuntimeMins",
                "RuntimeStr",
                "Plot",
                "ContentRating",
                "IMDbRating",
                "IMDbRatingCount",
                "MetacriticRating",
                "Genres",
                "GenreList",
                "Directors",
                "DirectorList",
                "Stars",
                "StarList"
            )
        }
    }
}