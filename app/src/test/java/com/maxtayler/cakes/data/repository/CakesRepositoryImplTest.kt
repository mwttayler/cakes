package com.maxtayler.cakes.data.repository

import com.maxtayler.cakes.data.api.FakeCakesApi
import com.maxtayler.cakes.data.api.response.CakeResponse
import com.maxtayler.cakes.data.api.response.CakeResponseFactory
import com.maxtayler.cakes.data.model.mapper.CakeMapper
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.io.IOException

class CakesRepositoryImplTest {

    @Test
    fun `GIVEN api returns duplicate titles WHEN getCakes THEN duplicates are removed`() = runTest {
        val repository = repositoryReturning(
            CakeResponseFactory.create(title = "Carrot cake"),
            CakeResponseFactory.create(title = "Carrot cake"),
            CakeResponseFactory.create(title = "Banana cake")
        )

        val titles = repository.getCakes().map { it.title }

        assertEquals(listOf("Banana cake", "Carrot cake"), titles)
    }

    @Test
    fun `GIVEN titles differ only by case or whitespace WHEN getCakes THEN they are treated as duplicates`() = runTest {
        val repository = repositoryReturning(
            CakeResponseFactory.create(title = "Lemon cheesecake"),
            CakeResponseFactory.create(title = " lemon cheesecake "),
            CakeResponseFactory.create(title = "LEMON CHEESECAKE")
        )

        val titles = repository.getCakes().map { it.title }

        assertEquals(listOf("Lemon cheesecake"), titles)
    }

    @Test
    fun `GIVEN api returns unsorted cakes WHEN getCakes THEN cakes are sorted by title ignoring case`() = runTest {
        val repository = repositoryReturning(
            CakeResponseFactory.create(title = "victoria sponge"),
            CakeResponseFactory.create(title = "Banana cake"),
            CakeResponseFactory.create(title = "carrot cake"),
            CakeResponseFactory.create(title = "Apple pie")
        )

        val titles = repository.getCakes().map { it.title }

        assertEquals(listOf("Apple pie", "Banana cake", "carrot cake", "victoria sponge"), titles)
    }

    @Test
    fun `GIVEN api fails WHEN getCakes THEN error is thrown`() {
        val repository = CakesRepositoryImpl(
            api = FakeCakesApi(error = IOException("No network")),
            mapper = CakeMapper()
        )

        assertThrows(IOException::class.java) {
            runTest { repository.getCakes() }
        }
    }

    private fun repositoryReturning(vararg cakes: CakeResponse) = CakesRepositoryImpl(
        api = FakeCakesApi(cakes = cakes.toList()),
        mapper = CakeMapper()
    )
}
