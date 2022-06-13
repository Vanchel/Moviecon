package com.vanchel.moviecon.presentation.screens

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vanchel.moviecon.R
import com.vanchel.moviecon.data.network.IMAGES_URL
import com.vanchel.moviecon.databinding.FragmentPersonBinding
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.domain.entities.PersonDetails
import com.vanchel.moviecon.presentation.adapters.CreditsPanelAdapter
import com.vanchel.moviecon.presentation.utils.Resource
import com.vanchel.moviecon.presentation.utils.navigate
import com.vanchel.moviecon.presentation.viewmodels.PersonViewModel
import com.vanchel.moviecon.util.SIZE_PROFILE_LARGE
import com.vanchel.moviecon.util.extensions.title
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Period
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий подробную информацию о человеке.
 */
@AndroidEntryPoint
class PersonFragment : Fragment() {
    private var _binding: FragmentPersonBinding? = null
    private val binding: FragmentPersonBinding
        get() = _binding!!

    private val args: PersonFragmentArgs by navArgs()

    /**
     * Фабрика для создания [PersonViewModel].
     */
    @Inject
    lateinit var personViewModelFactory: PersonViewModel.Factory
    private val personViewModel: PersonViewModel by viewModels {
        PersonViewModel.provideFactory(personViewModelFactory, args.personId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setViewModelObservers()
        setOnClickListeners()
        setUpCreditsRecyclerView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_round_arrow_back_24)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }
        binding.collapsingToolbarLayout.title = args.personName
    }

    private fun setViewModelObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    personViewModel.personDetailsResource.collect { resource ->
                        if (resource is Resource.Success) {
                            resource.data?.let(::bindPersonDetailsData)
                        }
                        binding.run {
                            sectionContent.isVisible = resource is Resource.Success
                            sectionLoading.root.isVisible = resource is Resource.Loading
                            sectionError.root.isVisible = resource is Resource.Error
                        }
                    }
                }
                launch {
                    personViewModel.selectedMovie.collect { movie ->
                        movie?.let {
                            navigate(
                                PersonFragmentDirections.personToMovie(
                                    movieId = movie.id,
                                    movieName = movie.title
                                )
                            )
                            personViewModel.selectedMovieProcessed()
                        }
                    }
                }
                launch {
                    personViewModel.selectedTv.collect { tv ->
                        tv?.let {
                            navigate(
                                PersonFragmentDirections.personToTv(
                                    tvId = tv.id,
                                    tvName = tv.title
                                )
                            )
                            personViewModel.selectedTvProcessed()
                        }
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            textFilmography.setOnClickListener {
                navigate(
                    PersonFragmentDirections.personToFilmography(
                        personId = args.personId,
                        personName = args.personName
                    )
                )
            }
            sectionError.buttonRetry.setOnClickListener { personViewModel.reload() }
        }
    }

    private fun setUpCreditsRecyclerView() {
        binding.recyclerViewCredits.adapter =
            CreditsPanelAdapter(object : CreditsPanelAdapter.ItemCallback {
                override fun onItemSelected(item: Credit) {
                    personViewModel.viewCredit(item)
                }
            })
    }

    private fun bindPersonDetailsData(personDetails: PersonDetails) {
        binding.apply {
            personDetails.profilePath?.let {
                val url = "$IMAGES_URL$SIZE_PROFILE_LARGE$it"
                Glide.with(requireView())
                    .load(url).placeholder(R.drawable.ic_round_person_24).into(avatar)
            }
            fabInstagram.run {
                isVisible = personDetails.instagramId != null
                personDetails.instagramId?.let(::openInstagramProfile)
            }
            textBirthday.text = formatBirthDay(personDetails.birthday, personDetails.deathDay)
            textPlaceOfBirth.text = personDetails.placeOfBirth
                ?: getString(R.string.birth_place_none)
            textSex.setText(personDetails.gender.title)
            textBio.text = personDetails.biography
            (recyclerViewCredits.adapter as CreditsPanelAdapter?)
                ?.submitList(personDetails.getTopCredits())
        }
    }

    private fun formatBirthDay(birth: Date?, death: Date?): String {
        return birth?.let { b ->
            val end = death ?: Date()
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            var str = dateFormat.format(b)
            death?.let { d ->
                str += " - ${dateFormat.format(d)}"
            }
            val age = Period(DateTime(b), DateTime(end)).years
            val plural = resources.getQuantityString(R.plurals.age, age, age)
            str += " ($plural)"
            str
        } ?: getString(R.string.date_unknown)
    }

    private fun openInstagramProfile(instagramId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        try {
            val pm = requireActivity().packageManager
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                intent.apply {
                    data = Uri.parse("http://instagram.com/_u/$instagramId")
                    `package` = "com.instagram.android"
                }
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        intent.data = Uri.parse("http://instagram.com/$instagramId")
        startActivity(intent)
    }
}