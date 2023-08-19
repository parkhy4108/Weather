package com.dev_musashi.weather

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev_musashi.weather.data.remote.WeatherApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class Retrofit2Test {

    private lateinit var server: MockWebServer
    private lateinit var weatherApi: WeatherApi
    private lateinit var retrofit: Retrofit


    /*
    SetUP함수
    server는 mock 웹서버 인스턴스로 초기화합니다.
    server.start()
    start()함수는 주어진 port에 맞는 loopback interface 서버를 실행시킵니다.
    (loopback interface란 실제로는 존재하지 않는 인터페이스)
    retrofit을 만들어줍니다. url설정은 server의 url을 "/"로 간단하게 가상의 url을 설정합니다.

    api는 실제로 서비스 인터페이스로 만들어둔 api를 retrofit을 이용해 api의 엔드포인트의 구현체를 생성합니다.

     */
    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherApi = retrofit.create(WeatherApi::class.java)

    }

    @After
    fun tearDown(){
        server.shutdown()
    }

    /*
    test함수
    response는 테스트했을 때 받을 값입니다. """를 이용하여 json형태를 만들어줍니다.
     */
    @Test
    fun getCurrentWeatherTest() = runTest {
        val response = """
            {
              "response": {
                "header": {
                  "resultCode": 0,
                  "resultMsg": "성공"
                },
                "body": {
                  "dataType": "JSON",
                  "items": {
                    "item": [
                      {
                        "baseDate": "20230426",
                        "baseTime": "0500",
                        "category": "T1H",
                        "fcstDate": "20230426",
                        "fcstTime": "0600",
                        "fcstValue": "10",
                        "nx": 60,
                        "ny": 127
                      }
                    ]
                  }
                }
              }
            }
        """.trimIndent()

        server.enqueue(MockResponse().setBody(response))

        val weather = weatherApi.getCurrentWeather(
            baseDate = "20230426",
            baseTime = "0630",
            nx = 55,
            ny = 127
        )

        println(weather)
        assertNotNull(weather)
        assertEquals(weather.response.header.resultCode, 0)
        assertEquals("성공", weather.response.header.resultMsg)
        assertEquals("JSON", weather.response.body.dataType)
        assertNotNull(weather.response.body.items.item)

    }


}