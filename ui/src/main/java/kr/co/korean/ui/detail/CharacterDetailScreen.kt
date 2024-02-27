package kr.co.korean.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

fun NavHostController.navigateToCharacterDetailScreen() {
    navigate("characterDetail")
}

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier
) {

}