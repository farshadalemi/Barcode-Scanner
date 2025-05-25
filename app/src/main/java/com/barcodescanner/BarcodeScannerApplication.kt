package com.barcodescanner

import android.app.Application
import com.barcodescanner.data.database.AppDatabase
import com.barcodescanner.data.repository.ProductRepositoryImpl
import com.barcodescanner.domain.repository.ProductRepository
import com.barcodescanner.utils.ExcelExporter

class BarcodeScannerApplication : Application() {
    
    // Database
    val database by lazy { AppDatabase.getDatabase(this) }
    
    // Repository
    val repository: ProductRepository by lazy { 
        ProductRepositoryImpl(database.productDao()) 
    }
    
    // Utils
    val excelExporter by lazy { ExcelExporter() }
}
