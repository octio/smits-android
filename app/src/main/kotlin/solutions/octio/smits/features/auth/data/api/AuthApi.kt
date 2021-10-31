package solutions.octio.smits.features.auth.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface AuthApi {

    @GET("")
    @Headers("DeviceID:{deviceId}")
    suspend fun register(
        @Body email: String,
        @Body devdescription: String,
        @Header("deviceId") deviceId: String
    ): Response<Unit>

}