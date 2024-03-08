package com.picpay.desafio.android.data.model

import com.picpay.desafio.android.data.models.UsersResponse

val dummyUserResponse = UsersResponse(
    id = 1,
    name = "Sandrine Spinka",
    img = "https://randomuser.me/api/portraits/men/1.jpg",
    username = "Tod86"
)

val dummyListUsersResponse = listOf(
    dummyUserResponse,
    dummyUserResponse.copy(
        id = 2,
        name = "Carli Carroll",
        img = "https://randomuser.me/api/portraits/men/2.jpg",
        username = "Constantin_Sawayn"
    ),
    dummyUserResponse.copy(
        id = 3,
        name = "Annabelle Reilly",
        img = "https://randomuser.me/api/portraits/men/3.jpg",
        username = "Lawrence_Nader62"
    )
)
