package com.superbeta.blibberly

import android.app.Application
import com.superbeta.blibberly.di.mainModule
import com.superbeta.blibberly_auth.di.authModule
import com.superbeta.blibberly_chat.di.chatModule
import com.superbeta.blibberly_chat.di.profileOpsModule
import com.superbeta.profile_ops.report_ghost_block.di.reportModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(mainModule, authModule, chatModule, profileOpsModule, reportModule)
        }
    }
}