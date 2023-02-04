package com.mandi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.mandi.ui.navigation.MandiAppNavGraph
import com.mandi.ui.navigation.NavigationActions
import com.mandi.ui.sellingcomplete.SellingCompleteViewModel
import com.mandi.ui.theme.MandiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MandiTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                /*Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }*/
                MandiAppNavGraph()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
//    Text(text = "Hello $name!")
    TextButton(onClick = { }) {
        Text(text = "Hello $name click me")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MandiTheme {
        Greeting("Android")
    }
}