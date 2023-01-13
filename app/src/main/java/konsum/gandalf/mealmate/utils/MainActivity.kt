package konsum.gandalf.mealmate.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.ContentNavHostLoginBinding
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import konsum.gandalf.mealmate.authentication.ui.fragments.AuthViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var navController: NavController

	private val viewModel: AuthViewModel by viewModels()

	private lateinit var _binding: ContentNavHostLoginBinding
	private val binding
		get() = _binding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ContentNavHostLoginBinding.inflate(layoutInflater);



		lifecycleScope.launch {
			setContent {
				Surface(color = Color(R.color.white), modifier = Modifier.fillMaxSize()) { Navigation() }
			}
			delay(1500)
			setContentView(binding.root)
			//val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
			//val navController = hostFragment.findNavController();

		}
	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp() || super.onSupportNavigateUp()
	}

}

sealed class Screen(val route: String) {
	object Splash : Screen("splash_screen")
	object Welcome : Screen("welcome_screen")
}

@Composable()
fun Navigation() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = Screen.Splash.route) {
		composable(Screen.Splash.route) { SplashScreen(navController = navController) }
		composable(Screen.Welcome.route) { return@composable }
	}
}

@Composable()
fun SplashScreen(navController: NavController) {
	val scale = remember { Animatable(0f) }
	LaunchedEffect(key1 = true) {
		scale.animateTo(
			targetValue = 1f,
			animationSpec =
			tween(
				durationMillis = 1000, easing = { OvershootInterpolator(2f).getInterpolation(it) }))
		navController.navigate(Screen.Welcome.route)
	}


	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier.fillMaxSize()) {
		Image(
			painter = painterResource(id = R.drawable.splash_panda),
			contentDescription = "Logo",
			modifier = Modifier.scale(scale.value))
	}
}
