package com.example.hackhub

import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Formatter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.lang.Exception
import java.net.InetAddress
import java.util.*
import kotlin.concurrent.thread
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy


class IpSweepper : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ip_sweepper)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val scanButton: Button = findViewById(R.id.scanStart)
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
        scanButton.setOnClickListener {
            log.setText("Start")
            var ip:String
            for (i in 0..256)
            {
                if (i == 0){
                    continue
                }
                ip = format.replace("FUZZ",i.toString())
                log.setText(ip)
                pinger(ip,1,recv)

            }
            log.setText("Done.")
        }
    }

    fun pinger(ip:String,timeout:Int,rec:LinearLayout)
    {
        try {
            val addr = InetAddress.getByName(ip)
            val rechbl = addr.isReachable(timeout)
            if (rechbl) {
                val info = addr.hostName
                val tv = TextView(this)
                tv.setText("$ip ($info)")
                rec.addView(tv)
                println("Found:$ip")
           }
            println(ip)
        }catch (ex:Exception){
            println("ERROR")
        }
    }
}