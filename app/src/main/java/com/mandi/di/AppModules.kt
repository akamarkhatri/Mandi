package com.mandi.di

import android.app.Application
import com.mandi.data.AppContainer
import com.mandi.data.AppContainerImpl
import com.mandi.data.DefaultDispatcher
import com.mandi.data.DispatcherProvider
import com.mandi.data.commodity.CommodityRepository
import com.mandi.data.commodity.impl.FakeCommodityRepository
import com.mandi.data.seller.SellerRepository
import com.mandi.data.seller.impl.FakeSellerRepository
import com.mandi.data.village.VillageRepository
import com.mandi.data.village.impl.FakeVillageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    fun provideAppContainer(
        sellerRepository: SellerRepository,
        villageRepository: VillageRepository,
        commodityRepository: CommodityRepository
    ):AppContainer {
        return AppContainerImpl(sellerRepository, villageRepository, commodityRepository)
    }

    @Provides
    fun provideSellerRepository(application: Application, dispatcherProvider: DispatcherProvider): SellerRepository {
        return FakeSellerRepository(dispatcherProvider)
    }

    @Provides
    fun provideVillageRepository(application: Application, dispatcherProvider: DispatcherProvider): VillageRepository {
        return FakeVillageRepository(dispatcherProvider)
    }

    @Provides
    fun provideCommodityRepository(application: Application, dispatcherProvider: DispatcherProvider): CommodityRepository {
        return FakeCommodityRepository(dispatcherProvider)
    }

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcher()
    }

}