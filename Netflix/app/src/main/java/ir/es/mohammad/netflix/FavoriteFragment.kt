package ir.es.mohammad.netflix

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import ir.es.mohammad.netflix.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        val layoutManager = GridLayoutManager(requireContext(), 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = 1
        }
        binding.recyclerViewFavoriteMovies.layoutManager = layoutManager

        val movieAdapter = MovieRecyclerAdapter(ArrayList(userViewModel.favoriteMovies))
        movieAdapter.setEachItem = { movie ->
            val drawable =
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.channels4_profile,
                        requireContext().theme
                    )!!
            movieImg.setImageDrawable(drawable)
            movieTitle.text = movie.title
            actionImgBtn.setImageResource(R.drawable.ic_baseline_remove_24)
            actionImgBtn.setOnClickListener {
                movieAdapter.remove(adapterPosition)
                userViewModel.removeMovieFromFavorite(movie)
            }
        }
        binding.recyclerViewFavoriteMovies.adapter = movieAdapter
    }
}