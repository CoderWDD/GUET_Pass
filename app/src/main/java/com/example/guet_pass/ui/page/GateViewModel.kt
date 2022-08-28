package com.example.guet_pass.ui.page

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GateViewModel: ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    val timeFlow = flow {
        var now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss:SS"))
        while (now.isNotEmpty()){
            delay(10)
            now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss:SS"))
            emit(now)
        }
    }.flowOn(Dispatchers.IO)
        .shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
        replay = 1
    )

    var gateViewState by mutableStateOf(GateViewState(timeFlow = timeFlow))
        private set

    fun intentHandler(action: GateViewAction){
        when(action) {
            is GateViewAction.SetUsername -> setUsername(action.username)
        }
    }

    private fun setUsername(username: String){
        gateViewState = gateViewState.copy(username = username)
    }


}

sealed class GateViewAction{
    class SetUsername(val username: String) : GateViewAction()
}

data class GateViewState(
    val username: String = "",
    val timeFlow: SharedFlow<String>
)