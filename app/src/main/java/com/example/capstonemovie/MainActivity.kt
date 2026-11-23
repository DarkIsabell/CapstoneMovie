package com.example.capstonemovie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.capstonemovie.databinding.ActivityMainBinding
import com.example.capstonemovie.movie.MovieFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.flContainer.setPadding(0, systemBars.top, 0, 0)
            binding.bottomNavigation.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) loadFragment(MovieFragment())
        setupView()
    }

    private fun setupView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_movie -> {
                    loadFragment(MovieFragment())
                    true
                }

                R.id.navigation_favorite -> {
                    val favoriteFragment =
                        createDynamicFragment("com.example.favorite.FavoriteFragment")
                    if (favoriteFragment != null) loadFragment(favoriteFragment)
                    true
                }

                R.id.navigation_trailer -> {
                    val trailerFragment =
                        createDynamicFragment("com.example.trailer.TrailerFragment")
                    if (trailerFragment != null) loadFragment(trailerFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, fragment)
            .commit()
    }

    private fun createDynamicFragment(className: String): Fragment? {
        return try {
            val clazz = Class.forName(className).asSubclass(Fragment::class.java)
            supportFragmentManager.fragmentFactory.instantiate(classLoader, clazz.name)
        } catch (e: ClassNotFoundException) {
            null
        }
    }
}