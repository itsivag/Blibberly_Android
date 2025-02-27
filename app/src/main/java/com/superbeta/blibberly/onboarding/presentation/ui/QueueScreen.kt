package com.superbeta.blibberly.onBoarding.presentation.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.LinkTextColor
import com.superbeta.blibberly.ui.PrimaryLinearGradient
import com.superbeta.blibberly.ui.TextColorGrey
import com.superbeta.blibberly.ui.components.PrimaryButton
import com.superbeta.blibberly_home.utils.FontProvider
import kotlinx.coroutines.launch

@Composable
fun QueueScreen(modifier: Modifier, navigateToInitialLoadingScreen: () -> Unit) {
    var openAlertDialog by remember { mutableStateOf(false) }
    if (openAlertDialog) {
        WhyQueueInfoDialog(onDismissRequest = { openAlertDialog = false })
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    PrimaryLinearGradient
                )
            ),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Balancing the vibe!",
                fontFamily = FontProvider.poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White
            )

            Text(
                text = "#4",
                fontFamily = FontProvider.dmSansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 86.sp,
                modifier = Modifier
                    .padding(24.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.15f), shape = RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp),
                color = Color.White
            )

            Text(
                text = "You’re 4 in the lineup! ⏳\uD83D\uDC96",
                fontFamily = FontProvider.poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.White
            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Why am I in the line?",
                modifier = Modifier
                    .align(Alignment.End)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clickable {
                        openAlertDialog = !openAlertDialog
                    },
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.End,
                fontFamily = FontProvider.poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = LinkTextColor
            )

            Text(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()), text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("\uD83C\uDF89 Skip the Line & Join Instantly! \uD83D\uDE80\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                        append("You don’t have to wait! Invite a friend of the opposite gender, and both of you get instant access—no waiting, no delays!\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("\nHow It Works:\n")
                    }

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                        append("\n1.Share your invite link with a friend.\n2. They sign up & verify their account.\n3. Boom! Both of you jump the queue and start chatting instantly.\n")
                    }

                }, fontFamily = FontProvider.dmSansFontFamily, color = TextColorGrey
            )

            PrimaryButton(
                buttonText = "Invite & Skip the Line Now",
                modifier = Modifier.fillMaxWidth()
            ) {
                scope.launch {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "APP_LINk_TO_BE_ENTERED")
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            }
        }
    }
}

@Composable
fun WhyQueueInfoDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = TextColorGrey
            )
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = "\uD83C\uDF1F Why We Balance the Gender Ratio in Chatting?",
                    modifier = Modifier
                        .weight(1f),
                    fontFamily = FontProvider.poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TextColorGrey
                )

                IconButton(onClick = { onDismissRequest() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.close_circle),
                        contentDescription = "Close Dialog"
                    )
                }
            }
            //TODO format this text
            Text(
                text = "Unlike other platforms that turn into a chaotic mess where one group gets overwhelmed with attention while the other gets ignored, we’re here to keep things fair, fun, and engaging for everyone. \uD83D\uDCAC✨\n" +
                        "\uD83D\uDCA5 The Problem with Unbalanced Apps\n" +
                        "Most chat platforms don’t manage their gender ratio, leading to:\n" +
                        "One group (usually females) getting flooded with messages and attention. \uD83D\uDCE9\uD83D\uDCE9\uD83D\uDCE9\n" +
                        "The other group (usually males) struggling to get any interactions. \uD83D\uDEAB\uD83D\uDC94\n" +
                        "Frustration on both sides – some users leave due to spam, while others leave due to lack of engagement. \uD83D\uDE35\u200D\uD83D\uDCAB\n" +
                        "Fake profiles, bots, and catfishing trying to fill the gaps. \uD83D\uDED1\uD83E\uDD16\n" +
                        "\n" +
                        "✅ How We Fix This with a Balanced System\n" +
                        "By maintaining an equal gender ratio in a geo-fenced area, we create:\n" +
                        "1\uFE0F⃣ Fair and Engaging Conversations\n" +
                        "\uD83D\uDD39 No one is ignored or overwhelmed—everyone gets a fair shot at meaningful chats.\u2028\uD83D\uDD39 It encourages genuine connections instead of random attention-seeking behavior.\n" +
                        "2\uFE0F⃣ Less Spam, More Quality Interactions\n" +
                        "\uD83D\uDD39 Users won’t feel like they’re just another message in a sea of unread texts.\u2028\uD83D\uDD39 No one gets bombarded with hundreds of desperate DMs, making chats more natural.\n" +
                        "3\uFE0F⃣ Avoids Fake Profiles & Catfishing\n" +
                        "\uD83D\uDD39 Other apps let one side dominate, leading to fake accounts trying to fill the void.\u2028\uD83D\uDD39 With balanced ratios, there’s no need for fakes or bots to artificially boost engagement.\n" +
                        "4\uFE0F⃣ A More Enjoyable & Long-Term Experience\n" +
                        "\uD83D\uDD39 A good balance keeps users engaged and coming back instead of leaving out of frustration.\u2028\uD83D\uDD39 No weird power dynamics—just chill, fun, and organic convos!\n" +
                        "\uD83D\uDE80 Want to Skip the Queue? Here’s How!\n" +
                        "Since we maintain a fair ratio, sometimes you may need to wait for the right balance in your area. But hey, there’s a shortcut! \uD83C\uDF9F\uFE0F\n" +
                        "\uD83D\uDCA1 Invite a friend of the opposite gender, and you BOTH get:\u2028✅ Instant access—no waiting!\u2028✅ Better matches and convos!\u2028✅ Extra perks & surprises!\n",
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                fontFamily = FontProvider.poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = TextColorGrey
            )

        }
    }
}