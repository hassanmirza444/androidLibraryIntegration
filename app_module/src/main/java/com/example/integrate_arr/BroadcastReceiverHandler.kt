package com.example.example_flutter_method_channel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class BroadcastReceiverHandler : BroadcastReceiver() {

    private lateinit var callback: BroadcastReceiverHandlerListener

    fun setListener(callback: BroadcastReceiverHandlerListener) {
        this.callback = callback
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action == "action.REPORT_GAME") {
            val info = intent.getStringExtra("reportGame")
            Log.i("hssn", "Android->onReceive Reported Game: $info")
            callback.onReportGame(info)
        }
    }
}