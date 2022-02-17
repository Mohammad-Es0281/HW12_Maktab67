package ir.es.mohammad.netflix

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    val registered: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }
    val profileImgDrawable: MutableLiveData<Drawable> by lazy { MutableLiveData<Drawable>() }
    val fullName: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val email: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val username: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val phoneNumber: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val favoriteMovies = HashSet<Movie>()

    fun addMovieToFavorite(movie: Movie){
        movie.isFavorite = true
        favoriteMovies.add(movie)
    }

    fun removeMovieFromFavorite(movie: Movie){
        movie.isFavorite = false
        favoriteMovies.remove(movie)
    }
}