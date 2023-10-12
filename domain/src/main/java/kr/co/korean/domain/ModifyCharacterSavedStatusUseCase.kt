package kr.co.korean.domain

import kr.co.korean.repository.CharacterRepository
import kr.co.korean.repository.model.CharacterDataModel
import javax.inject.Inject

class ModifyCharacterSavedStatusUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(dataModel: CharacterDataModel, saved: Boolean) {
        characterRepository.modifyCharacterSavedStatus(
            dataModel = dataModel,
            saved = saved
        )
    }
}