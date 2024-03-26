package kr.co.korean.ui.detail

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<Nothing>()


}