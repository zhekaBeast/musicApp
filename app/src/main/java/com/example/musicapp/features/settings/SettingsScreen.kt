package com.example.musicapp.features.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.musicapp.R

@Composable
fun SettingsScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val appName = stringResource(R.string.app_name)
    val shareTitle = stringResource(R.string.share_the_app)
    val title = stringResource(R.string.write_to_support)
    val user_agree = stringResource(R.string.user_agreement)

    Column(Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        Option(
            title = stringResource(id = R.string.share_the_app),
            icon = Icons.Default.Share,
            onClick = {shareApp(context)})
        Option(
            title = stringResource(id = R.string.write_to_support),
            icon = Icons.Default.Support,
            onClick = {sendEmailToSupport(context)})
        Option(
            title = stringResource(id = R.string.user_agreement),
            icon = Icons.Default.KeyboardArrowRight,
            onClick = {openUserAgreement(context)})
    }

}
private fun shareApp(context: android.content.Context) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_app_message))
    }
    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_the_app)))
}

private fun sendEmailToSupport(context: android.content.Context) {
    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, context.getString(R.string.support_email))
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject))
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.email_body))
    }
    context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.choose_email_client)))
}

private fun openUserAgreement(context: android.content.Context) {
    val agreementUrl = context.getString(R.string.user_agreement_url)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(agreementUrl))
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_browser)))
}

@Composable
fun Option(title: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
        ) {
            Text(text = title, modifier = Modifier.padding(start = 8.dp))
            Icon(imageVector = icon, contentDescription = title, tint = Color.Black)
        }
    }
}