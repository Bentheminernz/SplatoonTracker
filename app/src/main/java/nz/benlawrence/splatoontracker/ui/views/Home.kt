package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nz.benlawrence.splatoontracker.data.SplatoonDataState
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import androidx.compose.material3.Text
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import nz.benlawrence.splatoontracker.ui.components.VsStageItem
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily
import nz.benlawrence.splatoontracker.utils.getScheduleImage
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: SplatoonDataViewModel,
    modifier: Modifier
) {
    Column {
        when(val state = viewModel.dataState) {
            is SplatoonDataState.Success ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
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
                    val currentxSchedule = state.data.xSchedules.nodes.first {
                        Instant.parse(it.startTime).isBefore(now) && Instant.parse(it.endTime)
                            .isAfter(now)
                    }
                    var selectedMatch by remember { mutableStateOf<MatchType?>(null) }

                    Text(
                        "Schedules",
                        fontFamily = BlitzFontFamily,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Column(
                        modifier = Modifier
                            .rotate(-2f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = getScheduleImage(currentRegular.regularMatchSetting.__typename),
                                contentDescription = "Image of ${currentRegular.regularMatchSetting.vsRule.name}"
                            )

                            Text(
                                "Regular Battle",
                                fontFamily = BlitzFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
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

                        VsStageItem(
                            vsStages = state.data.regularSchedules.nodes.first {
                                it.startTime != currentRegular.startTime
                            }.regularMatchSetting.vsStages,
                            vsRule = state.data.regularSchedules.nodes.first {
                                it.startTime != currentRegular.startTime
                            }.regularMatchSetting.vsRule,
                            startTime = state.data.regularSchedules.nodes.first {
                                it.startTime != currentRegular.startTime
                            }.startTime,
                            endTime = state.data.regularSchedules.nodes.first {
                                it.startTime != currentRegular.startTime
                            }.endTime,
                            isNext = true
                        )

                        Button(
                            onClick = { selectedMatch = MatchType.Regular },
                        ) {
                            Text("View Full Schedule")
                        }
                    }

                    Column(
                        modifier = Modifier
                            .rotate(2f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                                fontFamily = BlitzFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
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

                        VsStageItem(
                            vsStages = state.data.bankaraSchedules.nodes.first {
                                it.startTime != currentBankara.startTime
                            }.bankaraMatchSettings.first {
                                it.bankaraMode == "CHALLENGE"
                            }.vsStages,
                            vsRule = state.data.bankaraSchedules.nodes.first {
                                it.startTime != currentBankara.startTime
                            }.bankaraMatchSettings.first {
                                it.bankaraMode == "CHALLENGE"
                            }.vsRule,
                            startTime = state.data.bankaraSchedules.nodes.first {
                                it.startTime != currentBankara.startTime
                            }.startTime,
                            endTime = state.data.bankaraSchedules.nodes.first {
                                it.startTime != currentBankara.startTime
                            }.endTime,
                            isNext = true
                        )

                        Button(
                            onClick = { selectedMatch = MatchType.BankaraChallenge },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("View Series Schedule")
                        }
                    }

                    Column(
                        modifier = Modifier
                            .rotate(-2f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                                fontSize = 20.sp,
                                fontFamily = BlitzFontFamily,
                                fontWeight = FontWeight.Bold,
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

                        VsStageItem(
                            vsStages = state.data.bankaraSchedules.nodes.first {
                                it.startTime != currentBankara.startTime
                            }.bankaraMatchSettings.first {
                                it.bankaraMode == "OPEN"
                            }.vsStages,
                            vsRule = state.data.bankaraSchedules.nodes.first {
                                it.startTime != currentBankara.startTime
                            }.bankaraMatchSettings.first {
                                it.bankaraMode == "OPEN"
                            }.vsRule,
                            startTime = state.data.bankaraSchedules.nodes.first {
                                it.startTime != currentBankara.startTime
                            }.startTime,
                            endTime = state.data.bankaraSchedules.nodes.first {
                                it.startTime != currentBankara.startTime
                            }.endTime,
                            isNext = true
                        )

                        Button(
                            onClick = { selectedMatch = MatchType.BankaraOpen },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("View Series Schedule")
                        }
                    }

                    Column(
                        modifier = Modifier
                            .rotate(2f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = getScheduleImage(currentxSchedule.xMatchSetting.__typename),
                                contentDescription = "Image of ${currentxSchedule.xMatchSetting.vsRule.name}"
                            )

                            Text(
                                "X Battle",
                                fontSize = 20.sp,
                                fontFamily = BlitzFontFamily,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp)
                            )
                        }

                        VsStageItem(
                            vsStages = currentxSchedule.xMatchSetting.vsStages,
                            vsRule = currentxSchedule.xMatchSetting.vsRule,
                            startTime = currentxSchedule.startTime,
                            endTime = currentxSchedule.endTime
                        )

                        VsStageItem(
                            vsStages = state.data.xSchedules.nodes.first {
                                it.startTime != currentxSchedule.startTime
                            }.xMatchSetting.vsStages,
                            vsRule = state.data.xSchedules.nodes.first {
                                it.startTime != currentxSchedule.startTime
                            }.xMatchSetting.vsRule,
                            startTime = state.data.xSchedules.nodes.first {
                                it.startTime != currentxSchedule.startTime                            }.startTime,
                            endTime = state.data.xSchedules.nodes.first {
                                it.startTime != currentxSchedule.startTime                            }.endTime,
                            isNext = true
                        )

                        Button(
                            onClick = { selectedMatch = MatchType.XBattle },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("View Full Schedule")
                        }
                    }

                    selectedMatch?.let { type ->
                        ModalBottomSheet(onDismissRequest = { selectedMatch = null }) {
                            UpcomingBattleSheet(
                                data = state.data,
                                type = type
                            )
                        }
                    }
                }

            is SplatoonDataState.Error ->
                Text(state.message)

            is SplatoonDataState.Loading ->
                Text("loading")
        }
    }
}

sealed class MatchType {
    data object Regular: MatchType()
    data object BankaraChallenge: MatchType()
    data object BankaraOpen: MatchType()
    data object XBattle: MatchType()
}