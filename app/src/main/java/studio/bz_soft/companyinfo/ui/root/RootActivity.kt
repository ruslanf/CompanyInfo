package studio.bz_soft.companyinfo.ui.root

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_root.*
import org.koin.android.ext.android.inject
import studio.bz_soft.companyinfo.R
import studio.bz_soft.companyinfo.root.state.LoadingState
import studio.bz_soft.companyinfo.root.state.LoadingStateViewModel
import studio.bz_soft.companyinfo.ui.companies.company.CompanyPresenter

class RootActivity : AppCompatActivity() {

    private val rootPresenter by inject<RootPresenter>()
    private val companyPresenter by inject<CompanyPresenter>()
    private val loadingStateViewModel by inject<LoadingStateViewModel>()

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        setSupportActionBar(toolbar)

        initialize()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.menuCompaniesList, R.id.menuMore, R.id.menuSettings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        mainBottomNavigationMenu.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main_menu, menu)
        if (menu is MenuBuilder) menu.setOptionalIconsVisible(true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        NavigationUI.onNavDestinationSelected(item, navController)
        when (item.itemId) {
            R.id.menuSync -> syncButtonListener()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initialize() {
        loadingStateViewModel.registerLoadingStateListener(rootPresenter, companyPresenter)
        loadingStateViewModel.loadingState.observe(this, Observer { it?.let { render(it) } })

        rootPresenter.synchronize()
    }

    private fun syncButtonListener() {
        rootPresenter.synchronize()
    }

    private fun render(loadingState: LoadingState) {
        progressBar.visibility = when (loadingState.isLoading) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }
}
