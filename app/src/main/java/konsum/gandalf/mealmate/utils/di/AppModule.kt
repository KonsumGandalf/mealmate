package konsum.gandalf.mealmate.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import konsum.gandalf.mealmate.authentication.data.repository.AuthRepositoryImpl
import konsum.gandalf.mealmate.authentication.data.repository.firebase.FirebaseAuthenticator
import konsum.gandalf.mealmate.authentication.data.repository.firebase.IAuthenticator
import konsum.gandalf.mealmate.authentication.domain.repository.IAuthRepository
import konsum.gandalf.mealmate.evaluation.data.repository.EvaluationRepositoryImpl
import konsum.gandalf.mealmate.evaluation.domain.repository.IEvaluationRepository
import konsum.gandalf.mealmate.recipe.data.api.MealDBApi
import konsum.gandalf.mealmate.recipe.data.firebaseRecipe.FirebaseRecipeRepositoryImpl
import konsum.gandalf.mealmate.recipe.data.firebaseRecipe.IFirebaseRecipeRepository
import konsum.gandalf.mealmate.recipe.data.repository.RecipeRepositoryImpl
import konsum.gandalf.mealmate.recipe.domain.repository.IRecipeRepository
import konsum.gandalf.mealmate.user.data.repository.UserRepositoryImpl
import konsum.gandalf.mealmate.user.domain.repository.IUserRepository
import konsum.gandalf.mealmate.utils.repository.IImageRepository
import konsum.gandalf.mealmate.utils.repository.ImageRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAuthenticator(): IAuthenticator {
        return FirebaseAuthenticator()
    }

    @Singleton
    @Provides
    fun provideImageRepository(): IImageRepository {
        return ImageRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideFirebaseRecipeRepo(): IFirebaseRecipeRepository {
        return FirebaseRecipeRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideEvaluationsRepo(): IEvaluationRepository {
        return EvaluationRepositoryImpl()
    }

    // this just takes the same idea as the authenticator. If we create another repository class
    // we can simply just swap here
    @Singleton
    @Provides
    fun provideAuthRepository(authenticator: IAuthenticator): IAuthRepository {
        return AuthRepositoryImpl(authenticator)
    }

    @Singleton
    @Provides
    fun provideUserRepository(authRepo: IAuthRepository): IUserRepository {
        return UserRepositoryImpl(authRepo)
    }

    @Singleton
    @Provides
    fun provideRecipeRepository(dbApi: MealDBApi, firebaseDb: IFirebaseRecipeRepository): IRecipeRepository {
        return RecipeRepositoryImpl(dbApi, firebaseDb)
    }
}
