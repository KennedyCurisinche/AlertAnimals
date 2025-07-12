package corp.guia.app.alert.animals.common

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object GoogleSignInHelper {

    private var googleSignInClient: GoogleSignInClient? = null

    fun init(context: Context) {
        if (googleSignInClient == null) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                //.requestIdToken("TU_WEB_CLIENT_ID") // solo si usas Firebase/backend
                .build()
            googleSignInClient = GoogleSignIn.getClient(context.applicationContext, gso)
        }
    }

    fun getClient(): GoogleSignInClient {
        return googleSignInClient
            ?: throw IllegalStateException("GoogleSignInHelper no ha sido inicializado. Llama a init(context) primero.")
    }

    fun getLastSignedInAccount(context: Context): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    fun silentSignIn(context: Context, onComplete: (GoogleSignInAccount?) -> Unit) {
        init(context)
        googleSignInClient?.silentSignIn()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onComplete(task.result)
            } else {
                onComplete(null)
            }
        }
    }

    fun signOut(context: Context, onComplete: () -> Unit) {
        init(context)
        googleSignInClient?.signOut()?.addOnCompleteListener {
            onComplete()
        }
    }


}