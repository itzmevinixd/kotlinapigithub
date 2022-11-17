package br.com.kotlinapigithub.di

import br.com.kotlinapigithub.data.GithubApi
import br.com.kotlinapigithub.utilities.Constants.Companion.BASE_URL_GIT_HUB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL_GIT_HUB)
        .addConverterFactory(JacksonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideGithubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)
}