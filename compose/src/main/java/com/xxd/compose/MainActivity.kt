package com.xxd.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xxd.compose.activitys.FirstActivity
import com.xxd.compose.activitys.SecondActivity
import com.xxd.compose.ui.basic.CustomFirstBaseline
import com.xxd.compose.ui.theme.MatrixAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatrixAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current

        CustomText(text = "First Activity") {
            context.startActivity(Intent(context, FirstActivity::class.java))
        }

        CustomText(text = "Second Activity") {
            context.startActivity(Intent(context, SecondActivity::class.java))
        }
    }
}

@Composable
private fun CustomText(text: String, block: () -> Unit) {
    Text(
        modifier = Modifier.custom(block),
        color = Color.Black,
        text = text,
    )
}

private fun Modifier.custom(block: () -> Unit) =
    this
        .padding(top = 10.dp, start = 10.dp)
        .clickable(onClick = block)
        .clip(RoundedCornerShape(CornerSize(50)))
        .background(Color.Cyan)
        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MatrixAndroidTheme {
        Greeting()
    }
}