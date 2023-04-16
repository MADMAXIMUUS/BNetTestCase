package ru.madmax.bnettestcase.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.madmax.bnettestcase.data.dataSource.DragApi
import ru.madmax.bnettestcase.data.repository.DragRepository
import ru.madmax.bnettestcase.domain.repository.DragRepositoryImpl
import ru.madmax.bnettestcase.domain.useCase.DragUseCases
import ru.madmax.bnettestcase.domain.useCase.GetByIdUseCase
import ru.madmax.bnettestcase.domain.useCase.SearchUseCase
import ru.madmax.bnettestcase.util.Constants.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideDragApi(client: OkHttpClient): DragApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(DragApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDragRepository(dragApi: DragApi): DragRepository {
        return DragRepositoryImpl(dragApi)
    }

    @Provides
    @Singleton
    fun provideDragUseCases(dragRepository: DragRepository): DragUseCases {
        return DragUseCases(
            searchUseCase = SearchUseCase(dragRepository),
            getByIdUseCase = GetByIdUseCase(dragRepository)
        )
    }

}