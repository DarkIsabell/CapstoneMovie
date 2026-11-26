package com.example.trailer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.utils.Resource
import com.example.trailer.databinding.FragmentTrailerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class TrailerFragment : Fragment() {

    private var _binding: FragmentTrailerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrailerViewModel by viewModel()
    private val trailerAdapter: TrailerAdapter by lazy { TrailerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(trailerModule)
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(trailerModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrailerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTrailerMovie()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.trailer.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> binding.pbMovie.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.pbMovie.visibility = View.GONE
                        val data = movies.data ?: emptyList()
                        if (data.isNotEmpty()) trailerAdapter.submitList(data)
                    }

                    is Resource.Error -> {
                        binding.pbMovie.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setupTrailerMovie() {
        binding.rvFavoriteMovie.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = trailerAdapter
        }

        trailerAdapter.setOnItemClickCallback(object :
            TrailerAdapter.OnItemClickCallback {
            override fun onItemClicked(link: String) {
                if (link.isNotBlank()) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            link.toUri()
                        )
                    )
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        trailerAdapter.setOnItemClickCallback(null)
        binding.rvFavoriteMovie.adapter = null
        _binding = null
    }

}