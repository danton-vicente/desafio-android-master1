package com.picpay.desafio.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.picpay.desafio.android.presentation.navigation.ContactsNavHost
import com.picpay.desafio.android.ui.AppTheme

class ContactsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                Surface {
                    val navController = rememberNavController()
                    ContactsNavHost(
                        navController = navController,
                        doOnBackPressed = {
                            finish()
                        },
                    )
                }
            }
        }
    }
}
