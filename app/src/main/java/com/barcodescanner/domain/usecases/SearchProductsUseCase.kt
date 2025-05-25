package com.barcodescanner.domain.usecases

import com.barcodescanner.data.database.entities.Product
import com.barcodescanner.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
class SearchProductsUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(searchQuery: String): Flow<List<Product>> {
        return if (searchQuery.isBlank()) {
            repository.getAllProducts()
        } else {
            repository.searchProducts(searchQuery.trim())
        }
    }
}
