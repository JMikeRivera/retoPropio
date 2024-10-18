package com.claymation.retopropio

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import com.claymation.retopropio.Screens.MostrarCasosDerechoCivil
import com.claymation.retopropio.Screens.validarDatos
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
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

    @Test
    fun `login should trigger onFailure callback on API error`() = runTest {
        val context = mock(Context::class.java)
        val viewModel = MyViewModel()

        // Mocking failure callback
        var isLoginFailed = false

        viewModel.updateEmail("wrongemail@mail.com")
        viewModel.updatePassword("wrongpassword")

        viewModel.login(context, onSuccess = {
            fail("Login should have failed")
        }, onFailure = {
            isLoginFailed = true
        })

        // Simulate the failure response
        assertTrue("Login should have failed", isLoginFailed)
        assertFalse("User should not be marked as logged in", viewModel.isLoggedIn.value)
    }

    @Test
    fun `register should successfully register a new user`() = runTest {
        val context = mock(Context::class.java)
        val viewModel = MyViewModel()

        // Mocking success callback
        var isRegisterSuccess = false

        // Set valid data for registration
        viewModel.updateName("John")
        viewModel.updateSecName("Doe")
        viewModel.updateEmail("john.doe@mail.com")
        viewModel.updateAge("25")
        viewModel.updatePhone("1234567890")
        viewModel.updatePassword("password123")
        viewModel.updateConfirmPassword("password123")

        viewModel.register(context, onSuccess = {
            isRegisterSuccess = true
        }, onFailure = {
            fail("Registration should have succeeded")
        })

        // Simulate the success response
        assertTrue("Registration should have succeeded", isRegisterSuccess)
    }

}


class RegistroScreenTest {

    @Test
    fun `validarDatos retorna true cuando todos los datos son correctos`() {
        val esDeNuevoLeon = true
        val edad = 25
        val ingresoMensual = 25000

        val resultado = validarDatos(esDeNuevoLeon, edad, ingresoMensual)

        assertTrue("La validación debería ser exitosa", resultado)
    }

    @Test
    fun `validarDatos retorna false cuando no es de Nuevo León`() {
        val esDeNuevoLeon = false
        val edad = 25
        val ingresoMensual = 25000

        val resultado = validarDatos(esDeNuevoLeon, edad, ingresoMensual)

        assertFalse("La validación debería fallar porque no es de Nuevo León", resultado)
    }

    @Test
    fun `validarDatos retorna false cuando la edad es menor de 18`() {
        val esDeNuevoLeon = true
        val edad = 17
        val ingresoMensual = 25000

        val resultado = validarDatos(esDeNuevoLeon, edad, ingresoMensual)

        assertFalse("La validación debería fallar porque la edad es menor de 18", resultado)
    }

    @Test
    fun `validarDatos retorna false cuando el ingreso mensual es mayor a 30000`() {
        val esDeNuevoLeon = true
        val edad = 25
        val ingresoMensual = 31000

        val resultado = validarDatos(esDeNuevoLeon, edad, ingresoMensual)

        assertFalse("La validación debería fallar porque el ingreso mensual es mayor a 30000", resultado)
    }

    @Test
    fun `validarDatos retorna false cuando no cumple ninguna condición`() {
        val esDeNuevoLeon = false
        val edad = 16
        val ingresoMensual = 50000

        val resultado = validarDatos(esDeNuevoLeon, edad, ingresoMensual)

        assertFalse("La validación debería fallar porque no cumple ninguna condición", resultado)
    }

}
