package com.example.androidjetpackcompose

import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.parseAsHtml
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.androidjetpackcompose.model.Product
import com.example.androidjetpackcompose.ui.theme.AndroidJetPackComposeTheme
import com.example.androidjetpackcompose.view.HomeScreen
import com.example.androidjetpackcompose.view.InfoScreen
import com.example.androidjetpackcompose.viewModel.ProductViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {

    private val productViewModel by viewModels<ProductViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidJetPackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigator()
                }
            }
        }
    }

    @Composable
    fun AppNavigator() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "homeScreen") {
            composable(route = "homeScreen") {
                productViewModel.getMovieList()
                ProductList(productList = productViewModel.productListResponse, navController)
            }

            composable(route = "infoScreen/{brand}/{price}/{productType}/{name}/{category}/{image_link}/{description}", arguments = listOf(
                navArgument("brand") { type = NavType.StringType  } ,
                navArgument("price") { type = NavType.StringType  },
                navArgument("productType") { type = NavType.StringType  },
                navArgument("name") { type = NavType.StringType  },
                navArgument("category") { type = NavType.StringType  },
                navArgument("image_link") { type = NavType.StringType  },
                navArgument("description") { type = NavType.StringType  },

                ))
            { entry ->
                val brand = entry.arguments?.getString("brand") ?: ""
                val price = entry.arguments?.getString("price") ?: ""
                val productType = entry.arguments?.getString("productType") ?: ""
                val name = entry.arguments?.getString("name") ?: ""
                val category = entry.arguments?.getString("category") ?: ""
                val imageLink = entry.arguments?.getString("image_link") ?: ""
                val description = entry.arguments?.getString("description") ?: ""


                InfoScreen(
                    brand,
                    price,
                    productType,
                    name,
                    category,
                    imageLink,
                    description,
                    navController)
            }

        }
    }

    @Composable
    fun highPriceButton(productList: List<Product>){
        Button(onClick = {
            val result = productList.sortedBy { it.price }
            productViewModel.updateList(result)
        }, shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(5.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                disabledElevation = 0.dp
            )) {
            Text(text = "Menor preço", fontSize = 12.sp)
        }
    }

    @Composable
    fun lowPriceButton(productList: List<Product>){
        Button(onClick = {
            val result = productList.sortedByDescending { it.price }
            productViewModel.updateList(result)
        }, shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(5.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                disabledElevation = 0.dp
            )) {
            Text(text = "Maior preço", fontSize = 12.sp)
        }
    }

    @Composable
    fun alpAcsButton(productList: List<Product>){
        Button(onClick = {
            val result = productList.sortedBy{ it.name }
            productViewModel.updateList(result)
        }, shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(5.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                disabledElevation = 0.dp
            )) {
            Text(text = "A - Z",fontSize = 12.sp)
        }
    }

    @Composable
    fun alpDecButton(productList: List<Product>){
        Button(onClick = {
            val result = productList.sortedByDescending { it.name }
            productViewModel.updateList(result)
        }, shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(5.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                disabledElevation = 0.dp
            )) {
            Text(text = "Z - A",fontSize = 12.sp)
        }
    }

    @Composable
    fun ProductList(productList: List<Product>, navController: NavHostController) {
        fun navigateToInfo(
            brand: String,
            price: String,
            productType: String,
            name: String,
            category: String,
            image_link: String,
            description: String
        ) {
            val encodedUrl = URLEncoder.encode(image_link, StandardCharsets.UTF_8.toString())
            navController.navigate("infoScreen/$brand/$price/$productType/$name/$category/$encodedUrl/$description")
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(Color.Transparent).fillMaxWidth()
            ) {
                highPriceButton(productList)
                lowPriceButton(productList)
                alpAcsButton(productList)
                alpDecButton(productList)
            }
            LazyColumn {
                itemsIndexed(items = productList) { index, item ->
                    item.description = Html.fromHtml(item.description, Html.FROM_HTML_MODE_COMPACT).toString()
                    HomeScreen(product = item, onClick = {
                        val category = if(item.category.isNullOrEmpty()){
                            "..."
                        }else{
                            item.category
                        }
                        navigateToInfo(
                            item.brand,
                            item.price,
                            item.product_type,
                            item.name,
                            category,
                            item.image_link,
                            item.description
                        )
                    })
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidJetPackComposeTheme {
        val product = Product(
            "Coco",
            "https://howtodoandroid.com/images/coco.jpg",
            "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar",
            "Latest",
            "",
            "",
            ""
        )

        HomeScreen(product = product, onClick = {
            println("Testing")
        } )

    }
}





