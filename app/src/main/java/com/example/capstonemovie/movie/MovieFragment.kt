package com.example.capstonemovie.movie

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.capstonemovie.R
import com.example.capstonemovie.databinding.FragmentMovieBinding
import com.example.capstonemovie.movie.adapter.MovieAdapter
import com.example.capstonemovie.movie.model.MovieUiModel
import com.example.capstonemovie.movie.viewmodel.MovieViewModel
import com.example.capstonemovie.moviedetail.DetailMovieActivity
import com.example.core.domain.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModel()
    private val movieAdapter by lazy { MovieAdapter() }
    private val handler = Handler(Looper.getMainLooper())

    private var currentDataList: List<MovieUiModel> = emptyList()
    private var actualSize = 0
    private var size = 0

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (_binding == null || movieAdapter.itemCount <= 1) return
            val nextItem = (binding.vpVideoHighlight.currentItem + 1) % movieAdapter.itemCount
            binding.vpVideoHighlight.setCurrentItem(nextItem, true)
            handler.postDelayed(this, 3000)
        }
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            when (state) {
                ViewPager2.SCROLL_STATE_DRAGGING -> {
                    stopAutoScroll()
                }

                ViewPager2.SCROLL_STATE_IDLE -> {
                    stopAutoScroll()
                    startAutoScroll()
                    if (actualSize >= 2) {
                        when (binding.vpVideoHighlight.currentItem) {
                            size - 1 -> binding.vpVideoHighlight.setCurrentItem(
                                if (actualSize == 2) 2 else 1,
                                false
                            )

                            0 -> binding.vpVideoHighlight.setCurrentItem(
                                size - if (actualSize == 2) 3 else 2,
                                false
                            )
                        }
                    }
                }

                else -> {}
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (currentDataList.isNotEmpty() && actualSize > 0) {
                val index = if (actualSize >= 2) {
                    (position - 1 + actualSize) % actualSize
                } else {
                    position
                }
                if (index in currentDataList.indices) {
                    binding.tvTitle.text = currentDataList[index].title
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapterHighlightVideo()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> binding.pbMovie.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.pbMovie.visibility = View.GONE
                        val data = movies.data ?: emptyList()
                        if (data.isNotEmpty()) {
                            setupVideoHighlight(data)
                        }
                    }

                    is Resource.Error -> {
                        binding.pbMovie.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupVideoHighlight(list: List<MovieUiModel>) {
        val isInitialLoad = currentDataList.isEmpty()
        currentDataList = list
        setupInfinite(list)
        if (isInitialLoad) {
            val initialItem = if (list.size >= 2) 1 else 0
            binding.vpVideoHighlight.setCurrentItem(initialItem, false)
            if (list.isNotEmpty()) {
                binding.tvTitle.text = list.first().title
            }
        } else {
            val currentPos = binding.vpVideoHighlight.currentItem
            if (list.isNotEmpty() && actualSize > 0) {
                val index = if (actualSize >= 2) {
                    (currentPos - 1 + actualSize) % actualSize
                } else {
                    currentPos
                }
                if (index in list.indices) {
                    binding.tvTitle.text = list[index].title
                }
            }
        }
        stopAutoScroll()
        startAutoScroll()
    }

    private fun setupInfinite(dataList: List<MovieUiModel>) {
        actualSize = dataList.size
        if (dataList.size >= 3) {
            val newListClick = listOf(dataList.last()) + dataList + listOf(dataList.first())
            size = newListClick.size
            movieAdapter.submitList(newListClick)
        } else if (dataList.size == 2) {
            val newListClick =
                listOf(dataList.last()) + dataList + listOf(dataList.first()) + listOf(dataList.last())
            size = newListClick.size
            movieAdapter.submitList(newListClick)
        } else {
            size = dataList.size
            movieAdapter.submitList(dataList)
        }
        movieAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MovieUiModel) {
                navigateToDetail(data)
            }
        })
    }

    private fun navigateToDetail(movie: MovieUiModel) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    private fun setupAdapterHighlightVideo() {
        binding.vpVideoHighlight.adapter = movieAdapter
        binding.vpVideoHighlight.offscreenPageLimit = 3
        binding.vpVideoHighlight.getChildAt(0).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(28))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
            page.findViewById<TextView>(R.id.tvAdult)?.let {
                it.scaleY = 1.15f - r * 0.15f
            }
            page.findViewById<TextView>(R.id.tvRating)?.let {
                it.scaleY = 1.15f - r * 0.15f
            }
            page.findViewById<TextView>(R.id.tvRelease)?.let {
                it.scaleY = 1.15f - r * 0.15f
            }
        }
        binding.vpVideoHighlight.setPageTransformer(compositePageTransformer)
        binding.vpVideoHighlight.registerOnPageChangeCallback(pageChangeCallback)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable)
    }

    private fun startAutoScroll() {
        stopAutoScroll()
        if (movieAdapter.itemCount > 1) {
            handler.postDelayed(autoScrollRunnable, 3000)
        }
    }

    override fun onResume() {
        super.onResume()
        if (movieAdapter.itemCount > 1) {
            startAutoScroll()
        }
    }

    override fun onPause() {
        super.onPause()
        stopAutoScroll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll()
        binding.vpVideoHighlight.unregisterOnPageChangeCallback(pageChangeCallback)
        _binding = null
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie_data"
    }
}