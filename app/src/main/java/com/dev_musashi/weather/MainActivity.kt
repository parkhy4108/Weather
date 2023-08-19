package com.dev_musashi.weather

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionLauncher.launch(permissions)
        setContent { MainContent() }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionList ->
        permissionList.forEach { permission ->
            if(!permission.value) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(this@MainActivity, "권한이 필요합니다. 설정에서 권한을 승인하여 주시길 바랍니다.", Toast.LENGTH_LONG).show()
                    delay(2000)
                    ActivityCompat.finishAffinity(this@MainActivity)
                    exitProcess(0)
                }
            }
        }

    }
}
