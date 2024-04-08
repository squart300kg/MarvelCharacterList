package kr.co.architecture.domain

import kr.co.architecture.repository.CharacterRepository
import kr.co.architecture.repository.model.CharacterDataModel
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