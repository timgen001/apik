package com.example.apik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.apik.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var pass2 = ""
    private var pass = ""
    private var phone = ""
    private lateinit var tonk:String
    private var name = ""
    private var member = ""
    private var userName = ""
    private var user_id = ""
    private var phoneNumber = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener{
            pass = binding.password.text.toString()
            pass2 = binding.password2.text.toString()
            phone = binding.phone.text.toString()
            name = binding.name.text.toString()
            //signup()
            login()
        }
        binding.button.setOnClickListener{
            Log.e("tonk",tonk)
        }


    }


    private fun signup() {
        val json = """
        {
            "phoneNumber": "753",
            "userName": "753",
            "password": "753"
        }
    """.trimIndent()
        //"checkPassword": """+pass2+"""
        // 定义 JSON 格式的媒体类型
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
        // 创建请求体
        val body = json.toRequestBody(JSON_MEDIA_TYPE)
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间为 10 秒
            .readTimeout(10, TimeUnit.SECONDS) // 读取超时时间为 10 秒
            .writeTimeout(10, TimeUnit.SECONDS) // 写入超时时间为 10 秒
            .build()
        val request = Request.Builder()
            .url("http:/10.0.2.2:3000/signup")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseString = response.body?.string()
                response.body?.close() // 关闭响应体
                if (responseString != null) {
                    Log.e("test", responseString)
                }
                /*val jsonResponse = JSONObject(response.body?.string())
                userName = jsonResponse.getString("message")
                if (jsonResponse != null) {
                    Log.e("test", userName)
                }

                 */
            }
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
        // 释放线程池
        client.dispatcher.executorService.shutdown()
    }
   private fun login() {

       val json = """
        {
            "phoneNumber": "123",
            "password": "123"
        }
    """.trimIndent()
       // 定义 JSON 格式的媒体类型
       val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
       // 创建请求体
       val requestBody = json.toRequestBody(JSON_MEDIA_TYPE)
       val client = OkHttpClient.Builder()
           .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间为 10 秒
           .readTimeout(10, TimeUnit.SECONDS) // 读取超时时间为 10 秒
           .writeTimeout(10, TimeUnit.SECONDS) // 写入超时时间为 10 秒
           .build()

       val request = Request.Builder()
           .url("http:/10.0.2.2:3000/login")
           .post(requestBody)
           .build()

       client.newCall(request).enqueue(object : Callback {
           override fun onResponse(call: Call, response: Response) {
               //val responseString = response.body?.string()
              val jsonResponse = JSONObject(response.body?.string())

               //userName = jsonResponse.getString("userName")
               //user_id = jsonResponse.getString("user_id")
               //phoneNumber = jsonResponse.getString("phoneNumber")
               tonk = jsonResponse.getJSONObject("response").get("token").toString()
                if (jsonResponse != null) {
                    Log.e("login",tonk )
                }
           }

           override fun onFailure(call: Call, e: IOException) {
               e.printStackTrace()
           }
       })
       // 释放线程池
       client.dispatcher.executorService.shutdown()
   }

    private fun senser() {
        val json = """
        {
            "sensorData": """+phone+""",
            "user_id": """+phone+"""
        }
    """.trimIndent()
        // 定义 JSON 格式的媒体类型
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
        // 创建请求体
        val body = json.toRequestBody(JSON_MEDIA_TYPE)
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间为 10 秒
            .readTimeout(10, TimeUnit.SECONDS) // 读取超时时间为 10 秒
            .writeTimeout(10, TimeUnit.SECONDS) // 写入超时时间为 10 秒
            .build()
        val request = Request.Builder()
            .url("http:/10.0.2.2:3000/sensorData")
            .addHeader("Authorization","Bearer " + tonk)
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonResponse = JSONObject(response.body?.string())
                val userDetail = jsonResponse.getString("userDetail")
            }
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
        // 释放线程池
        client.dispatcher.executorService.shutdown()
    }
    private fun getsenser() {
        val json = """
        {
            "user_id": """+phone+"""
        }
    """.trimIndent()
        // 定义 JSON 格式的媒体类型
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
        // 创建请求体
        val body = json.toRequestBody(JSON_MEDIA_TYPE)
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间为 10 秒
            .readTimeout(10, TimeUnit.SECONDS) // 读取超时时间为 10 秒
            .writeTimeout(10, TimeUnit.SECONDS) // 写入超时时间为 10 秒
            .build()
        val request = Request.Builder()
            .url("http:/10.0.2.2:3000/sensorData/show")
            .addHeader("Authorization","Bearer " + tonk)
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonResponse = JSONObject(response.body?.string())
                userName = jsonResponse.getString("userName")
                user_id = jsonResponse.getString("user_id")
                phoneNumber = jsonResponse.getString("phoneNumber")
            }
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
        // 释放线程池
        client.dispatcher.executorService.shutdown()
    }
    private fun userInfo() {
        val json = """
        {
            "phoneNumber": """+phone+""",
            "userName": """+phone+""",
            "password": """+pass+""",
            "checkPassword": """+pass+"""
        }
    """.trimIndent()
        // 定义 JSON 格式的媒体类型
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
        // 创建请求体
        val body = json.toRequestBody(JSON_MEDIA_TYPE)
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间为 10 秒
            .readTimeout(10, TimeUnit.SECONDS) // 读取超时时间为 10 秒
            .writeTimeout(10, TimeUnit.SECONDS) // 写入超时时间为 10 秒
            .build()
        val request = Request.Builder()
            .url("localhost:3000/userInfo")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonResponse = JSONObject(response.body?.string())
                val userDetail = jsonResponse.getString("userDetail")
            }
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
        // 释放线程池
        client.dispatcher.executorService.shutdown()
    }
    private fun membersRequest() {
        val json = """
        {
            "phoneNumber": """+phone+""",
            "userName": """+phone+""",
            "password": """+pass+""",
            "checkPassword": """+pass+"""
        }
    """.trimIndent()
        // 定义 JSON 格式的媒体类型
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
        // 创建请求体
        val body = json.toRequestBody(JSON_MEDIA_TYPE)
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间为 10 秒
            .readTimeout(10, TimeUnit.SECONDS) // 读取超时时间为 10 秒
            .writeTimeout(10, TimeUnit.SECONDS) // 写入超时时间为 10 秒
            .build()
        val request = Request.Builder()
            .url("localhost:3000/membersRequest")
            .addHeader("Authorization","Bearer <token>")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonResponse = JSONObject(response.body?.string())
                val userDetail = jsonResponse.getString("userDetail")
            }
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
        // 释放线程池
        client.dispatcher.executorService.shutdown()
    }
    private fun user2Info() {
        val json = """
        {
            "phoneNumber": """+phone+""",
            "userName": """+phone+""",
            "password": """+pass+""",
            "checkPassword": """+pass+"""
        }
    """.trimIndent()
        // 定义 JSON 格式的媒体类型
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
        // 创建请求体
        val body = json.toRequestBody(JSON_MEDIA_TYPE)
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间为 10 秒
            .readTimeout(10, TimeUnit.SECONDS) // 读取超时时间为 10 秒
            .writeTimeout(10, TimeUnit.SECONDS) // 写入超时时间为 10 秒
            .build()
        val request = Request.Builder()
            .url("localhost:3000/userInfo")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonResponse = JSONObject(response.body?.string())
                val userDetail = jsonResponse.getString("userDetail")
            }
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
        // 释放线程池
        client.dispatcher.executorService.shutdown()
    }
    private fun membersResponse() {
        val json = """
        {
            "phoneNumber": """+phone+""",
            "userName": """+phone+""",
            "password": """+pass+""",
            "checkPassword": """+pass+"""
        }
    """.trimIndent()
        // 定义 JSON 格式的媒体类型
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
        // 创建请求体
        val body = json.toRequestBody(JSON_MEDIA_TYPE)
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间为 10 秒
            .readTimeout(10, TimeUnit.SECONDS) // 读取超时时间为 10 秒
            .writeTimeout(10, TimeUnit.SECONDS) // 写入超时时间为 10 秒
            .build()
        val request = Request.Builder()
            .url("localhost:3000/membersResponse/accept")
            .addHeader("Authorization","Bearer <token>")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonResponse = JSONObject(response.body?.string())
                val userDetail = jsonResponse.getString("userDetail")
            }
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
        // 释放线程池
        client.dispatcher.executorService.shutdown()
    }
    private fun deny() {
        val json = """
        {
            "phoneNumber": """+phone+""",
            "userName": """+phone+""",
            "password": """+pass+""",
            "checkPassword": """+pass+"""
        }
    """.trimIndent()
        // 定义 JSON 格式的媒体类型
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
        // 创建请求体
        val body = json.toRequestBody(JSON_MEDIA_TYPE)
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间为 10 秒
            .readTimeout(10, TimeUnit.SECONDS) // 读取超时时间为 10 秒
            .writeTimeout(10, TimeUnit.SECONDS) // 写入超时时间为 10 秒
            .build()
        val request = Request.Builder()
            .url("localhost:3000/membersResponse/deny")
            .addHeader("Authorization","Bearer <token>")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonResponse = JSONObject(response.body?.string())
                val userDetail = jsonResponse.getString("userDetail")
            }
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
        // 释放线程池
        client.dispatcher.executorService.shutdown()
    }
}

