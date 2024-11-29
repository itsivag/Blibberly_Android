package com.superbeta.blibberly.root

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BlibberlyTopAppBar() {
    TopAppBar(
        title = { Text(text = "Blibberly") },
//        navigationIcon = {
//            IconButton(onClick = { navController.navigate(Screen.Filter.route) }) {
//                Icon(
//                    imageVector = ImageVector.vectorResource(R.drawable.filter),
//                    contentDescription = "Filter"
//                )
//            }
//        },
//        actions = {
//            IconButton(onClick = { navController.navigate(Screen.Filter.route) }) {
//                Icon(
//                    imageVector = ImageVector.vectorResource(R.drawable.filter),
//                    contentDescription = "Filter"
//                )
//            }
//        }
    )
}