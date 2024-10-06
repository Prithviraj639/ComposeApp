package com.prithvirajvb.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.prithvirajvb.myapplication.ui.screens.AppBottomBar
import com.prithvirajvb.myapplication.ui.screens.HomeDetailScreen
import com.prithvirajvb.myapplication.ui.screens.LoginScreen
import com.prithvirajvb.myapplication.ui.theme.MyApplicationTheme
import com.prithvirajvb.myapplication.utils.Dest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavHost()
                }
            }
        }
    }
}


@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
    val navController = rememberNavController(

    )
    val bottomNavController = rememberNavController()

//    LaunchedEffect(navController) {
        navController.setViewModelStore(ViewModelStore())
//    }

//    LaunchedEffect(bottomNavController) {
        bottomNavController.setViewModelStore(ViewModelStore())
//    }




    NavHost(
        navController = navController,
        startDestination = Dest.AuthGraph,
        modifier = modifier
    ) {
        addAuthGraph(navController)
        addHomeGraph(navController, bottomNavController)
    }
}


fun NavGraphBuilder.addAuthGraph(
    navController: NavController,
) {
    navigation<Dest.AuthGraph>(
        startDestination = Dest.Login,
    ) {
        composable<Dest.Login> {
            LoginScreen(
                onLoginClick = { s: String, s2: String ->
                    navController.navigate(Dest.MainGraph) {
                        launchSingleTop = true
                        popUpTo(Dest.AuthGraph) { inclusive = true }
                    }
                }
            )
        }

    }
}

fun NavGraphBuilder.addHomeGraph(
    navController: NavController,
    bottomNavController: NavHostController,
) {
    navigation<Dest.MainGraph>(
        startDestination = Dest.BottomHome,
    ) {
        composable<Dest.BottomHome> {
            AppBottomBar(
                mainNavController = navController,
                bottomAppNavController = bottomNavController,
            )
        }

        composable<Dest.Detail> {
            HomeDetailScreen(
                dest = it.toRoute(),
                onBackClicked = {
                    navController.navigateUp()
                },
                onCartClicked = {
                    // navigate to cart

                    navController.popBackStack(
                        Dest.BottomHome,
                        inclusive = false
                    )

                    bottomNavController.navigate(Dest.Cart)


                },
            )
        }

    }
}



