package com.linh.core.data.repository.utils

import com.linh.core.data.remote.utils.NetworkException
import com.linh.core.domain.repository.utils.Resource
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException

inline fun <Entity, Dto, Domain> networkBoundResource(
    crossinline query: () -> Flow<Entity>,
    crossinline fetch: suspend (cachedData: Entity) -> Result<Dto>,
    crossinline saveFetchResult: suspend (Dto?) -> Unit,
    crossinline shouldFetch: (Entity) -> Boolean = { true },
    crossinline entityToDomain: (Entity) -> Domain
): Flow<Resource<Domain?>> = flow {


    val initQuery = query()
    val data = initQuery.first()

    //If shouldFetch returns true,
    val resource = if (shouldFetch(data)) {

        //Dispatch a message to the UI that you're doing some background work
        emit(Resource.Loading(entityToDomain(data)))

        try {
            val networkResult = fetch(data)
            if (networkResult.isSuccess) {
                saveFetchResult(networkResult.getOrNull())
                query().map { Resource.Success(entityToDomain(it)) }
            } else {
                initQuery.map {
                    Resource.Error(
                        networkResult.exceptionOrNull() ?: NetworkException.UnknownError,
                        entityToDomain(it)
                    )
                }
            }

        } catch (e: UnresolvedAddressException) {
            initQuery.map { Resource.Success(entityToDomain(it)) }
        } catch (e: IOException) {
            initQuery.map { Resource.Success(entityToDomain(it)) }
        } catch (throwable: Throwable) {
            //Dispatch any error emitted to the UI, plus data emitted from the Database
            initQuery.map { Resource.Error(Exception(throwable.message), entityToDomain(it)) }

        }

        //If should fetch returned false
    } else {
        //Make a query to the database and Dispatch it to the UI.
        query().map { Resource.Success(entityToDomain(it)) }
    }

    //Emit the resource variable
    emitAll(resource)
}