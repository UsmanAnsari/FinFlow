package com.uansari.finflow.navigation

import kotlinx.serialization.Serializable

// Graph Containers
@Serializable
object OnboardingGraph

@Serializable
object MainGraph

// Onboarding Graph Destinations

@Serializable
object Welcome

@Serializable
object ChooseAccountType

@Serializable
data class PersonalDetails(val accountType: String)

@Serializable
object IdVerification

@Serializable
object Success

// Main Graph Destinations

@Serializable
object Home

@Serializable
object Cards

@Serializable
object Settings