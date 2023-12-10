package com.emyr78.theproj.ui.screen

sealed class Screen
data class DetailsScreen(
    val repoOwner: String,
    val repoName: String
): Screen()

interface ScreenNavigator{
    fun goToScreen(screen: Screen)
}