package corp.guia.app.alert.animals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import corp.guia.app.alert.animals.common.GoogleSignInHelper
import corp.guia.app.alert.animals.databinding.ActivityPruebaBinding
import corp.guia.app.alert.animals.viewmodel.GoogleSignInViewModel
import java.security.Provider

class PruebaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPruebaBinding

    private lateinit var googleSignInViewModel: GoogleSignInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPruebaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        googleSignInViewModel = ViewModelProvider(this)[GoogleSignInViewModel::class.java]

        googleSignInViewModel.accountLiveData.observe(this) { account ->
            if (account != null) {
                Log.i("GoogleSignIn", "!Hola ${account.displayName}")
                binding.txvFullName.text = account.displayName
            } else {
                // Sesión expirada o usuario cerró sesión
                Log.i("GoogleSignIn", "fue rebocado de sus permisos")
            }
        }

    }

}