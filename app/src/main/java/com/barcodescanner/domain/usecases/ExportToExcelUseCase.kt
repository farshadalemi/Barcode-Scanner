package com.barcodescanner.domain.usecases

import android.content.Context
import com.barcodescanner.data.database.entities.Product
import com.barcodescanner.utils.ExcelExporter
import java.io.File
class ExportToExcelUseCase(
    private val excelExporter: ExcelExporter
) {
    suspend operator fun invoke(
        context: Context,
        products: List<Product>,
        fileName: String? = null
    ): Result<File> {
        return try {
            val file = excelExporter.exportToExcel(context, products, fileName)
            Result.success(file)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
