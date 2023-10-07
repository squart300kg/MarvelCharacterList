package kr.co.korean.investment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import kr.co.korean.investment.ui.theme.KoreanInvestmentTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoreanInvestmentTheme {

                @OptIn(ExperimentalMaterial3Api::class)
                Scaffold(
                    snackbarHost = {},
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = true,
                                onClick = {},
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_home_filled),
                                        contentDescription = null
                                    )
                                }
                            )
                            NavigationBarItem(
                                selected = true,
                                onClick = {},
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_bookmark_filled),
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Text(
                        modifier = Modifier.padding(innerPadding),
                        text = "sdf")
                }
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
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
    KoreanInvestmentTheme {
        Greeting("Android")
    }
}