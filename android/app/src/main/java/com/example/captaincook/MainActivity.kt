package com.example.captaincook

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.captaincook.navigation.RecipeNavigation
import com.example.captaincook.recipes.viewmodel.RecipesViewModel
import com.example.captaincook.ui.theme.CaptainCookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    protected lateinit var connectionLiveData: ConnectionLiveData
    val viewModel by viewModels<RecipesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)

        setContent {

            connectionLiveData.observe(this) {
                viewModel.isNetworkAvailable.value = it
                if(it == true){
                    this.viewModel.syncData()
                }
            }

            viewModel.isNetworkAvailable.value = isConnected
            viewModel.load()

            val isLoading by viewModel.isLoading.collectAsState()
            CaptainCookTheme {
                // A surface container using the 'background' color from the theme
                when {
                    isLoading -> LoadingUi()
                    else -> Surface(color = MaterialTheme.colors.background) {
                        CaptainCookApp(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CaptainCookApp(viewModel: RecipesViewModel) {
    RecipeNavigation(viewModel)

}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello boyzzzzz!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CaptainCookTheme {
        Greeting("Android")
    }
}


@Composable
fun LoadingUi() {
    Scaffold(
        content = { LoadingIndicator() },
    )
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
