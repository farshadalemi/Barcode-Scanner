package com.barcodescanner.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barcodescanner.presentation.ui.screens.HomeScreen
import com.barcodescanner.presentation.ui.screens.ProductListScreen
import com.barcodescanner.presentation.ui.screens.ScannerScreen

@Composable
fun BarcodeScannerNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToScanner = { navController.navigate("scanner") },
                onNavigateToProductList = { navController.navigate("product_list") }
            )
        }
        
        composable("scanner") {
            ScannerScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToProductList = { 
                    navController.navigate("product_list") {
                        popUpTo("home")
                    }
                }
            )
        }
        
        composable("product_list") {
            ProductListScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
