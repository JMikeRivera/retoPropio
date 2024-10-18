package com.claymation.retopropio

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.claymation.retopropio.Screens.validarDatos
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
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
