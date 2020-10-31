package solutions.octio.smits.features.auth.data.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import solutions.octio.smits.core.functional.Result
import solutions.octio.smits.core.functional.mapCatching
import solutions.octio.smits.core.functional.resultFrom
import solutions.octio.smits.features.auth.domain.AuthRepository

class AuthDataSource(private val authApi: AuthApi): AuthRepository {

    override suspend fun register(
        email: String,
        devDescription: String,
        deviceId: String
    ): Result<Unit> {
        return resultFrom {
            withContext(Dispatchers.IO) {
                authApi.register(
                    email = email,
                    devdescription = devDescription,
                    deviceId = deviceId
                )
            }
        }.mapCatching { }
    }


}