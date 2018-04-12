package com.grzesiek.godt

import android.app.Application
import android.content.Context
import com.grzesiek.godt.data.remote.GodtApiService
import com.squareup.moshi.Moshi
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, ActivityBindingModule::class])
interface AppComponent {
    fun inject(godtApplication: GodtApplication)
}

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideGodtApiService(moshi: Moshi): GodtApiService = Retrofit.Builder()
            .baseUrl("https://www.godt.no/api/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GodtApiService::class.java)

    @Provides
    @Singleton
    fun providesSharedPrefs(context: Context) = context.getSharedPreferences("AppSharedPrefs", Context.MODE_PRIVATE)
}
