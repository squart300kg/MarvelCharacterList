package kr.co.architecture.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.architecture.repository.CharacterRepository
import kr.co.architecture.repository.CharacterRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {

    @Binds
    fun bindsCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository

}