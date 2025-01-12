package com.linh.features.users.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.linh.core.ui.theme.spacing
import demoproject.features.users.generated.resources.Res
import demoproject.features.users.generated.resources.user_card_avatar_content_description
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserCard(
    avatarUrl: String,
    title: String,
    content: @Composable () -> Unit,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(Modifier.fillMaxWidth().padding(MaterialTheme.spacing.small)) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = stringResource(Res.string.user_card_avatar_content_description, title),
                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(12.dp))
            )
            Spacer(Modifier.width(MaterialTheme.spacing.small))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                HorizontalDivider(Modifier.padding(vertical = MaterialTheme.spacing.small))
                content()
            }
        }
    }
}