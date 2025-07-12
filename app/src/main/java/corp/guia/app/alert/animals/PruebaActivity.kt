package corp.guia.app.alert.animals

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import corp.guia.app.alert.animals.databinding.ActivityPruebaBinding
import corp.guia.app.alert.animals.viewmodel.GoogleSignInViewModel

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