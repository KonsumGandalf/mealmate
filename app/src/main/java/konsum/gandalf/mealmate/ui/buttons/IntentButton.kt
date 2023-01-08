package konsum.gandalf.mealmate.ui.buttons

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

data class IntentButton(val nextActivity: Class<*>, val sign: Int?){
}