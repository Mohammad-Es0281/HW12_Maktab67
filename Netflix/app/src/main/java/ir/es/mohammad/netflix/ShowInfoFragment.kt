package ir.es.mohammad.netflix

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import ir.es.mohammad.netflix.databinding.FragmentShowInfoBinding
import java.util.Observer

class ShowInfoFragment : Fragment(R.layout.fragment_show_info) {

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentShowInfoBinding.bind(view)

        binding.user = userViewModel

        val navController = findNavController()
        userViewModel.registered.observe(this){ registered ->
            if (!registered) {
                val startDestination = navController.graph.startDestinationId
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(startDestination, false)
                    .build()
                navController.navigate(R.id.registerFragment, null, navOptions)
            }
        }
    }
}