package com.example.week4

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.week4.ui.theme.Week4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        val a = A()
        val b = B()

        a.start()
        a.join()
        b.start()
    }


    class A : Thread(){//스레드만듬
        override fun run() {
            super.run()//스레드를 시작함과 동시에 실행
            for( i in 1..1000){
                Log.d("test", "first : $i")
            }
        }
    }

    class B : Thread(){//스레드만듬
    override fun run() {
        super.run()//스레드를 시작함과 동시에 실행
        for( i in 1000 downTo 1){
            Log.d("test", "second : $i")
        }
    }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Week4Theme {
        Greeting("Android")
    }
}