package konsum.gandalf.mealmate.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import konsum.gandalf.mealmate.authentication.data.repository.AuthRepositoryImpl
import konsum.gandalf.mealmate.authentication.data.repository.firebase.FirebaseAuthenticator
import konsum.gandalf.mealmate.authentication.data.repository.firebase.IAuthenticator
import konsum.gandalf.mealmate.authentication.domain.repository.IAuthRepository
import konsum.gandalf.mealmate.user.data.repository.UserRepositoryImpl
import konsum.gandalf.mealmate.user.domain.repository.IUserRepository
import konsum.gandalf.mealmate.utils.repository.IImageRepository
import konsum.gandalf.mealmate.utils.repository.ImageRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**All of our application dependencies shall be provided here*/

    // this means that anytime we need an authenticator Dagger will provide a Firebase authenticator.
    // in future if you want to swap out Firebase authentication for your own custom authenticator
    // you will simply come and swap here.
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
}
