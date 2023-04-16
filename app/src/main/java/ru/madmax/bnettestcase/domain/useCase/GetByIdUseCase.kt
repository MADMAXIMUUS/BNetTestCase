package ru.madmax.bnettestcase.domain.useCase

import ru.madmax.bnettestcase.data.repository.DragRepository
import ru.madmax.bnettestcase.domain.model.Drag

class GetByIdUseCase(
    private val dragRepository: DragRepository
) {

    suspend operator fun invoke(id: Int): Drag {
        return dragRepository.getById(id)
    }
}