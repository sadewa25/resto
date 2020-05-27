package com.devpro.resto.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.devpro.resto.BuildConfig
import com.devpro.resto.R
import com.devpro.resto.data.source.remote.response.ResponseJSON
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.utils.SessionManager
import com.devpro.resto.utils.Utils
import com.devpro.resto.utils.common.Status
import com.devpro.resto.utils.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private val model by viewModel<LoginViewModel>()
    private val sessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLogin()
        setupClickListener()
    }

    private fun checkLogin() {
        if (sessionManager.getLogin() == true)
            navigateToDahsboard()
    }

    private fun setupClickListener() {
        btn_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> login()

        }
    }

    private fun login() {
        model.getUsers(
            ValuesItems(
                username = ed_login_username.text.toString(),
                password = ed_login_password.text.toString(),
                apikey = BuildConfig.API_Key
            )
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        login_loading.visibility = View.GONE
                        resource.data.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        login_loading.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        login_loading.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(
        it: ResponseJSON?
    ) {
        if (it?.error == false) {
            sessionManager.setIDUser(it.values?.get(0)?.idUsers.toString())
            sessionManager.setRoleUser(it.values?.get(0)?.statusUsers.toString())
            sessionManager.setLogin(true)
            navigateToDahsboard()
        } else
            Utils().toast(requireContext(), it?.message.toString())
    }

    private fun navigateToDahsboard() {
        val actions = LoginFragmentDirections.actionLoginFragmentToNavigationWaiters()
        findNavController().navigate(actions)
    }
}
