package com.claymation.retopropio


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.claymation.retopropio.Viewmodels.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any


@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class ViewModelRegisterTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `register regresa exitoso cuando los campos son validos`() = runTest {
        val mockContext = mock(android.content.Context::class.java)
        val viewModel = ViewModel()


        // Set valid inputs for the registration
        viewModel.updateName("Juan")
        viewModel.updateSecName("Pérez")
        viewModel.updateEmail("juan.perez@example.com")
        viewModel.updatePhone("5555555555")
        viewModel.updateAge("25")
        viewModel.updatePassword("Password123")
        viewModel.updateConfirmPassword("Password123")


        viewModel.register(mockContext, onSuccess = {
            // Verificar que el registro fue exitoso
            assertTrue(viewModel.errorMessage.value == null)
        }, onFailure = {
            fail("El registro debería haber sido exitoso")
        })
    }


    @Test
    fun `register falla cuando las contraseñas no son iguales`() = runTest {
        val mockContext = mock(android.content.Context::class.java)
        val viewModel = ViewModel()


        // Set invalid inputs (passwords do not match)
        viewModel.updateName("Juan")
        viewModel.updateSecName("Pérez")
        viewModel.updateEmail("juan.perez@example.com")
        viewModel.updatePhone("5555555555")
        viewModel.updateAge("25")
        viewModel.updatePassword("Password123")
        viewModel.updateConfirmPassword("DifferentPassword")


        viewModel.register(mockContext, onSuccess = {
            fail("El registro no debería haber sido exitoso")
        }, onFailure = {
            // Verificar que el registro falló por contraseñas no coincidentes
            assertEquals("Las contraseñas no coinciden.", viewModel.errorMessage.value)
        })
    }
}
