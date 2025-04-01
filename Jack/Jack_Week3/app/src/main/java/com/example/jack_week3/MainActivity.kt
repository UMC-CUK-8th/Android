package com.example.jack_week3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.jack_week3.databinding.ActivityMainBinding
import com.example.jack_week3.ui.theme.Jack_Week3Theme

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setBottomNavigationView()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        if (savedInstanceState == null){
            binding.myBottomNav.selectedItemId = R.id.homeFragment
        }
    }
    fun setBottomNavigationView() {
        binding.myBottomNav.setOnItemSelectedListener { item ->
            val navController = supportFragmentManager.findFragmentById(R.id.my_nav_host)?.findNavController()

            when (item.itemId) {
                R.id.homeFragment -> {
                    navController?.navigate(R.id.homeFragment)
                    true
                }
                R.id.seeFragment -> {
                    navController?.navigate(R.id.seeFragment)
                    true
                }
                R.id.searchFragment -> {
                    navController?.navigate(R.id.searchFragment)
                    true
                }
                R.id.libraryFragment -> {
                    navController?.navigate(R.id.libraryFragment)
                    true
                }
                else -> false
            }
        }
    }

}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Jack_Week3Theme {
        Greeting("Android")
    }
}