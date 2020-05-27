package com.devpro.resto.ui.dashboard_waiters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.devpro.resto.BuildConfig
import com.devpro.resto.R
import com.devpro.resto.data.source.remote.response.ResponseJSON
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.databinding.FragmentDashboardWaitersBinding
import com.devpro.resto.utils.Utils
import com.devpro.resto.utils.common.EventObserver
import com.devpro.resto.utils.common.Status
import com.devpro.resto.utils.findNavController
import kotlinx.android.synthetic.main.fragment_dashboard_waiters.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardWaitersFragment : Fragment() {

    private val model: DashboardWaitersViewModel by viewModel()
    private val _dashboardWaitersAdapter by lazy {
        DashboardWaitersAdapter(model)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentDashboardWaitersBinding>(
                inflater,
                R.layout.fragment_dashboard_waiters,
                container,
                false
            )
        binding.lifecycleOwner = this
        binding.model = model
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRvAdapter()
        setupCategory()
        setupObservers()
    }

    private fun setupObservers() {
        model.onItemClick.observe(viewLifecycleOwner, EventObserver {
            navigateToMenus(it)
        })
    }

    private fun navigateToMenus(it: ValuesItems) {
        val actions =
            DashboardWaitersFragmentDirections.actionDashboardWaitersFragmentToMenusFragment(it.idKategoriMenu.toString(),it.nameKategoriMenu.toString())
        findNavController().navigate(actions)
    }

    private fun setupRvAdapter() {
        rv_dashboard_waiters.adapter = _dashboardWaitersAdapter
    }

    private fun setupCategory() {
        model.getCategory(
            ValuesItems(
                apikey = BuildConfig.API_Key
            )
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading_dashboard_waiters.visibility = View.GONE
                        resource.data.let { data -> retrieveList(data) }
                    }
                    Status.ERROR -> {
                        loading_dashboard_waiters.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        loading_dashboard_waiters.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(data: ResponseJSON?) {
        if (data?.error == true)
            Utils().toast(requireContext(), data.message.toString())
        else {
            model.setDataCategory(data)
            _dashboardWaitersAdapter.notifyDataSetChanged()
        }
    }

}
