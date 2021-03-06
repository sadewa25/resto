package com.devpro.resto.ui.tables

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
import com.devpro.resto.databinding.FragmentTablesBinding
import com.devpro.resto.utils.SessionManager
import com.devpro.resto.utils.Utils
import com.devpro.resto.utils.common.EventObserver
import com.devpro.resto.utils.common.Status
import com.devpro.resto.utils.findNavController
import kotlinx.android.synthetic.main.fragment_tables.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TablesFragment : Fragment() {

    private val model: TablesViewModel by viewModel()
    private val _TableAdapter by lazy {
        TablesAdapter(model)
    }
    private val sessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentTablesBinding>(
                inflater,
                R.layout.fragment_tables,
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
        setupAdapter()
        setupLoadEntireTable()
    }

    private fun setupObservers() {
        model.onItemClick.observe(viewLifecycleOwner, EventObserver {
            val value = it
            Utils().formDialog(
                requireContext(),
                getString(R.string.inforrmation),
                getString(R.string.information_table_update)
            ) {
                if (value.statusMeja.equals(getString(R.string.empty_)))
                    Utils().toast(requireContext(), getString(R.string.tables_ordere))
                else
                    retrieveUpdateList(value)
            }
        })
    }

    private fun retrieveUpdateList(
        value: ValuesItems
    ) {
        sessionManager.setTablesUser(value.idMeja.toString())
        sessionManager.setNameTablesUser(value.nameMeja.toString())
        updateTables()
    }

    private fun setupLoadEntireTable() {
        model.getEntireTables(
            ValuesItems(
                apikey = BuildConfig.API_Key
            )
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading_tables.visibility = View.GONE
                        resource.data.let { data -> retrieveList(data) }
                    }
                    Status.ERROR -> {
                        loading_tables.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        loading_tables.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun updateTables() {
        model.updateTable(
            ValuesItems(
                apikey = BuildConfig.API_Key,
                namaMeja = sessionManager.getNameTablesUser(),
                idMeja = sessionManager.getTablesUser(),
                statusMeja = getString(R.string.emptys_)
            )
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading_tables.visibility = View.GONE
                        resource.data.let { data -> retrieveUpdateTables(data) }
                    }
                    Status.ERROR -> {
                        loading_tables.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        loading_tables.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveUpdateTables(data: ResponseJSON?) {
        Utils().toast(requireContext(), getString(R.string.already_update))
        findNavController().navigateUp()
    }

    private fun retrieveList(data: ResponseJSON?) {
        if (data?.error == true)
            Utils().toast(requireContext(), data.message.toString())
        else {
            model.setDataTables(data)
            _TableAdapter.notifyDataSetChanged()
        }
    }

    private fun setupAdapter() {
        rv_tables.adapter = _TableAdapter
    }

}
