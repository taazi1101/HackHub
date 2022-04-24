package com.example.hackhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.content.res.AssetManager
import android.os.DropBoxManager
import android.widget.EditText
import java.io.BufferedInputStream
import java.io.InputStream
import java.lang.Exception
import java.lang.reflect.Executable
import java.net.HttpURLConnection
import java.net.URL
import java.security.KeyStore


class FileBruteforcer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_bruteforcer)
        val scanButton: Button = findViewById(R.id.scanStart)
        val log: TextView = findViewById(R.id.log)
        val recv: LinearLayout = findViewById(R.id.recv)
        val urlInput : EditText = findViewById(R.id.urlInput)

        scanButton.setOnClickListener {
            var url = urlInput.text.toString()
            if (!url.endsWith("/")){
                url += "/"
            }

            val files = assets.list("")
            if (files == null) {
                urlInput.setText("WORDLISTERROR")
            }
            val reader = assets.open("filewordlist.txt").bufferedReader()
            try {
                var line = reader.readLine()
                while (line != null) {
                    try {
                        log.setText(line.toString())
                        var respCode = ""
                        with(URL(url + line.toString()).openConnection() as HttpURLConnection){
                            respCode = responseCode.toString()
                        }
                        if (respCode != "404"){
                            val tv = TextView(this)
                            tv.setText("Found:${line.toString()} ($respCode)")
                            recv.addView(tv)
                        }
                    }catch(ex : Exception){
                        println("ERROR")
                    }
                    line = reader.readLine()
                }
            } finally {
                reader.close()
            }
        }
    }
}