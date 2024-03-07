package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.models.UsersResponse
import com.picpay.desafio.android.domain.models.Users

internal fun UsersResponse.toUsers() = Users(
    id = id, name = name, img = img, username = username
)