package com.example.praktikumpertama.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.praktikumpertama.R
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)

val pages = listOf(
        OnboardingPage("Selamat Datang", "Jelajahi data terbuka Jawa Barat!", R.drawable.pageone),
        OnboardingPage("Cari Data", "Temukan informasi provinsi favoritmu!", R.drawable.pagetwo),
        OnboardingPage("Profil", "Sesuaikan pengalamanmu di sini!", R.drawable.pagethree)
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(pages[page].imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(Modifier.height(24.dp))
                Text(pages[page].title, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(8.dp))
                Text(pages[page].description, style = MaterialTheme.typography.bodyMedium)
            }
        }
        // Removed HorizontalPagerIndicator

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage > 0) {
                OutlinedButton(onClick = { scope.launch { pagerState.scrollToPage(pagerState.currentPage - 1) } }) {
                    Text("Back")
                }
            } else {
                Spacer(Modifier.width(8.dp))
            }
            Button(onClick = {
                if (pagerState.currentPage == pages.size - 1) onFinish()
                else scope.launch { pagerState.scrollToPage(pagerState.currentPage + 1) }
            }) {
                Text(if (pagerState.currentPage == pages.size - 1) "Get Started" else "Next")
            }
        }
    }
}
