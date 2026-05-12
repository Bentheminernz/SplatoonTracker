package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.DataState
import nz.benlawrence.splatoontracker.data.models.coral.SessionRequest
import nz.benlawrence.splatoontracker.ui.components.SideOrderAttemptCard
import nz.benlawrence.splatoontracker.utils.openInBrowser

@Composable
fun Splatnet(
  viewModel: CoralDataViewModel,
  navController: NavController
) {
  var redirectUri by remember { mutableStateOf("") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
  ) {
    val context = LocalContext.current
    when (val authState = viewModel.dataState.authURL) {
      is DataState.Loading ->
        CircularProgressIndicator(
          color = MaterialTheme.colorScheme.secondary,
          trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

      is DataState.Error ->
        Text(authState.message)

      is DataState.Success ->
        Column {
          Button(onClick = {
            openInBrowser(context, authState.data.url)
          }) {
            Text("Open")
          }

          TextField(
            value = redirectUri,
            onValueChange = { redirectUri = it },
            label = { Text("Redirect URI") },
            modifier = Modifier.fillMaxWidth()
          )

          Button(onClick = {
            viewModel.createSession(
              SessionRequest(
                state = authState.data.state,
                verifier = authState.data.verifier,
                authoriseUrl = authState.data.url,
                redirectUri
              )
            )
          }) {
            Text("Create Session")
          }

          if (viewModel.dataState.sessionBlob != null) {
            Text("Session Blob: ${viewModel.dataState.sessionBlob}")
          }
        }
    }

    when (val sideOrderState = viewModel.dataState.sideOrderRecords) {
      is DataState.Loading ->
        CircularProgressIndicator(
          color = MaterialTheme.colorScheme.secondary,
          trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

      is DataState.Error ->
        Text(sideOrderState.message)

      is DataState.Success ->
        Column {
          Text("High Score!")

          val challengesWrapper = sideOrderState.data.challenges
          val challengeRecord = challengesWrapper?.sideOrderRecord

          // highestScoreTryResult may be absent depending on which fields the API returned
          if (challengeRecord?.highestScoreTryResult != null) {
            SideOrderAttemptCard(attempt = challengeRecord.highestScoreTryResult)
          } else {
            Text("No high score attempt data available")
          }

          // tryResults is a Connection — safely read its nodes if present
          challengeRecord?.tryResults?.nodes?.let { nodes ->
            LazyColumn {
              items(nodes) { node ->
                SideOrderAttemptCard(attempt = node)
              }
            }
          }
        }
    }
  }
}