package ir.es.mohammad.netflix

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import ir.es.mohammad.netflix.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val homeMovieViewModel: HomeMovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val layoutManager = GridLayoutManager(requireContext(), 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = 1
        }
        binding.recyclerViewHomeMovies.layoutManager = layoutManager
        val movieAdapter = MovieRecyclerAdapter(ArrayList(homeMovieViewModel.movies))
        movieAdapter.setEachItem = { movie ->
            val drawable =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.channels4_profile,
                    requireContext().theme
                )!!
            movieImg.setImageDrawable(drawable)
            movieTitle.text = movie.title
            actionImgBtn.setMovieActionButton(movie)
        }
        binding.recyclerViewHomeMovies.adapter = movieAdapter
    }

    private fun ImageButton.setMovieActionButton(movie: Movie) {
        if (movie.isFavorite)
            setImageResource(R.drawable.ic_baseline_favorite_24)
        else
            setImageResource(R.drawable.ic_outline_favorite)

        setOnClickListener {
            if (userViewModel.registered.value!!) {
                if (movie.isFavorite) {
                    userViewModel.removeMovieFromFavorite(movie)
                    setImageResource(R.drawable.ic_outline_favorite)
                } else {
                    userViewModel.addMovieToFavorite(movie)
                    setImageResource(R.drawable.ic_baseline_favorite_24)
                }
            } else
                showRegisterSnackbar()
        }
    }

    private fun showRegisterSnackbar() {
        Snackbar.make(binding.root, "First You must register", Snackbar.LENGTH_LONG).apply {
            setAction("Register") {
                val bottomNavigationView =
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
                bottomNavigationView.selectedItemId = R.id.showInfoFragment
            }
        }.show()
    }
}