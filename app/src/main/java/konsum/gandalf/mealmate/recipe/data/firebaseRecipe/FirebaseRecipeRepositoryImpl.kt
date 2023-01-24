package konsum.gandalf.mealmate.recipe.data.firebaseRecipe

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import konsum.gandalf.mealmate.recipe.domain.models.FilterFirebaseNamespace
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.utils.await
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseRecipeRepositoryImpl(
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO,
    private val recipeCollection: CollectionReference =
        FirebaseFirestore.getInstance().collection(FirebaseReferenceEnum.RECIPE)
) : IFirebaseRecipeRepository {
    private val TAG = "FirebaseRecipeRepositoryImpl"

    override suspend fun createRecipe(recipe: Recipe): Recipe =
        withContext(ioDispatchers) {
            val id = recipeCollection.document().id
            recipe.id = id

            recipeCollection
                .document(id)
                .set(recipe)
                .addOnSuccessListener {}
                .addOnFailureListener {
                    Log.d(TAG, it.localizedMessage!!.toString())
                }
                .await()
            recipe
        }

    override suspend fun getRecipe(recipeId: String): Recipe? = withContext(ioDispatchers) {
        var recipe: Recipe? = null
        recipeCollection
            .document(recipeId)
            .get()
            .addOnSuccessListener { document ->
                recipe = document.toObject<Recipe>()
            }
            .addOnFailureListener { Log.d(TAG, it.localizedMessage!!.toString()) }
            .await()

        recipe
    }

    override suspend fun updateRecipe(recipe: Recipe): Recipe =
        withContext(ioDispatchers) {
            recipeCollection
                .document(recipe.id)
                .set(recipe)
                .addOnSuccessListener {}
                .addOnFailureListener { Log.d(TAG, it.localizedMessage!!.toString()) }
                .await()

            recipe
        }

    override suspend fun deleteRecipe(id: String): Unit =
        withContext(ioDispatchers) {
            recipeCollection
                .document(id)
                .delete()
                .addOnSuccessListener {
                }
                .addOnFailureListener {
                    Log.d(TAG, it.localizedMessage!!.toString())
                }
                .await()
        }

    override suspend fun getRecipeByFilter(
        name: String
    ): List<Recipe> =
        withContext(ioDispatchers) {
            val filteredList = ArrayList<Recipe>()
            recipeCollection
                .whereGreaterThanOrEqualTo(FilterFirebaseNamespace.TITLE, name)
                .whereLessThanOrEqualTo(FilterFirebaseNamespace.TITLE, name + "\uF7FF")
                .get()
                .addOnSuccessListener { query ->
                    if (!query.isEmpty) {
                        query.documents.all { document ->
                            val recipe = document.toObject<Recipe>()
                            if (recipe != null) {
                                filteredList.add(recipe)
                            }
                            true
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, it.localizedMessage!!.toString())
                }.await()
            filteredList
        }

    override suspend fun getUserRecipes(
        userId: String
    ): List<Recipe> =
        withContext(ioDispatchers) {
            val filteredList = ArrayList<Recipe>()
            recipeCollection
                .get()
                .addOnSuccessListener { query ->
                    if (!query.isEmpty) {
                        query.documents.all { document ->
                            val recipe = document.toObject<Recipe>()
                            if (recipe != null) {
                                filteredList.add(recipe)
                            }
                            true
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, it.localizedMessage!!.toString())
                }.await()
            filteredList.filter { it.owner?.uid == userId }
        }
}
