package com.barcodescanner.utils

import android.content.Context
import com.barcodescanner.data.database.entities.Product
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
class ExcelExporter {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val fileNameDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())

    suspend fun exportToExcel(
        context: Context,
        products: List<Product>,
        fileName: String? = null
    ): File {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Products")

        // Create header style
        val headerStyle = workbook.createCellStyle().apply {
            fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
            fillPattern = FillPatternType.SOLID_FOREGROUND
            setBorderBottom(BorderStyle.THIN)
            setBorderTop(BorderStyle.THIN)
            setBorderRight(BorderStyle.THIN)
            setBorderLeft(BorderStyle.THIN)
        }

        val headerFont = workbook.createFont().apply {
            bold = true
        }
        headerStyle.setFont(headerFont)

        // Create data style
        val dataStyle = workbook.createCellStyle().apply {
            setBorderBottom(BorderStyle.THIN)
            setBorderTop(BorderStyle.THIN)
            setBorderRight(BorderStyle.THIN)
            setBorderLeft(BorderStyle.THIN)
        }

        // Create header row
        val headerRow = sheet.createRow(0)
        val headers = arrayOf(
            "ID", "Barcode", "Product Name", "Description",
            "Price", "Quantity", "Category", "Notes", "Timestamp"
        )

        headers.forEachIndexed { index, header ->
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            cell.cellStyle = headerStyle
        }

        // Add data rows
        products.forEachIndexed { index, product ->
            val row = sheet.createRow(index + 1)

            row.createCell(0).apply {
                setCellValue(product.id.toDouble())
                cellStyle = dataStyle
            }
            row.createCell(1).apply {
                setCellValue(product.barcode)
                cellStyle = dataStyle
            }
            row.createCell(2).apply {
                setCellValue(product.name)
                cellStyle = dataStyle
            }
            row.createCell(3).apply {
                setCellValue(product.description)
                cellStyle = dataStyle
            }
            row.createCell(4).apply {
                setCellValue(product.price)
                cellStyle = dataStyle
            }
            row.createCell(5).apply {
                setCellValue(product.quantity.toDouble())
                cellStyle = dataStyle
            }
            row.createCell(6).apply {
                setCellValue(product.category)
                cellStyle = dataStyle
            }
            row.createCell(7).apply {
                setCellValue(product.notes)
                cellStyle = dataStyle
            }
            row.createCell(8).apply {
                setCellValue(dateFormat.format(product.timestamp))
                cellStyle = dataStyle
            }
        }

        // Auto-size columns
        for (i in headers.indices) {
            sheet.autoSizeColumn(i)
        }

        // Create file
        val finalFileName = fileName ?: "products_export_${fileNameDateFormat.format(Date())}.xlsx"
        val file = File(context.getExternalFilesDir(null), finalFileName)

        // Write to file
        FileOutputStream(file).use { outputStream ->
            workbook.write(outputStream)
        }

        workbook.close()
        return file
    }
}
