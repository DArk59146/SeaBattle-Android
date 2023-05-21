package com.example.seabattle

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.seabattle.api.SeaBattleService
import com.example.seabattle.api.model.BooleanResponse
import com.example.seabattle.api.model.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

class LoginViewModel : ViewModel() {
    // Значение ошибки
    // -1 значит, что всё корректно
    // -2 значит, что значение уже обработанно
    val liveData = MutableLiveData<Int>()
    var sPreferences: SharedPreferences? = null


    fun login(user: UserDto) {
        SeaBattleService().getApi().login(
            user
        )
            .enqueue(object : Callback<BooleanResponse> {
                override fun onFailure(call: Call<BooleanResponse>, t: Throwable) {
                    if (t::class == ConnectException::class ||
                        t::class == SocketTimeoutException::class
                    ) {
                        liveData.value = R.string.lostConnection
                    } else {
                        liveData.value = R.string.unexpectedError
                    }
                }

                override fun onResponse(
                    call: Call<BooleanResponse>,
                    response: Response<BooleanResponse>
                ) {
                    if (response.isSuccessful && response.body()!!.getMessage() == "") {
                        sPreferences!!.edit().putString(
                            R.string.currentUsername.toString(),
                            user.username
                        )?.apply()
                        liveData.value = -1
                    } else {
                        liveData.value = R.string.unexpectedError
                    }
                }
            })
    }
}