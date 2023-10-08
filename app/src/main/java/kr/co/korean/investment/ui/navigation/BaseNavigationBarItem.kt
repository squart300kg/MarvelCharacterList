package kr.co.korean.investment.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun RowScope.BaseNavigationBarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selected: Boolean,
    destination: BaseDestination
) {
    NavigationBarItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = if (selected) {
                    destination.selectedIconRes
                } else {
                    destination.unselectedIconRes
                }),
                contentDescription = null
            )
        },
        label = { Text(stringResource(id = destination.iconTextIdRes)) }
    )
}