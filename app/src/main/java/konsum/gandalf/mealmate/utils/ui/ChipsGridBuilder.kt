package konsum.gandalf.mealmate.utils.ui

import android.content.Context
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class ChipsGridBuilder() {
    companion object {
        fun buildLayout(context: Context): FlexboxLayoutManager {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.CENTER
            return layoutManager
        }
    }
}
