package corp.guia.app.alert.animals

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import corp.guia.app.alert.animals.common.GoogleSignInHelper
import corp.guia.app.alert.animals.databinding.ActivityMainBinding
import corp.guia.app.alert.animals.viewmodel.GoogleSignInViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private lateinit var googleSignInViewModel: GoogleSignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        googleSignInViewModel = ViewModelProvider(this)[GoogleSignInViewModel::class.java]

        GoogleSignInHelper.init(this)

        googleSignInClient = GoogleSignInHelper.getClient()

        binding.btnSignIn.setOnClickListener(this)
        binding.btnSignOut.setOnClickListener(this)

        setGoogleSignResultLaunchers()

    }

    private fun googleSignIn() {
        val intent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(intent)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.btnSignIn.id -> googleSignIn()
            binding.btnSignOut.id -> googleSignOut()
        }
    }

    private fun googleSignOut() {

        googleSignInViewModel.signOut {
            Log.d("GoogleSignIn", "Sesión cerrada")
        }

    }

    private fun setGoogleSignResultLaunchers() {
        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            } else {
                Log.w("GoogleSignIn", "Sign-in canceled or failed.")
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            // Éxito: puedes usar account.email, account.displayName, etc.
            Log.d("GoogleSignIn", "Email: ${account?.email}")
            Log.d("GoogleSignIn", "GivenName: ${account?.givenName}")
            Log.d("GoogleSignIn", "DisplayName: ${account?.displayName}")
            Log.d("GoogleSignIn", "FamilyName: ${account?.familyName}")

            startActivity(Intent(this, PruebaActivity::class.java))

        } catch (e: ApiException) {
            Log.w("GoogleSignIn", "signInResult:failed code=" + e.statusCode)
        }
    }

}