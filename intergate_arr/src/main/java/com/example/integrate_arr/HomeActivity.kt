package com.example.integrate_arr

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.Application.Companion.FLUTTER_ENGINE_NAME
import com.example.example_flutter_method_channel.EventChannelHandler
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.android.KeyData.CHANNEL
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

const val FLUTTER_ENGINE_ID = "nps_flutter_engine_name"
class HomeActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var flutterEngine: FlutterEngine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        val ClickMe: Button = findViewById(R.id.click_me)
        val buttonClick: Button = findViewById(R.id.button_click)
        val buttonBack: Button = findViewById(R.id.button_back)


        setupFlutterEngine()
        setupMethodChannel()




        val eventChannelHandler = EventChannelHandler(context = applicationContext)
        eventChannelHandler.startListening(flutterEngine,
            MyMainActivity.EVENT_CHANNEL,
            MyMainActivity.API_KEY
        )

        ClickMe.setOnClickListener {
            Intent().also { intent ->
                intent.action = "action.REPORT_GAME"
                intent.putExtra("reportGame", "API key Received.")
                sendBroadcast(intent)
            }
        }
        buttonClick.setOnClickListener{
            eventChannelHandler.onReportGameClicked(applicationContext, "HomeActivity")
            Intent().also { intent ->
                intent.action = "action.REPORT_GAME"
                intent.putExtra("reportGame", "Game Reported")
                sendBroadcast(intent)
            }
        }
        buttonBack.setOnClickListener {
            finish()
        }
    }



        private fun setupFlutterEngine() {
         createAndConfigureFlutterEngine()
           FlutterEngineCache
               .getInstance()
               .put(FLUTTER_ENGINE_ID, flutterEngine)
       }

       private fun createAndConfigureFlutterEngine() {
           flutterEngine = FlutterEngine(this)
           flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
       }

       private fun setupMethodChannel() {
           MethodChannel(
               flutterEngine.dartExecutor.binaryMessenger,
               CHANNEL
           ).setMethodCallHandler { call, result ->

           }
       }

      private fun launchFlutterModule() {
           startActivity(getFlutterIntent())
       }

       private fun getFlutterIntent(): Intent {
          return FlutterActivity
               .withCachedEngine(FLUTTER_ENGINE_NAME)
               .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
               .build(this)
       }
}