package solutions.octio.smits.features.auth.domain

import solutions.octio.smits.core.functional.Result

interface AuthRepository {

    suspend fun register(
        email: String,
        devDescription: String,
        deviceId: String,
    ): Result<Unit>

}