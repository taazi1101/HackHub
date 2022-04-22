package com.example.hackhub

import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Formatter
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.lang.reflect.Executable
import java.net.InetAddress
import java.util.*
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class IpSweepper : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ip_sweepper)
        val perIp:TextView = findViewById(R.id.pIp)
        val log:TextView = findViewById(R.id.log)
        val recv:LinearLayout = findViewById(R.id.recv)
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddress: String = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
        perIp.setText(ipAddress)
        val ipParts = ipAddress.split(".").toMutableList()
        println(ipParts.toString())
        val own = ipParts.removeLast().toInt()
        println(ipParts.toString())
        var format = ""
        for (part in ipParts.indices){

            println(part.toString())
            format += ipParts[part]
            if (part < ipParts.size){
                format += "."
            }
        }
        format += "FUZZ"
        println(format.toString())
        var ip:String
        for (i in 0..256)
        {
            if (i == own){
                continue
            }
            ip = format.replace("FUZZ",i.toString())
            log.setText(ip)
            thread (start = true) { pinger(ip,10000,recv) }

        }

    }

    fun pinger(ip:String,timeout:Int,rec:LinearLayout)
    {
        try {
            val addr = InetAddress.getByName(ip)
            val rechbl = addr.isReachable(timeout)
            if (rechbl) {
                var text = TextView(this)
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F);
                text.setText(ip)
                rec.addView(text)
            }
        }catch (ex:Exception){
            println("ERROR")
        }
    }
}