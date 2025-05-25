package com.barcodescanner.domain.repository

import com.barcodescanner.data.database.entities.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    suspend fun getProductById(id: Long): Product?
    suspend fun getProductByBarcode(barcode: String): Product?
    fun searchProducts(searchQuery: String): Flow<List<Product>>
    fun getProductsByCategory(category: String): Flow<List<Product>>
    suspend fun insertProduct(product: Product): Long
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteProductById(id: Long)
    suspend fun deleteAllProducts()
    suspend fun getProductCount(): Int
    fun getAllCategories(): Flow<List<String>>
    suspend fun updateQuantityByBarcode(barcode: String, additionalQuantity: Int)
}
