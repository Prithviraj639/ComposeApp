package com.prithvirajvb.myapplication.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ShoppingBasket
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.prithvirajvb.myapplication.utils.Dest

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

val topLevelRoutes = listOf(
    TopLevelRoute("Home", Dest.Home, Icons.Rounded.Home),
    TopLevelRoute("Cart", Dest.Cart, Icons.Rounded.ShoppingBasket),
    TopLevelRoute("Profile", Dest.Profile, Icons.Rounded.Home),
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    bottomAppNavController: NavHostController = rememberNavController(),
    mainNavController: NavController,
) {

    val insets = WindowInsets.navigationBars.asPaddingValues()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            val navBackStackEntry by bottomAppNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            NavigationBar {

                topLevelRoutes.forEach { topLevelRoute ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true

                    NavigationBarItem(
                        selected = selected,
                        icon = {
                            Icon(
                                imageVector = topLevelRoute.icon,
                                contentDescription = null
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        onClick = {
                            bottomAppNavController.navigate(topLevelRoute.route) {
                                popUpTo(bottomAppNavController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true

                            }


                        }

                    )


                }

            }


        },
        content = { paddingValues ->

            NavHost(
                navController = bottomAppNavController,
                startDestination = Dest.Home,
                modifier = Modifier.padding(
                    bottom = paddingValues.calculateBottomPadding()
                        .minus(insets.calculateBottomPadding())
                ),
                builder = {
                    composable<Dest.Home> {
                        HomeScreen { index ->
                            mainNavController.navigate(Dest.Detail(index))
                        }
                    }

                    composable<Dest.Cart> {
                        Text("Cart Screen")
                    }

                    composable<Dest.Profile> {
                        ProfilePage(
                            onLogoutClick = {
                                mainNavController.navigate(Dest.AuthGraph) {
                                    launchSingleTop = true
                                    popUpTo(Dest.MainGraph) { inclusive = true }
                                }
                            }
                        )
                    }

                }
            )

        }
    )


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(modifier: Modifier = Modifier, onLogoutClick: () -> Unit) {

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )

            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Button(
                onClick = onLogoutClick,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Logout")
            }

        }
    }

}