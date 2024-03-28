package kr.co.korean.investment.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun BaseNavigationRailBarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selected: Boolean,
    destination: BaseDestination
) {
    NavigationRailItem(
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
        label = { Text(stringResource(id = destination.iconTextIdRes)) })
}