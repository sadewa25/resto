package com.devpro.resto.ui.detail_cart

import android.os.Bundle
import android.util.Log
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
import com.devpro.resto.databinding.FragmentDetailCartBinding
import com.devpro.resto.utils.SessionManager
import com.devpro.resto.utils.Utils
import com.devpro.resto.utils.common.EventObserver
import com.devpro.resto.utils.common.Status
import com.devpro.resto.utils.findNavController
import kotlinx.android.synthetic.main.fragment_detail_cart.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailCartFragment : Fragment() {

    private val model: DetailCartViewModel by viewModel()
    private val _detailCartAdapter by lazy {
        DetailCartAdapter(model)
    }
    private val sessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentDetailCartBinding>(
                inflater,
                R.layout.fragment_detail_cart,
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
        setupDataByOrders()
    }

    private fun setupObservers() {
        model.onItemOrder.observe(viewLifecycleOwner, EventObserver {
            insertOrder()
        })
    }

    private fun setupAdapter() {
        rv_detail_cart.adapter = _detailCartAdapter
    }

    private fun setupDataByOrders() {
        model.getViewCartByOrder(
            ValuesItems(
                apikey = BuildConfig.API_Key,
                noOrder = sessionManager.getNoOrder().toString()
            )
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading_detail_cart.visibility = View.GONE
                        resource.data.let { data -> retrieveCartOrder(data) }
                    }
                    Status.ERROR -> {
                        loading_detail_cart.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        loading_detail_cart.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun insertOrder() {
        model.insertOrder(
            ValuesItems(
                apikey = BuildConfig.API_Key,
                noOrder = sessionManager.getNoOrder().toString()
            )
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading_detail_cart.visibility = View.GONE
                        resource.data.let { data -> retrieveInsertOrder(data) }
                    }
                    Status.ERROR -> {
                        loading_detail_cart.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        loading_detail_cart.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveInsertOrder(data: ResponseJSON?) {
        if (data?.error == false) {
            sessionManager.setNoOrder("")
            sessionManager.setNameTablesUser("")
            sessionManager.setTablesUser("")
            sessionManager.setIdCart("")
            findNavController().navigateUp()
        } else {
            Utils().toast(requireContext(), data?.message.toString())
        }
    }

    private fun retrieveCartOrder(data: ResponseJSON?) {
        model.setDataCart(data?.values?.get(0)?.detail)
        Log.i("Informais Data :: ", data?.values.toString())
        _detailCartAdapter.notifyDataSetChanged()
    }

}
