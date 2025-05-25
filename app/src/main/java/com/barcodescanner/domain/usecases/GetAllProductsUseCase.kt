package com.barcodescanner.domain.usecases

import com.barcodescanner.data.database.entities.Product
import com.barcodescanner.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
class GetAllProductsUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> = repository.getAllProducts()
}
