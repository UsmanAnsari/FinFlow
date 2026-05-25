package com.uansari.finflow.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.uansari.finflow.ui.main.screens.HomeScreen

// Bottom Nav Items

private data class BottomNavItem(
    val route: Any, val label: String, val icon: @Composable () -> Unit
)

private val bottomNavItems = listOf(
    BottomNavItem(
        route = Home,
        label = "Home",
        icon = { Icon(Icons.Rounded.Home, contentDescription = "Home") },
    ), BottomNavItem(
        route = Cards,
        label = "Cards",
        icon = { Icon(Icons.Rounded.CreditCard, contentDescription = "Cards") },
    ), BottomNavItem(
        route = Settings,
        label = "Settings",
        icon = { Icon(Icons.Rounded.Settings, contentDescription = "Settings") },
    )
)

// Main Graph

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation<MainGraph>(startDestination = Home) {

        composable<Home> {
            MainScaffold(navController = navController) {
                HomeScreen()
            }
        }

        composable<Cards> {
            MainScaffold(navController = navController) {
                PlaceholderScreen(label = "Cards")
            }
        }

        composable<Settings> {
            MainScaffold(navController = navController) {
                PlaceholderScreen(label = "Settings")
            }
        }
    }
}

// Shared Scaffold with Bottom Nav

@Composable
private fun MainScaffold(
    navController: NavController, content: @Composable () -> Unit
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.route?.contains(
                            item.route::class.qualifiedName ?: ""
                        ) ?: false, onClick = {
                            navController.navigate(item.route) {
                                // Pop to start destination — avoids
                                // building a large back stack
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }, icon = item.icon, label = { Text(item.label) })
                }
            }
        }) { innerPadding ->
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            content()
        }
    }
}

// Placeholder for Cards & Settings

@Composable
private fun PlaceholderScreen(label: String) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(
            text = label, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )
    }
}
