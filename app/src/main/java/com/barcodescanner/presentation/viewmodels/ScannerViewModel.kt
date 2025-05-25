package com.barcodescanner.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.barcodescanner.data.database.entities.Product
import com.barcodescanner.domain.usecases.AddProductUseCase
import com.barcodescanner.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class ScannerViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState: StateFlow<ScannerUiState> = _uiState.asStateFlow()

    private val _scannedBarcode = MutableStateFlow<String?>(null)
    val scannedBarcode: StateFlow<String?> = _scannedBarcode.asStateFlow()

    fun onBarcodeScanned(barcode: String) {
        _scannedBarcode.value = barcode
        _uiState.value = _uiState.value.copy(
            scannedBarcode = barcode,
            showAddProductDialog = true
        )
    }

    fun onAddProduct(
        barcode: String,
        name: String,
        description: String = "",
        price: Double = 0.0,
        quantity: Int = 1,
        category: String = "",
        notes: String = ""
    ) {
        viewModelScope.launch {
            try {
                val product = Product(
                    barcode = barcode,
                    name = name,
                    description = description,
                    price = price,
                    quantity = quantity,
                    category = category,
                    notes = notes,
                    timestamp = Date()
                )
                
                val addProductUseCase = AddProductUseCase(repository)
                addProductUseCase(product)
                
                _uiState.value = _uiState.value.copy(
                    showAddProductDialog = false,
                    message = "Product added successfully"
                )
                
                // Clear scanned barcode
                _scannedBarcode.value = null
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to add product: ${e.message}"
                )
            }
        }
    }

    fun dismissDialog() {
        _uiState.value = _uiState.value.copy(
            showAddProductDialog = false,
            scannedBarcode = null
        )
        _scannedBarcode.value = null
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class ScannerUiState(
    val scannedBarcode: String? = null,
    val showAddProductDialog: Boolean = false,
    val message: String? = null,
    val error: String? = null
)

class ScannerViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScannerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScannerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
