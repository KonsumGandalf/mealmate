package konsum.gandalf.mealmate.authentication.ui.constants

import android.content.Context
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.deprecated.views.LoginActivity
import konsum.gandalf.mealmate.deprecated.views.RegisterActivity
import konsum.gandalf.mealmate.deprecated.views.WelcomeScreen
import konsum.gandalf.mealmate.deprecated.views.buttons.IntentButton
import konsum.gandalf.mealmate.deprecated.views.enums.DecisionFragmentsEnum
import konsum.gandalf.mealmate.deprecated.views.login.DecisionFragment
import konsum.gandalf.mealmate.deprecated.views.login.DecisionFragmentInformation

class DecisionFragmentBuilder(private val curContext: Context, val mode: String) {
    fun build(): DecisionFragment {
        return DecisionFragment(information())
    }

    fun information(): DecisionFragmentInformation {
        return when (mode) {
            DecisionFragmentsEnum.LOGIN.fragmentName -> {
                return DecisionFragmentInformation(
                    curContext,
                    IntentButton(WelcomeScreen::class.java, R.drawable.icon_google_foreground),
                    IntentButton(LoginActivity::class.java, R.drawable.icon_gmail_foreground),
                    R.color.red_200,
                    R.drawable.undraw_mobile_login,
                    mode
                )
            }
            DecisionFragmentsEnum.REGISTER.fragmentName -> {
                return DecisionFragmentInformation(
                    curContext,
                    IntentButton(WelcomeScreen::class.java, R.drawable.icon_google_foreground),
                    IntentButton(RegisterActivity::class.java, R.drawable.icon_gmail_foreground),
                    R.color.blue_200,
                    R.drawable.undraw_fingerprint,
                    mode
                )
            }
            else -> {
                DecisionFragmentInformation(
                    curContext,
                    IntentButton(LoginActivity::class.java, null),
                    IntentButton(RegisterActivity::class.java, null),
                    R.color.green_200,
                    R.drawable.undraw_cooking,
                    mode
                )
            }
        }
    }
}