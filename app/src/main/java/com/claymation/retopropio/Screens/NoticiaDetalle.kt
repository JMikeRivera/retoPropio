import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.claymation.retopropio.Screens.VideoPlayer
import com.claymation.retopropio.Viewmodels.ViewModel

@Composable
fun NoticiaDetalleScreen(noticiaId: Int, navController: NavController?) {
    val viewModel: ViewModel = viewModel()
    val noticia = viewModel.noticias.collectAsState().value.find { it.Noticia_id == noticiaId }

    if (noticia != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = noticia.Nombre,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (noticia.IsVideo) {
                VideoPlayer(videoUrl = noticia.Link, modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .padding(bottom = 8.dp))
            } else {
                Image(
                    painter = rememberAsyncImagePainter(noticia.Link),
                    contentDescription = noticia.Nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .padding(bottom = 8.dp)
                )
            }
            Text(
                text = noticia.Descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        Text(
            text = "Noticia no encontrada.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}