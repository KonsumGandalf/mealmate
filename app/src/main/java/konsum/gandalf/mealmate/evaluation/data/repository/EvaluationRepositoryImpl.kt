package konsum.gandalf.mealmate.evaluation.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import konsum.gandalf.mealmate.evaluation.domain.models.EvaluationPost
import konsum.gandalf.mealmate.evaluation.domain.models.FirebaseReferenceEnum
import konsum.gandalf.mealmate.evaluation.domain.repository.IEvaluationRepository
import konsum.gandalf.mealmate.utils.await
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EvaluationRepositoryImpl(
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO,
    private val evaluationCollection: CollectionReference =
        FirebaseFirestore.getInstance().collection(FirebaseReferenceEnum.EVALUATIONS)
) : IEvaluationRepository {
    private val TAG = "RecipeRepositoryImpl"

    override suspend fun createEvaluation(evaluation: EvaluationPost): EvaluationPost =
        withContext(ioDispatchers) {
            val id = evaluationCollection.document().id
            evaluation.id = id

            evaluationCollection
                .document(id)
                .set(evaluation)
                .addOnSuccessListener {}
                .addOnFailureListener {
                    Log.d(TAG, it.localizedMessage!!.toString())
                }
                .await()
            evaluation
        }

    override suspend fun getEvaluations(): List<EvaluationPost> =
        withContext(ioDispatchers) {
            val filteredList = ArrayList<EvaluationPost>()
            evaluationCollection
                .get()
                .addOnSuccessListener { query ->
                    if (!query.isEmpty) {
                        query.documents.all { document ->
                            val eval = document.toObject<EvaluationPost>()
                            if (eval != null) {
                                filteredList.add(eval)
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

    override suspend fun getEvaluations(recipeId: String): List<EvaluationPost> =
        withContext(ioDispatchers) {
            val filteredList = ArrayList<EvaluationPost>()
            evaluationCollection
                .whereEqualTo("${FirebaseReferenceEnum.RECIPE}.recipeId", recipeId)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        filteredList.add(document.toObject<EvaluationPost>())
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, it.localizedMessage!!.toString())
                }.await()
            filteredList
        }
}
