package com.example.guet_pass

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.guet_pass.ui.page.GUETGatePage
import com.example.guet_pass.ui.theme.GUET_PassTheme
import com.example.guet_pass.ui.theme.Green

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            GUET_PassTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Green
                ) {
                    GUETGatePage()
                }
            }
        }
    }
}