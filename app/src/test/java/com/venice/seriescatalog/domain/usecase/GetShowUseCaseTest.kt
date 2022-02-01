package com.venice.seriescatalog.domain.usecase

import com.venice.seriescatalog.data.repository.SeriesCatalogRepository
import com.venice.seriescatalog.model.Schedule
import com.venice.seriescatalog.model.Show
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/*
 * Created by Andre Arruda on 2/1/22.
 */
class GetShowUseCaseTest {

    private lateinit var getShowUseCaseTest: GetShowUseCase

    @MockK
    private lateinit var seriesCatalogRepository: SeriesCatalogRepository

    private var mockedShowV0 = Show(id = 1,
                                    name = "MockedShow",
                                    image = null,
                                    genres = listOf("MockedDrama", "MockedTerror", "MockedAction"),
                                    summary = "MockedSummary for MockedShow",
                                    runtime = 90,
                                    schedule = Schedule("MockedTime", listOf("MockedSunday", "MockedWednesday")),
                                    episodes = null)

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getShowUseCaseTest = GetShowUseCase(seriesCatalogRepository)
    }

    @Test
    fun `get show information returning successfull response`() {
        coEvery { getShowUseCaseTest.invoke(1) } coAnswers {mockedShowV0}

        val returnedShow = runBlocking { getShowUseCaseTest.invoke(1) }

        assertEquals(returnedShow, mockedShowV0)

    }

}
