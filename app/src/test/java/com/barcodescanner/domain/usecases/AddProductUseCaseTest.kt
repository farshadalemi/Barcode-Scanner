package com.barcodescanner.domain.usecases

import com.barcodescanner.data.database.entities.Product
import com.barcodescanner.domain.repository.ProductRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class AddProductUseCaseTest {

    @Mock
    private lateinit var repository: ProductRepository

    private lateinit var useCase: AddProductUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = AddProductUseCase(repository)
    }

    @Test
    fun `should insert new product when barcode doesn't exist`() = runTest {
        // Given
        val product = Product(0, "123456", "Test Product", "Description", 10.0, 1, Date())
        `when`(repository.getProductByBarcode(product.barcode)).thenReturn(null)
        `when`(repository.insertProduct(product)).thenReturn(1L)

        // When
        val result = useCase(product)

        // Then
        verify(repository).getProductByBarcode(product.barcode)
        verify(repository).insertProduct(product)
        assert(result == 1L)
    }

    @Test
    fun `should update quantity when barcode already exists`() = runTest {
        // Given
        val newProduct = Product(0, "123456", "Test Product", "Description", 10.0, 2, Date())
        val existingProduct = Product(1, "123456", "Existing Product", "Description", 10.0, 1, Date())
        
        `when`(repository.getProductByBarcode(newProduct.barcode)).thenReturn(existingProduct)

        // When
        val result = useCase(newProduct)

        // Then
        verify(repository).getProductByBarcode(newProduct.barcode)
        verify(repository).updateQuantityByBarcode(newProduct.barcode, newProduct.quantity)
        verify(repository, never()).insertProduct(any())
        assert(result == existingProduct.id)
    }
}
