package com.maxtayler.cakes.di

import com.maxtayler.cakes.data.api.CakesApi
import com.maxtayler.cakes.data.api.CakesApiImpl
import com.maxtayler.cakes.data.repository.CakesRepository
import com.maxtayler.cakes.data.repository.CakesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CakesModule {

    @Binds
    @Singleton
    abstract fun bindCakesRepository(impl: CakesRepositoryImpl): CakesRepository

    @Binds
    @Singleton
    abstract fun bindCakesApi(impl: CakesApiImpl): CakesApi
}