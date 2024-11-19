package com.example.di_mywhatsapp
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class Contacto(
    var nombre: String,
    @DrawableRes var photo: Int,
    var grupo: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    var expanded by remember { mutableStateOf(false) }


    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            Text(
                "MyWhatsApp",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },

        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search"
                )
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "share"
                )
            }
        },
    scrollBehavior = scrollBehavior,
    )
}

@Composable
fun MyFAB(){

    FloatingActionButton(
        modifier = Modifier
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
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun MainScreen(modifier: Modifier){
    val tabs = listOf("Chats", "Novedades", "Llamadas")
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabs.size }
    )
    val coroutineScope = rememberCoroutineScope()
    Column (modifier = modifier.fillMaxSize()){
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        } },
                    text = { Text(
                        text = title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp
                    )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when(page){
                0-> Chats()
                1-> Novedades()
                2-> Llamadas()
            }

        }


    }
}

fun getContactos(): List<Contacto> {
    return listOf(
        Contacto("Jefe de departamento", R.drawable.usuario1,"Departamento Informática"),
        Contacto("Tutora DAM", R.drawable.usuario2,"Departamento Informática"),
        Contacto("Tutor DAW",  R.drawable.usuario1,"Departamento Informática"),
        Contacto("Tutora ASIX",  R.drawable.usuario2,"Departamento Informática"),
        Contacto("Presidenta", R.drawable.usuario2,"Comunidad Propietarios"),
        Contacto("Secritaria", R.drawable.usuario2,"Comunidad Propietarios"),
        Contacto("Administrador",  R.drawable.usuario1,"Comunidad Propietarios"),
        Contacto("Entrenadora",  R.drawable.usuario2,"Gimnasio"),
        Contacto("Nutricionista",  R.drawable.usuario2,"Gimnasio"),
        Contacto("Fisioterapueta",  R.drawable.usuario2,"Gimnasio"),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Chats(){
    val groupVisibility = remember { mutableStateMapOf<String, Boolean>() }
val contactos: Map<String,List<Contacto>> = getContactos().groupBy { it.grupo }
    LazyColumn (verticalArrangement = Arrangement.spacedBy(8.dp)){
        contactos.forEach{ (grupo, myContact)->
            stickyHeader {
                Row (modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .padding(8.dp)
                    .clickable {
                        groupVisibility[grupo]=!(groupVisibility[grupo]?: true)
                    },
                    verticalAlignment = Alignment.CenterVertically
                    ){
                    Text(
                        text = grupo,
                        modifier = Modifier
                            .background(Color.LightGray),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

            }
            if (groupVisibility[grupo] != false){
                items(myContact){ miContacto-> ItemContact(contacto = miContacto)}
            }
        }

    }
}

@Composable
fun ItemContact(contacto: Contacto ){
    var expanded by remember { mutableStateOf(false) }
    val opciones = listOf("salir del grupo", "Info. grupo", "Crear acceso directo")
    Box() {

        Row(
            modifier = Modifier
                .pointerInput(true) {
                    detectTapGestures(onLongPress = {
                        expanded = true
                    }
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(contacto.photo),
                contentDescription = "foto de contacto${contacto.nombre}",
                modifier = Modifier.clip(RoundedCornerShape(50.dp)).size(80.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize().padding(10.dp),
            ) {
                Text(
                    text = contacto.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

            }

        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(20.dp, 0.dp)

        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(text = opcion) },
                    onClick = {
                        expanded = false
                    }
                )
            }
        }
    }
    HorizontalDivider()

}

@Composable
fun Novedades(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            textAlign = TextAlign.Center,
            text = "Novedades",
            style = MaterialTheme.typography.bodyLarge
        )
    }

}

@Composable
fun Llamadas(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Llamadas",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
