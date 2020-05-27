package com.devpro.resto.ui.splashscreen


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devpro.resto.R
import com.devpro.resto.utils.Utils
import com.devpro.resto.utils.findNavController
import com.novoda.merlin.*

class SplashScreenFragment : Fragment(), Connectable, Disconnectable, Bindable {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private val merlin: Merlin by lazy {
        Merlin.Builder().withConnectableCallbacks().withDisconnectableCallbacks()
            .withBindableCallbacks().build(context)
    }

    override fun onResume() {
        super.onResume()
        merlin.bind()
        merlin.bind()
        merlin.registerConnectable(this)
        merlin.registerBindable(this)
        merlin.registerDisconnectable(this)
    }

    override fun onPause() {
        merlin.unbind()
        super.onPause()
    }

    private fun splashScreenRun() {
        Handler().postDelayed({
            navigateToLogin()
        }, 1000)
    }

    private fun navigateToLogin() {
        val actions = SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment()
        findNavController().navigate(actions)
    }

    override fun onConnect() {}
    override fun onDisconnect() {}

    override fun onBind(networkStatus: NetworkStatus?) {
        if (networkStatus != null) {
            if (networkStatus.isAvailable) {
                splashScreenRun()
            } else {
                Utils().toast(context!!, getString(R.string.network_unavailable))
                activity?.finish()
            }
        }
    }

}
