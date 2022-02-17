package ir.es.mohammad.netflix

import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import ir.es.mohammad.netflix.databinding.FragmentComingSoonBinding
import java.util.concurrent.Executors

class ComingSoonFragment : Fragment(R.layout.fragment_coming_soon) {

    private lateinit var binding: FragmentComingSoonBinding
    private val comingSoonViewModel: ComingSoonViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val executor by lazy { Executors.newSingleThreadExecutor() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentComingSoonBinding.bind(view)

        comingSoonViewModel.errorOccurred.observe(this) {
            if (it!!)
                Toast.makeText(
                    requireContext(),
                    "an error occurred, check your network",
                    Toast.LENGTH_LONG
                ).show()
        }

        binding.recyclerViewComingSoonMovies.layoutManager = LinearLayoutManager(requireContext())

        executor.submit {
            Log.d("Before", "After getting movies")
            val movieAdapter = MovieRecyclerAdapter(ArrayList(comingSoonViewModel.comingSoonMovies))
            Log.d("TAGGG", "After getting movies")
            movieAdapter.setEachItem = { movie ->
                setDrawableByURL(movieImg, movie.image)
                movieTitle.text = movie.title
                actionImgBtn.setMovieActionButton(movie)
            }
            Handler(Looper.getMainLooper()).post {
                binding.recyclerViewComingSoonMovies.adapter = movieAdapter
                Log.d("TAGGG", "After setting Adapter")
            }
        }
    }

    private fun setDrawableByURL(imageView: ImageView, url: String) {
        Glide.with(this).load(url).placeholder(R.drawable.loading_animation).into(imageView)
    }

    private fun ImageButton.setMovieActionButton(movie: Movie) {
        setImageResource(R.drawable.ic_baseline_share_24)
        setOnClickListener {
            if (userViewModel.registered.value!!)
                context.startActivity(createShareText(movie.title))
            else
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

    private fun createShareText(text: String): Intent {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(EXTRA_TEXT, text)
        }
        return intent
    }
}