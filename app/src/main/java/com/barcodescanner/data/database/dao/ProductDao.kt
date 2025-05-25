package com.barcodescanner.data.database.dao

import androidx.room.*
import com.barcodescanner.data.database.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    
    @Query("SELECT * FROM products ORDER BY timestamp DESC")
    fun getAllProducts(): Flow<List<Product>>
    
    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Long): Product?
    
    @Query("SELECT * FROM products WHERE barcode = :barcode")
    suspend fun getProductByBarcode(barcode: String): Product?
    
    @Query("SELECT * FROM products WHERE name LIKE '%' || :searchQuery || '%' OR barcode LIKE '%' || :searchQuery || '%'")
    fun searchProducts(searchQuery: String): Flow<List<Product>>
    
    @Query("SELECT * FROM products WHERE category = :category ORDER BY timestamp DESC")
    fun getProductsByCategory(category: String): Flow<List<Product>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long
    
    @Update
    suspend fun updateProduct(product: Product)
    
    @Delete
    suspend fun deleteProduct(product: Product)
    
    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProductById(id: Long)
    
    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()
    
    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductCount(): Int
    
    @Query("SELECT DISTINCT category FROM products WHERE category != '' ORDER BY category")
    fun getAllCategories(): Flow<List<String>>
    
    @Query("UPDATE products SET quantity = quantity + :additionalQuantity WHERE barcode = :barcode")
    suspend fun updateQuantityByBarcode(barcode: String, additionalQuantity: Int)
}
