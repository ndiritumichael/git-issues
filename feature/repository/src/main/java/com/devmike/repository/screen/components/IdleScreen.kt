package com.devmike.repository.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.devmike.repository.R

@Composable
fun IdleScreen(modifier: Modifier = Modifier) {
    val idleComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.search))

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimation(
            idleComposition,
            modifier = Modifier.size(300.dp).padding(top = 100.dp, bottom = 20.dp),
            iterations = 20,
        )

        Text("Type at least 3 characters to search", fontWeight = FontWeight.Bold)
    }
}

@Composable
@Preview
fun IdleScreenPreview() {
    IdleScreen()
}
