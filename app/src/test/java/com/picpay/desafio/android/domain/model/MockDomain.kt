package com.picpay.desafio.android.domain.model

import com.picpay.desafio.android.data.model.dummyUserResponse
import com.picpay.desafio.android.domain.models.Users

val dummyUsers = Users(
    id = 1,
    name = "Sandrine Spinka",
    img = "https://randomuser.me/api/portraits/men/1.jpg",
    username = "Tod86"
)

val dummyListUsers = listOf(
    dummyUsers,
    dummyUsers.copy(
        id = 2,
        name = "Carli Carroll",
        img = "https://randomuser.me/api/portraits/men/2.jpg",
        username = "Constantin_Sawayn"
    ),
    dummyUsers.copy(
        id = 3,
        name = "Annabelle Reilly",
        img = "https://randomuser.me/api/portraits/men/3.jpg",
        username = "Lawrence_Nader62"
    )
)