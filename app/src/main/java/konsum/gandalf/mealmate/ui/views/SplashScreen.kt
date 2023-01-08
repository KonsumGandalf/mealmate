package konsum.gandalf.mealmate.ui.views

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.ActivityWelcomeScreenBinding
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
  object Splash : Screen("splash_screen")
  object Welcome : Screen("welcome_screen")
}

class SplashScreen : AppCompatActivity() {
  lateinit var binding: ActivityWelcomeScreenBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
    setContent {
      Surface(color = Color(R.color.white), modifier = Modifier.fillMaxSize()) { Navigation(binding) }
    }
  }
}

@Composable()
fun Navigation(welcomeScreen: ActivityWelcomeScreenBinding) {
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
        targetValue = 0.5f,
        animationSpec =
            tween(
                durationMillis = 500, easing = { OvershootInterpolator(2f).getInterpolation(it) }))
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