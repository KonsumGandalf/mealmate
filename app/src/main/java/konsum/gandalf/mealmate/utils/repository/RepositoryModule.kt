package konsum.gandalf.mealmate.utils.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import konsum.gandalf.mealmate.authentication.domain.repository.IAuthRepository
import konsum.gandalf.mealmate.authentication.repositories.AuthRepositoryImpl


// TODO delete if no further use
object RepositoryModule {
	@InstallIn(SingletonComponent::class)
	@Module
	class RepositoryModule {

		@Provides
		fun provideAuthRepository(repo: AuthRepositoryImpl): IAuthRepository = repo

	}
}