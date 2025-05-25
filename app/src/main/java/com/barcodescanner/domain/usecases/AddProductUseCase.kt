package com.barcodescanner.domain.usecases

import com.barcodescanner.data.database.entities.Product
import com.barcodescanner.domain.repository.ProductRepository
class AddProductUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product): Long {
        // Check if product with same barcode already exists
        val existingProduct = repository.getProductByBarcode(product.barcode)

        return if (existingProduct != null) {
            // Update quantity of existing product
            repository.updateQuantityByBarcode(product.barcode, product.quantity)
            existingProduct.id
        } else {
            // Insert new product
            repository.insertProduct(product)
        }
    }
}
