package ir.es.mohammad.netflix

import androidx.lifecycle.ViewModel

class HomeMovieViewModel: ViewModel() {
     val movies: MutableSet<Movie> = mutableSetOf()

     init { (1..21).forEach { movies.add(Movie("Movie $it", "")) } }
}