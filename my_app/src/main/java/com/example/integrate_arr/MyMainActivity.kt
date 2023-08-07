package com.example.integrate_arr


import android.content.Intent
import com.example.example_flutter_method_channel.EventChannelHandler
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MyMainActivity : FlutterActivity() {
    private val tag = "MainActivity"

    companion object {
         var API_KEY = "ssdfdsfsdfsdnbfsdkjhflisfjrwetrthe"
        const val METHOD_CHANNEL = "samples.flutter.io/game"
        const val EVENT_CHANNEL = "samples.flutter.io/report"
    }


    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {

        val eventChannelHandler = EventChannelHandler(context = applicationContext)
        eventChannelHandler.startListening(flutterEngine, EVENT_CHANNEL, API_KEY)


        MethodChannel(flutterEngine.dartExecutor, METHOD_CHANNEL).setMethodCallHandler { call, result ->
            if ((call.method == "startNewActivity")) {
                goHomeActivity()
            } else {
                result.notImplemented()
            }
        }
    }



    private fun goHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java).apply {

        }
        startActivity(intent)
    }
}