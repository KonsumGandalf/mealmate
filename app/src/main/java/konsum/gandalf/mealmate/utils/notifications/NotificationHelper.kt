package konsum.gandalf.mealmate.utils.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.utils.application.MealMateApplication

object NotificationHelper {
    const val CHANNEL_ID = "meal-mate-1"

    fun createImageNotification(context: Context, img: Bitmap): NotificationCompat.Builder {
        val startAppIntent = Intent(context, MealMateApplication::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, startAppIntent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.meal_mate_icon_small)
            .setLargeIcon(img)
            .setContentTitle("My notification")
            .setContentText(
                "Congratulations! Your recipe for MealMate has been created and is now live on our app for others to enjoy. Bon appétit!"
            )
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Recipe uploaded")
            )
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setContentIntent(pendingIntent)
            .setContentTitle("MealMate")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "We are thrilled to inform you that your recipe for MealMate has been successfully created and is now live for others to enjoy. We know that creating a recipe takes time and effort, and we want to thank you for sharing your culinary creations with us. Your recipe is a true reflection of your passion for food and we are honored to have it featured on our platform.\n" +
                            "\n" +
                            "We understand that creating a recipe is a process that requires a lot of creativity and experimentation. From choosing the right ingredients to finding the perfect combination of flavors, it's a journey that requires patience and dedication. That's why we want to recognize and celebrate your hard work.\n" +
                            "\n" +
                            "We hope that your recipe will inspire others to cook with passion and creativity. Our community of food enthusiasts is always looking for new and delicious recipes to try, and we are confident that your recipe will be a hit.\n" +
                            "\n" +
                            "Once again, congratulations on creating a recipe! We can't wait to see what other culinary creations you come up with in the future. Bon appétit!"
                    )
            )
    }
}
