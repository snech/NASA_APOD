package com.example.nasa.domain.usecase

import com.example.nasa.base.results.NasaResult
import com.example.nasa.domain.repository.NasaRepository
import com.example.nasa.mvvm.model.NasaAOPD
import javax.inject.Inject

class GetAPODUseCase @Inject constructor(private val nasaRepository: NasaRepository) {

    suspend fun execute(searchDate: String): NasaResult<NasaAOPD> =
        nasaRepository.getAPOD(searchDate)
}