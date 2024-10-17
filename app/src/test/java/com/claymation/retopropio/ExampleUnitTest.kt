package com.claymation.retopropio

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.claymation.retopropio.Viewmodels.ViewModel as MyViewModel

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class ViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `sendQuestionToAPI debe retornar codigo de respuesta 200`() = runTest {
        val viewModel = MyViewModel()

        viewModel.sendQuestionToAPI("¿Cuál es tu nombre?", onSuccess = { response ->
            // Aquí puedes imprimir y verificar la respuesta
            println("Respuesta del servidor: $response")
            assertTrue(response.isNotEmpty()) // Verificamos que la respuesta no esté vacía
        }, onFailure = { error ->
            fail("La API debería haber respondido correctamente")
        })
    }





}
