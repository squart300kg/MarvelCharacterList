package kr.co.korean.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.korean.network.MarvelCharacterApi
import kr.co.korean.repository.CharacterRepository
import kr.co.korean.repository.CharacterRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {

    @Binds
    fun bindsCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository

}