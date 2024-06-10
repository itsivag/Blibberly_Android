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
import com.superbeta.blibberly_chat.data.Message
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import kotlinx.coroutines.launch

@Composable
fun MessageTextField(
    viewModel: MessageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = MessageViewModel.Factory
    )
) {


    var message by remember {
        mutableStateOf(TextFieldValue())
    }

    val data = Message(
        messageId = "1",
        content = message.text,
        senderID = "1",
        receiverID = "2",
        timeStamp = "10:23 pm",
        isDelivered = false,
        isRead = false
    )

    val textFieldContainerColor = MaterialTheme.colorScheme.primaryContainer
    val scope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
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
                    viewModel.sendMessage(data)
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