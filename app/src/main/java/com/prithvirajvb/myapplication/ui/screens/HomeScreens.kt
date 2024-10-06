@file:OptIn(ExperimentalMaterial3Api::class)

package com.prithvirajvb.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prithvirajvb.myapplication.utils.Dest

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onItemClicked: (Int) -> Unit) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Home")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) {
        LazyColumn(
            modifier = modifier.padding(it),
        ) {
            items(100) { index ->
                ListItem(
                    headlineContent = {
                        Text("Item $index")
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 10.dp)
                        .clickable {
                            onItemClicked(index)
                        },
                )
            }
        }
    }

}

@Composable
fun HomeDetailScreen(
    modifier: Modifier = Modifier,
    dest: Dest.Detail,
    onBackClicked: () -> Unit,
    onCartClicked: () -> Unit,
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Home Detail")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton({
                        onBackClicked()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                            contentDescription = null
                        )
                    }

                },
                actions = {
                    // cart navigate to cart
                    IconButton({
                        onCartClicked()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->

        Text("Detail Screen for ${dest.id}", modifier = modifier.padding(padding))

    }
}


