package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nz.benlawrence.splatoontracker.data.SplatoonDataState
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items

@Composable
fun HomeScreen(
    viewModel: SplatoonDataViewModel,
    modifier: Modifier
) {
    Column {
        when(val state = viewModel.dataState) {
            is SplatoonDataState.Success ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    items(state.data.vsStages.nodes) { node ->
                        Text(node.name)
                    }
                }

            is SplatoonDataState.Error ->
                Text(state.message)

            is SplatoonDataState.Loading ->
                Text("loading")
        }
    }
}