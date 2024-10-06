package com.prithvirajvb.myapplication.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class Dest {
    @Serializable
    data object Home : Dest()

    @Serializable
    data object BottomHome : Dest()

    @Serializable
    data class Detail(val id: Int) : Dest()

    @Serializable
    data object Cart : Dest()

    @Serializable
    data object Profile : Dest()

    @Serializable
    data object Login : Dest()

    @Serializable
    data object Register : Dest()

    @Serializable
    data object ForgotPassword : Dest()

    @Serializable
    data object AuthGraph : Dest()

    @Serializable
    data object MainGraph : Dest()


}