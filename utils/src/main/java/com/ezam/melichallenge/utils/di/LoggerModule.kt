package com.ezam.melichallenge.utils.di

import com.ezam.melichallenge.utils.utils.Logger
import com.ezam.melichallenge.utils.utils.TimberLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface LoggerModule {

    @Binds
    fun bindsLogger( impl: TimberLogger): Logger
}