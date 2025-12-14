//package com.example.musicapp
//
//import android.content.Intent
//import android.os.Bundle
//import android.os.PersistableBundle
//import android.widget.Toast
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.mutableStateOf
//import androidx.core.content.ContentProviderCompat.requireContext
//
//class MainActivity : ComponentActivity() {
//    private val requester = PermissionRequester.instance()
//    private var permissionGrantedState by mutableStateOf(false)
//
//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//
//        setContent {
//            MaterialTheme {
//                if (permissionGrantedState) {
//                    PermissionsGranted()
//                } else {
//                    GrantPermissions { requestPermissons() }
//                }
//            }
//        }
//    }
//
//    private fun requestPermissions() {
//        lifecycleScope.launch {
//            requester.request(Manifest.permission.CAMERA).collect { result ->
//                when (result) {
//                    is PermissionResult.Granted -> {
//                        permissionGrantedState = true
//                    }
//                    is PermissionResult.Denied.DeniedPermanently -> {
//                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        intent.data= Uri.fromParts("package", context.packageName, null)
//                        context.startActivity(intent)
//                    }
//                    is PermissionResult.Denied.NeedsRationale -> {
//                        Toast.makeText(requireContext(), "Разрешение на использование геолокации необходимо для доступа к Bluetooth-устройствам", Toast.LENGTH_LONG).show()
//                        permissionGrantedState = false
//                    }
//                    is PermissionResult.Cancelled -> {
//                        return@collect
//                    }
//                }
//            }
//        }
//    }
//}