package com.barcodescanner.data.repository

import com.barcodescanner.data.database.dao.ProductDao
import com.barcodescanner.data.database.entities.Product
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class ProductRepositoryTest {

    @Mock
    private lateinit var productDao: ProductDao

    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = ProductRepositoryImpl(productDao)
    }

    @Test
    fun `getAllProducts should return flow from dao`() = runTest {
        // Given
        val products = listOf(
            Product(1, "123456", "Test Product", "Description", 10.0, 1, Date())
        )
        `when`(productDao.getAllProducts()).thenReturn(flowOf(products))

        // When
        val result = repository.getAllProducts()

        // Then
        verify(productDao).getAllProducts()
    }

    @Test
    fun `insertProduct should call dao insert`() = runTest {
        // Given
        val product = Product(0, "123456", "Test Product", "Description", 10.0, 1, Date())
        `when`(productDao.insertProduct(product)).thenReturn(1L)

        // When
        val result = repository.insertProduct(product)

        // Then
        verify(productDao).insertProduct(product)
        assert(result == 1L)
    }

    @Test
    fun `getProductByBarcode should return product from dao`() = runTest {
        // Given
        val barcode = "123456"
        val product = Product(1, barcode, "Test Product", "Description", 10.0, 1, Date())
        `when`(productDao.getProductByBarcode(barcode)).thenReturn(product)

        // When
        val result = repository.getProductByBarcode(barcode)

        // Then
        verify(productDao).getProductByBarcode(barcode)
        assert(result == product)
    }
}
