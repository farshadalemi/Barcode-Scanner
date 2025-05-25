package com.barcodescanner.data.repository

import com.barcodescanner.data.database.dao.ProductDao
import com.barcodescanner.data.database.entities.Product
import com.barcodescanner.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {

    override fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    override suspend fun getProductById(id: Long): Product? = productDao.getProductById(id)

    override suspend fun getProductByBarcode(barcode: String): Product? = productDao.getProductByBarcode(barcode)

    override fun searchProducts(searchQuery: String): Flow<List<Product>> = productDao.searchProducts(searchQuery)

    override fun getProductsByCategory(category: String): Flow<List<Product>> = productDao.getProductsByCategory(category)

    override suspend fun insertProduct(product: Product): Long = productDao.insertProduct(product)

    override suspend fun updateProduct(product: Product) = productDao.updateProduct(product)

    override suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)

    override suspend fun deleteProductById(id: Long) = productDao.deleteProductById(id)

    override suspend fun deleteAllProducts() = productDao.deleteAllProducts()

    override suspend fun getProductCount(): Int = productDao.getProductCount()

    override fun getAllCategories(): Flow<List<String>> = productDao.getAllCategories()

    override suspend fun updateQuantityByBarcode(barcode: String, additionalQuantity: Int) =
        productDao.updateQuantityByBarcode(barcode, additionalQuantity)
}
