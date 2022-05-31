package android.example.movies.screen

import android.example.movies.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navHost = supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(
            this,
            navHost.navController,
            AppBarConfiguration(navHost.navController.graph)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navHost.navController)
                || super.onOptionsItemSelected(item)
    }
}