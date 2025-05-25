package com.barcodescanner.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.barcodescanner.data.database.entities.Product
import com.barcodescanner.domain.usecases.ExportToExcelUseCase
import com.barcodescanner.domain.usecases.GetAllProductsUseCase
import com.barcodescanner.domain.usecases.SearchProductsUseCase
import com.barcodescanner.domain.repository.ProductRepository
import com.barcodescanner.utils.ExcelExporter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

class ProductListViewModel(
    private val repository: ProductRepository,
    private val excelExporter: ExcelExporter
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private val getAllProductsUseCase = GetAllProductsUseCase(repository)
    private val searchProductsUseCase = SearchProductsUseCase(repository)
    private val exportToExcelUseCase = ExportToExcelUseCase(excelExporter)

    val products: StateFlow<List<Product>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            searchProductsUseCase(query)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.deleteProduct(product)
                _uiState.value = _uiState.value.copy(
                    message = "Product deleted successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to delete product: ${e.message}"
                )
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.updateProduct(product)
                _uiState.value = _uiState.value.copy(
                    message = "Product updated successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update product: ${e.message}"
                )
            }
        }
    }

    fun exportToExcel(context: Context, products: List<Product>) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isExporting = true)
            
            exportToExcelUseCase(context, products)
                .onSuccess { file ->
                    _uiState.value = _uiState.value.copy(
                        isExporting = false,
                        exportedFile = file,
                        message = "Excel file exported successfully"
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isExporting = false,
                        error = "Failed to export: ${error.message}"
                    )
                }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearExportedFile() {
        _uiState.value = _uiState.value.copy(exportedFile = null)
    }
}

data class ProductListUiState(
    val isExporting: Boolean = false,
    val exportedFile: File? = null,
    val message: String? = null,
    val error: String? = null
)

class ProductListViewModelFactory(
    private val repository: ProductRepository,
    private val excelExporter: ExcelExporter
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository, excelExporter) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
