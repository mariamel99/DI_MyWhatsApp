package com.example.di_mywhatsapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.di_mywhatsapp.ui.theme.DI_MyWhatsAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DI_MyWhatsAppTheme {
                val scrollBehavior= TopAppBarDefaults.enterAlwaysScrollBehavior(
                    rememberTopAppBarState()
                )
                Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).fillMaxSize(),
                    topBar = {MyTopBar(scrollBehavior)}) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding))

                    Box(modifier = Modifier.fillMaxSize()){
                        FloatingActionButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 40.dp, end = 20.dp),
                            containerColor = MaterialTheme.colorScheme.primary,
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "ArrowBack"
                            )
                        }
                    }
                }
            }
        }
    }
}

