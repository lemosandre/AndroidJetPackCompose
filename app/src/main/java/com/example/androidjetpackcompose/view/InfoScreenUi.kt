package com.example.androidjetpackcompose.view

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter


@Composable
fun InfoScreen(
    brand: String,
    price: String,
    productType: String,
    name: String,
    category: String,
    image_link: String,
    description: String,
    navController: NavController)
{
        Scaffold(topBar = {
            TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Descrição de produto", fontWeight = FontWeight.Bold)
                }
            }
        }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Card(
                    shape = RoundedCornerShape(8.dp), elevation = 4.dp,
                    modifier = Modifier
                        .padding(10.dp, 10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = image_link,
                            ),
                            contentDescription = "Imager",
                            modifier = Modifier.size(150.dp).clip(CircleShape)
                        )

                        Text(
                            text = name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Marca", fontWeight = FontWeight.Bold)
                        Text(text = brand)

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Preco", fontWeight = FontWeight.Bold)
                        Text(text = price)

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Tipo de produto", fontWeight = FontWeight.Bold)
                        Text(text = productType)

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Categoria", fontWeight = FontWeight.Bold)
                        Text(text = category, textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Decricao", fontWeight = FontWeight.Bold)
                        Text(text = description, textAlign = TextAlign.Center)
                    }

                }
            }
        }
}
