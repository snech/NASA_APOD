package com.example.nasa.mvvm.view

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.nasa.R
import com.example.nasa.base.observe
import com.example.nasa.databinding.FragmentHomeBinding
import com.example.nasa.mvvm.NasaUtil
import com.example.nasa.mvvm.viewmodel.HomeViewModel
import com.example.nasa.mvvm.viewmodel.HomeViewModelIntent
import com.example.nasa.mvvm.viewmodel.HomeViewModelState
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.properties.Delegates


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var mCurrentYear by Delegates.notNull<Int>()
    private var mCurrentMonth by Delegates.notNull<Int>()
    private var mCurrentDay by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        mCurrentYear = calendar.get(Calendar.YEAR)
        mCurrentMonth = calendar.get(Calendar.MONTH) + 1
        mCurrentDay = calendar.get(Calendar.DAY_OF_MONTH)

        getPictureOfTheDay()
        observeEvents()
        clickListeners()
    }

    private fun getPictureOfTheDay(
        curYear: Int = mCurrentYear,
        curMonth: Int = mCurrentMonth,
        curDay: Int = mCurrentDay
    ) {
        viewModel.sendIntent(HomeViewModelIntent.LoadAPOD("$curYear-$curMonth-$curDay"))
    }

    private fun clickListeners() {
        binding.tvSearchDate.setOnClickListener {
            showCalendarViewDialog()
        }

        binding.ltApodMainView.ivImageOfDay.setOnClickListener {
            binding.resultApod?.let {
                if (it.isVideo) {
                    NasaUtil.openBrowserToPlayVideo(it.videoLinkUrl, requireContext())
                }
            }
        }

        binding.ltApodMainView.ivAddToFav.setOnClickListener {
            binding.resultApod?.let {
                viewModel.sendIntent(HomeViewModelIntent.InsertFavData(it))
            }
        }

        binding.tvGoToFav.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                add(R.id.container, FavFragment()).addToBackStack(null)
            }
        }
    }

    private fun showCalendarViewDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val customView =
            LayoutInflater.from(requireContext()).inflate(R.layout.layout_calender_view, null)
        dialogBuilder.setView(customView)
        var selectedYear = 0
        var selectedMonth = 0
        var selectedDay = 0
        val calenderView = customView.findViewById<CalendarView>(R.id.my_calender_view)
        calenderView.maxDate = Calendar.getInstance().timeInMillis
        calenderView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedYear = year
            selectedMonth = month + 1
            selectedDay = dayOfMonth
        }
        dialogBuilder.create().apply {
            this.setButton(
                DialogInterface.BUTTON_POSITIVE, getString(R.string.text_search)
            ) { _, _ ->
                if (selectedYear != 0 && selectedMonth != 0 && selectedDay != 0) {
                    getPictureOfTheDay(
                        curYear = selectedYear,
                        curMonth = selectedMonth,
                        curDay = selectedDay
                    )
                    this.dismiss()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.please_select_a_date),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            this.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.text_dismiss))
            { _, _ ->
                this.dismiss()
            }
            this.show()
        }
    }

    private fun observeEvents() {
        observe(viewModel.getStateFlow()) {
            when (it) {
                is HomeViewModelState.Error -> {
                    binding.ltApodMainView.loader.visibility = View.GONE
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.errorMsg)
                        .create().apply {
                            this.setButton(
                                DialogInterface.BUTTON_POSITIVE, getString(R.string.text_ok)
                            ) { _, _ ->
                                this.dismiss()
                            }
                            this.show()
                        }
                }
                is HomeViewModelState.GetAPOD -> {
                    val apodResult = it.data
                    binding.ltApodMainView.loader.visibility = View.GONE
                    binding.ltApodMainView.layoutMain.visibility = View.VISIBLE
                    binding.resultApod = apodResult
                    Glide.with(requireContext())
                        .load(apodResult.imageUrl)
                        .into(binding.ltApodMainView.ivImageOfDay)
                }

                is HomeViewModelState.AddItemToFavDatabase -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_added_to_fav),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.ltApodMainView.ivAddToFav.setImageResource(R.drawable.ic_liked)
                }

                is HomeViewModelState.Loading -> {
                    binding.ltApodMainView.loader.visibility = View.VISIBLE
                    binding.ltApodMainView.layoutMain.visibility = View.GONE
                }

                else -> {}
            }
        }
    }
}