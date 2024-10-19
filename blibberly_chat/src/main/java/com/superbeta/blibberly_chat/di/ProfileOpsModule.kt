package com.superbeta.blibberly_chat.di

import com.blibberly.blibberly_likes.data.local.ProfileOpsDao
import com.blibberly.blibberly_likes.domain.ProfileOpsRepo
import com.blibberly.blibberly_likes.domain.ProfileOpsRepoImpl
import com.blibberly.blibberly_likes.presentation.viewmodel.ProfileOpsViewModel
import com.superbeta.blibberly_chat.data.local.BlibberlyRoomInstanceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileOpsModule = module {

    single<ProfileOpsDao> {
        BlibberlyRoomInstanceProvider.getDatabase(androidContext()).ProfileOpsDao()
    }

    single<ProfileOpsRepo> {
        ProfileOpsRepoImpl(db = get())
    }

    viewModel {
        ProfileOpsViewModel(profileOpsRepo = get())
    }
}