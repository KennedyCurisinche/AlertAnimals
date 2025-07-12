package corp.guia.app.alert.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import corp.guia.app.alert.animals.common.GoogleSignInHelper

class GoogleSignInViewModel(application: Application) : AndroidViewModel(application) {

    private val _accountLiveData = MutableLiveData<GoogleSignInAccount?>()
    val accountLiveData: LiveData<GoogleSignInAccount?> = _accountLiveData

    private val context = application.applicationContext

    init {
        GoogleSignInHelper.init(context)
        checkExistingSession()
    }

    fun checkExistingSession() {
        val account = GoogleSignInHelper.getLastSignedInAccount(context)
        _accountLiveData.value = account
    }

    fun silentSignIn() {
        GoogleSignInHelper.silentSignIn(context) { account ->
            _accountLiveData.postValue(account)
        }
    }

    fun signOut(onComplete: () -> Unit) {
        GoogleSignInHelper.signOut(context) {
            _accountLiveData.postValue(null)
        }.let {
            onComplete()
        }
    }

}