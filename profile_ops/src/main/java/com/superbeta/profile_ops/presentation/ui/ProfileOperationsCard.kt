package com.superbeta.profile_ops.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_utils.FontProvider
import com.superbeta.profile_ops.R
import com.superbeta.profile_ops.report.presentation.viewModel.ReportViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileOperationsCard(
    modifier: Modifier,
    showReportProfileBottomSheet: () -> Unit,
    viewModel: ReportViewModel = koinViewModel()
) {
    Column(modifier = modifier) {
        Row(Modifier.padding(8.dp)) {
            ProfileOperationItem(content = "About", icon = R.drawable.about, modifier =
            Modifier.Companion
                .weight(1f)
                .padding(8.dp), onCLick = {})
            ProfileOperationItem(
                content = "Ghost", icon = R.drawable.ghost, modifier =
                Modifier.Companion
                    .weight(1f)
                    .padding(8.dp), onCLick = {}
            )
        }

        Row(Modifier.padding(horizontal = 8.dp)) {
            ProfileOperationItem(
                content = "Block", icon = R.drawable.block, modifier =
                Modifier.Companion
                    .weight(1f)
                    .padding(8.dp), onCLick = {}
            )

            ProfileOperationItem(content = "Report",
                icon = R.drawable.report,
                modifier =
                Modifier.Companion
                    .weight(1f)
                    .padding(8.dp),
                onCLick = {
                    showReportProfileBottomSheet()
                })
        }

    }
}

@Composable
private fun ProfileOperationItem(
    content: String,
    icon: Int,
    modifier: Modifier,
    onCLick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { onCLick() },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.3f))
    ) {
        Text(
            text = content,
            fontFamily = FontProvider.dmSansFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp)
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = content
        )
    }
}