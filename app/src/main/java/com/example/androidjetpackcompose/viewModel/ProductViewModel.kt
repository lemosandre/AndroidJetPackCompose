package com.example.androidjetpackcompose.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidjetpackcompose.model.Product
import com.example.androidjetpackcompose.network.ApiService
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    var productListResponse:List<Product> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
    fun getMovieList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val productList = apiService.getProduct()
                productListResponse = productList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun updateList(products: List<Product>) {
        productListResponse = products
        viewModelScope.cancel()
    }
}