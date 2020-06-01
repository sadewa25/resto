package com.devpro.resto.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.devpro.resto.R
import com.devpro.resto.databinding.FragmentProfileBinding
import com.devpro.resto.utils.SessionManager
import com.devpro.resto.utils.Utils
import com.devpro.resto.utils.common.EventObserver
import com.devpro.resto.utils.findNavController
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val model by viewModel<ProfileViewModel>()
    private val sessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentProfileBinding>(
                inflater,
                R.layout.fragment_profile,
                container,
                false
            )
        binding.lifecycleOwner = this
        binding.model = model
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() {
        model.openSignout.observe(viewLifecycleOwner, EventObserver {
            Utils().formDialog(
                requireContext(),
                getString(R.string.inforrmation),
                getString(R.string.confirmation_signout)
            ) {
                sessionManager.setLogin(false)
                findNavController().popBackStack(R.id.dashboardWaitersFragment, true)
            }
        })
    }

}
