package com.nads.dindinnapp.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.nads.dindinnapp.api.DinDinnApiService
import com.nads.dindinnapp.models.OrderModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DinDinnAppServiceModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor.invoke { chain: Interceptor.Chain ->
                val orginal: Request = chain.request()
                val request = orginal.newBuilder().header("key", "GHYT 85KJ 74YU RTYU").method(
                    orginal.method,
                    orginal.body
                )
                    .build()

                chain.proceed(request)

            }).build()


        return client
    }
    @Singleton
    @Provides
    fun providesRetrofitClient(): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(Int::class.javaPrimitiveType, IntTypeAdapter())
            .registerTypeAdapter(Int::class.java, IntTypeAdapter()).create()
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl("https://0bdc25da-cf12-46cc-a1de-0296695c9081.mock.pstmn.io/")
            .client(providesOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideapiservice(retrofit: Retrofit):DinDinnApiService{
        val service: DinDinnApiService = retrofit.create(DinDinnApiService::class.java)
        return service
    }







    class IntTypeAdapter : TypeAdapter<Number?>() {
        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: Number?) {
            out.value(value)
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): Number? {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }
            return try {
                val result = `in`.nextString()
                if ("" == result) {
                    null
                } else result.toInt()
            } catch (e: NumberFormatException) {
                throw JsonSyntaxException(e)
            }
        }
    }



}