package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nz.benlawrence.splatoontracker.data.SplatoonDataState
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.ui.components.VsStageItem
import nz.benlawrence.splatoontracker.utils.getScheduleImage
import java.time.Instant

@Composable
fun HomeScreen(
    viewModel: SplatoonDataViewModel,
    modifier: Modifier
) {
    Column {
        when(val state = viewModel.dataState) {
            is SplatoonDataState.Success ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    val now = Instant.now()
                    val currentRegular = state.data.regularSchedules.nodes.first {
                        Instant.parse(it.startTime).isBefore(now) && Instant.parse(it.endTime)
                            .isAfter(now)
                    }
                    val currentBankara = state.data.bankaraSchedules.nodes.first {
                        Instant.parse(it.startTime).isBefore(now) && Instant.parse(it.endTime)
                            .isAfter(now)
                    }

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row {
                            Image(
                                painter = getScheduleImage(currentRegular.regularMatchSetting.__typename),
                                contentDescription = "Image of ${currentRegular.regularMatchSetting.vsRule.name}"
                            )

                            Text(
                                "Regular Battle",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp)
                            )
                        }
                        VsStageItem(
                            vsStages = currentRegular.regularMatchSetting.vsStages,
                            vsRule = currentRegular.regularMatchSetting.vsRule,
                            startTime = currentRegular.startTime,
                            endTime = currentRegular.endTime
                        )
                    }

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row {
                            Image(
                                painter = getScheduleImage(currentBankara.bankaraMatchSettings.first {
                                    it.bankaraMode == "CHALLENGE"
                                }.__typename),
                                contentDescription = "Image of ${currentBankara.bankaraMatchSettings.first {
                                    it.bankaraMode == "CHALLENGE"
                                }.vsRule.name}"
                            )

                            Text(
                                "Anarchy Battle Series",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp)
                            )
                        }


                        VsStageItem(
                            vsStages = currentBankara.bankaraMatchSettings.first {
                                it.bankaraMode == "CHALLENGE"
                            }.vsStages,
                            vsRule = currentBankara.bankaraMatchSettings.first {
                                it.bankaraMode == "CHALLENGE"
                            }.vsRule,
                            startTime = currentBankara.startTime,
                            endTime = currentBankara.endTime
                        )
                    }

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row {
                            Image(
                                painter = getScheduleImage(currentBankara.bankaraMatchSettings.first {
                                    it.bankaraMode == "OPEN"
                                }.__typename),
                                contentDescription = "Image of ${currentBankara.bankaraMatchSettings.first {
                                    it.bankaraMode == "OPEN"
                                }.vsRule.name}"
                            )

                            Text(
                                "Anarchy Battle Open",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp)
                            )
                        }

                        VsStageItem(
                            vsStages = currentBankara.bankaraMatchSettings.first {
                                it.bankaraMode == "OPEN"
                            }.vsStages,
                            vsRule = currentBankara.bankaraMatchSettings.first {
                                it.bankaraMode == "OPEN"
                            }.vsRule,
                            startTime = currentBankara.startTime,
                            endTime = currentBankara.endTime
                        )
                    }
                }

            is SplatoonDataState.Error ->
                Text(state.message)

            is SplatoonDataState.Loading ->
                Text("loading")
        }
    }
}