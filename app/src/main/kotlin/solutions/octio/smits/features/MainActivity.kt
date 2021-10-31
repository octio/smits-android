package solutions.octio.smits.features

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import solutions.octio.smits.R
import solutions.octio.smits.core.archcomponents.NavigationResult
import solutions.octio.smits.core.archcomponents.extension.observe
import solutions.octio.smits.features.appstate.AppStateViewModel
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        findNavController(R.id.main_activity_nav_host)
    }
    private val appStateViewModel: AppStateViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarLayout()
        setupNavController()
    }

    private fun setupActionBarLayout() {
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.colorActionBar
                )
            )
        )
    }

    private fun setupNavController() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            title = destination.label
            appStateViewModel.run {
                observe(components, ::handleComponents)
            }
        }
    }

    private fun handleComponents(components: AppStateViewModel.Components) {
        if (components.appBar) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }

        if (components.backButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val currentFragment = findCurrentFragment()

        return currentFragment?.onActivityResult(requestCode, resultCode, data)
            ?: super.onActivityResult(requestCode, resultCode, data)
    }

    private fun findCurrentFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_activity_nav_host)
        return navHostFragment?.childFragmentManager?.fragments?.getOrNull(0)
    }

    fun navigateBackWithResult(result: Bundle) {
        val childFragmentManager =
            supportFragmentManager.findFragmentById(R.id.main_activity_nav_host)
                ?.childFragmentManager
        var backStackListener: FragmentManager.OnBackStackChangedListener by Delegates.notNull()
        backStackListener = FragmentManager.OnBackStackChangedListener {
            (childFragmentManager?.fragments?.get(0) as NavigationResult).onNavigationResult(result)
            childFragmentManager.removeOnBackStackChangedListener(backStackListener)
        }
        childFragmentManager?.addOnBackStackChangedListener(backStackListener)
        navController.popBackStack()
    }
}
