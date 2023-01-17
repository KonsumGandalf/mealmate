package konsum.gandalf.mealmate.deprecated.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import konsum.gandalf.mealmate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
