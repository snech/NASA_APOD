package com.example.nasa.mvvm.viewmodel

import app.cash.turbine.test
import com.example.nasa.base.results.NasaResult
import com.example.nasa.domain.usecase.GetAPODUseCase
import com.example.nasa.domain.usecase.InsertFavUseCase
import com.example.nasa.mvvm.model.NasaAOPD
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var subject: HomeViewModel

    private val mockAPODUseCase = mockk<GetAPODUseCase>(relaxed = true)
    private val mockInsertFavUseCase = mockk<InsertFavUseCase>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        subject = HomeViewModel(mockAPODUseCase, mockInsertFavUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `sendIntent - LoadAPOD`() {
        val fakeData = NasaAOPD(
            imageTitle = "fake_copyright",
            imageUrl = "fake_url",
            imageExplanation = "fake_explanation",
            imageDate = "25/11/2014",
            videoLinkUrl = "fake_video_link",
            isVideo = false
        )
        coEvery { mockAPODUseCase.execute("25/11/2014") } returns NasaResult.Success(
            fakeData
        )
        runTest {
            subject.sendIntent(HomeViewModelIntent.LoadAPOD("25/11/2014"))

            subject.getStateFlow().test {
                expectThat(awaitItem()).isA<HomeViewModelState.GetAPOD>().and {
                    get { this.data.imageTitle } isEqualTo fakeData.imageDate
                }
            }
        }
    }

    @Test
    fun `sendIntent - InsertFavData`() {
        val fakeData = NasaAOPD(
            imageTitle = "fake_copyright",
            imageUrl = "fake_url",
            imageExplanation = "fake_explanation",
            imageDate = "25/11/2014",
            videoLinkUrl = "fake_video_link",
            isVideo = false
        )
        coEvery { mockInsertFavUseCase.insert(any()) } returns NasaResult.Success(
            true
        )
        runTest {
            subject.sendIntent(HomeViewModelIntent.InsertFavData(fakeData))

            subject.getStateFlow().test {
                expectThat(awaitItem()).isA<HomeViewModelState.AddItemToFavDatabase>()
            }
        }
    }

}