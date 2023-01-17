package konsum.gandalf.mealmate.deprecated.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import konsum.gandalf.mealmate.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
