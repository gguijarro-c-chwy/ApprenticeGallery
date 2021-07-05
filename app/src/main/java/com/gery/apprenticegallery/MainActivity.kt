package com.gery.apprenticegallery

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.gery.apprenticegallery.databinding.ActivityMainBinding
import com.jakewharton.rxbinding4.appcompat.queryTextChangeEvents

import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var disposable: Disposable
    private lateinit var searchView: SearchView
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        handleNavController()
    }

    private fun handleNavController() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.photoDetailsFragment) {
                toolbar.visibility = View.GONE
                toolbar.collapseActionView()
                searchView.onActionViewCollapsed()
            } else {
                toolbar.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.search)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        disposable = searchView.queryTextChangeEvents()
            .skipInitialValue()
            // Avoid multiple hits to API until user stops typing
            .debounce(800, TimeUnit.MILLISECONDS)
            .map {
                it.queryText.toString()
            }
            .filter {
                it.isNotEmpty()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.onSearchQueryChange(it)
            }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        disposable.isDisposed
        super.onDestroy()
    }

    class SearchViewModel : ViewModel() {
        val searchQuery: MutableLiveData<String> by lazy {
            MutableLiveData<String>()
        }

        fun onSearchQueryChange(searchQuery: String) {
            this.searchQuery.value = searchQuery
        }
    }
}