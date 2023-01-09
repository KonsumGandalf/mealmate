package konsum.gandalf.mealmate.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.ActivityLoginBinding
import konsum.gandalf.mealmate.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
	lateinit var binding: ActivityRegisterBinding;
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityRegisterBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}
}