package com.example.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.capstonemovie.movie.MovieFragment.Companion.EXTRA_MOVIE
import com.example.capstonemovie.movie.model.MovieUiModel
import com.example.capstonemovie.moviedetail.DetailMovieActivity
import com.example.favorite.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModel()

    private val favoriteAdapter by lazy { FavoriteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(favoriteModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFavoriteMovie()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.favoriteMovie.observe(viewLifecycleOwner) { movies ->
            favoriteAdapter.submitList(movies)
            binding.vEmpty.root.visibility =
                if (movies.isNotEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun setupFavoriteMovie() {
        binding.rvFavoriteMovie.apply {
            isNestedScrollingEnabled = false
            layoutManager = GridLayoutManager(activity, 2)
            adapter = favoriteAdapter
        }

        favoriteAdapter.setOnItemClickCallback(object :
            FavoriteAdapter.OnItemClickCallback {
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

    override fun onDestroyView() {
        super.onDestroyView()
        favoriteAdapter.setOnItemClickCallback(null)
        binding.rvFavoriteMovie.adapter = null
        _binding = null
    }
}