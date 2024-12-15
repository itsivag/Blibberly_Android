package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly_chat.R
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.androidx.compose.koinViewModel

@Composable
fun MessageTextField(
    receiverEmail: String,
    currUserEmail: String,
    viewModel: MessageViewModel = koinViewModel(),
) {
    var message by remember {
        mutableStateOf(TextFieldValue())
    }

    val currentTimeStamp = Clock.System.now()
    val messageId = currentTimeStamp.toString() + currUserEmail
    val data = MessageDataModel(
        messageId = messageId,
        content = message.text,
        senderEmail = currUserEmail,
        receiverEmail = receiverEmail,
        timeStamp = currentTimeStamp.toString(),
        isDelivered = false,
        isRead = false
    )

    val textFieldContainerColor = MaterialTheme.colorScheme.primaryContainer
    val scope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {

        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = { value -> message = value },
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = textFieldContainerColor,
                errorBorderColor = textFieldContainerColor,
                focusedBorderColor = textFieldContainerColor,
                disabledBorderColor = textFieldContainerColor,
                focusedContainerColor = textFieldContainerColor,
                disabledContainerColor = textFieldContainerColor,
                errorContainerColor = textFieldContainerColor,
                unfocusedContainerColor = textFieldContainerColor
            )
        )
        IconButton(
            onClick = {
                scope.launch {
                    viewModel.sendMessage(
                        data = data
                    )
                    message = TextFieldValue("")
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            modifier = Modifier
                .size(48.dp)
                .padding(start = 4.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.chat),
                contentDescription = "Send Message",
            )
        }
    }

}