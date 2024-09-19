package com.devmike.gitissuesmobile

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.devmike.datastore.repo.DataStoreRepo
import com.devmike.gitissuesmobile.ui.theme.GitIssuesMobileTheme
import com.devmike.repository.screen.RepositorySearchScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.ResponseTypeValues
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var service: AuthorizationService

    @Inject
    lateinit var repo: DataStoreRepo

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = AuthorizationService(this)
        enableEdgeToEdge()
        setContent {
            val token by repo.getUserToken().collectAsStateWithLifecycle(initialValue = null)
            GitIssuesMobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    if (token.isNullOrEmpty()) {
                        Box(modifier = Modifier.fillMaxSize().padding(it)) {
                            Button(onClick = { githubAuth() }) {
                                Text(text = "Hello auth the screen")
                            }
                        }
                    } else {
                        RepositorySearchScreen()
                    }
                }
            }

            LaunchedEffect(key1 = true) {
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val ex = AuthorizationException.fromIntent(it.data!!)
                val result = AuthorizationResponse.fromIntent(it.data!!)

                if (ex != null) {
                    Log.e("Github Auth", "launcher: $ex")
                } else {
                    val secret = ClientSecretBasic(Gitkeys.CLIENT_SECRET)
                    val tokenRequest = result?.createTokenExchangeRequest()

                    service.performTokenRequest(tokenRequest!!, secret) { res, exception ->
                        if (exception != null) {
                            Timber.tag("Github Auth").e("launcher: " + exception.error)
                        } else {
                            val token = res?.accessToken
                            Toast.makeText(this, "Token: $token", Toast.LENGTH_SHORT).show()

                            insertToken(token!!)
                        }
                    }
                }
            }
        }

    private fun insertToken(token: String) {
        lifecycleScope.launch {
            repo.insertToken(token)
        }
    }

    private fun githubAuth() {
        val redirectUri = Uri.parse("git-issues://dev.mike")
        val authorizeUri = Uri.parse("https://github.com/login/oauth/authorize")
        val tokenUri = Uri.parse("https://github.com/login/oauth/access_token")

        val config = AuthorizationServiceConfiguration(authorizeUri, tokenUri)
        val request =
            AuthorizationRequest
                .Builder(
                    config,
                    Gitkeys.CLIENT_ID,
                    ResponseTypeValues.CODE,
                    redirectUri,
                ).setScopes("user public_repo admin")
                .build()

        val intent = service.getAuthorizationRequestIntent(request)
        launcher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        service.dispose()
    }

    @Composable
    fun Greeting(
        name: String,
        modifier: Modifier = Modifier,
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier,
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        GitIssuesMobileTheme {
            Greeting("Android")
        }
    }
}

object Gitkeys {
    const val CLIENT_ID = "Ov23liwV4iXT8UA8TH6a"
    const val CLIENT_SECRET = "48538b3102ea545a168840bbf6b14d6fbee62fa7"
}
