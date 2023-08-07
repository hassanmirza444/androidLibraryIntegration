package com.example.example_flutter_method_channel

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel

class EventChannelHandler(val context: Context) {

    fun startListening(flutterEngine: FlutterEngine, channel: String, api_key: String) {


        val receiver = BroadcastReceiverHandler()
        val filter = IntentFilter("action.REPORT_GAME")
        context.registerReceiver(receiver, filter)


        EventChannel(flutterEngine.dartExecutor, channel).setStreamHandler(
            object : EventChannel.StreamHandler {
                override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink) {
                    Log.i("hssn", "Android->Success onListen Game")
                    eventSink.success("API Key= $api_key")
                    receiver.setListener(object : BroadcastReceiverHandlerListener() {
                        override fun onReportGame(detail: String?) {
                            Log.i("hssn", "Android->Success Reported Game: $detail")
                            eventSink.success(detail)
                        }
                    })
                }

                override fun onCancel(arguments: Any?) {
                    context.unregisterReceiver(receiver)
                }
            }
        )
    }

    fun onReportGameClicked(context: Context, tag: String) {
        Log.i("hssn", "Android->Report Game Clicked")

    }
}