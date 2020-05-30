package com.devpro.resto.ui.cart

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.devpro.resto.BuildConfig
import com.devpro.resto.R
import com.devpro.resto.data.source.remote.response.ResponseJSON
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.databinding.FragmentCartBinding
import com.devpro.resto.utils.SessionManager
import com.devpro.resto.utils.Utils
import com.devpro.resto.utils.common.EventObserver
import com.devpro.resto.utils.common.Status
import com.devpro.resto.utils.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_cart.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CartFragment : BottomSheetDialogFragment() {

    private val model: CartViewModel by viewModel()
    private var total = 0.0
    private val sessionManager by lazy {
        SessionManager(requireContext())
    }

    //name_menu,price_menu
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentCartBinding>(
                inflater,
                R.layout.fragment_cart,
                container,
                false
            )
        binding.lifecycleOwner = this
        binding.model = model
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeader()
        setupQtyChangedListener()
        setupObservers()

        Log.i("Informasii : ", sessionManager.getIdCart().toString())
    }

    private fun setupObservers() {
        model.openCart.observe(viewLifecycleOwner, EventObserver {
            postCart()
        })
    }

    private fun postCart() {
        if (arguments?.getString(getString(R.string.key_menus_fragment_status_insert), "")
                .toString()
                .equals("0")
        )
            model.insertCart(
                ValuesItems(
                    apikey = BuildConfig.API_Key,
                    idMenu = arguments?.getString(getString(R.string.id_menu)),
                    idMeja = sessionManager.getTablesUser().toString(),
                    idUser = sessionManager.getIDUser().toString(),
                    qty = ed_cart_qty.text.toString(),
                    totalHarga = total.toString(),
                    namaPemesan = ""
                )
            ).observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            loading_cart.visibility = View.GONE
                            resource.data.let { data -> retrieveList(data) }
                        }
                        Status.ERROR -> {
                            loading_cart.visibility = View.GONE
                        }
                        Status.LOADING -> {
                            loading_cart.visibility = View.VISIBLE
                        }
                    }
                }
            })
        else
            model.insertCart(
                ValuesItems(
                    apikey = BuildConfig.API_Key,
                    idMenu = arguments?.getString(getString(R.string.id_menu)),
                    idMeja = sessionManager.getTablesUser().toString(),
                    idUser = sessionManager.getIDUser().toString(),
                    qty = ed_cart_qty.text.toString(),
                    totalHarga = total.toString(),
                    namaPemesan = "",
                    idCart = sessionManager.getIdCart().toString()
                )
            ).observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            loading_cart.visibility = View.GONE
                            resource.data.let { data -> retrieveList(data) }
                        }
                        Status.ERROR -> {
                            loading_cart.visibility = View.GONE
                        }
                        Status.LOADING -> {
                            loading_cart.visibility = View.VISIBLE
                        }
                    }
                }
            })
    }

    private fun retrieveList(data: ResponseJSON?) {
        if (!data?.values.isNullOrEmpty()) {
            sessionManager.setNoOrder(data?.values?.get(0)?.noOrder.toString())
            sessionManager.setIdCart(data?.values?.get(0)?.idCart.toString())
        }
        Utils().toast(requireContext(), data?.message.toString())
        dismiss()
        findNavController().popBackStack(R.id.menusFragment, true)
    }

    private fun setupQtyChangedListener() {
        ed_cart_qty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!ed_cart_qty.text.isNullOrEmpty()) {
                    total = arguments?.getString(getString(R.string.price_menu), "")
                        .toString().toDouble() * ed_cart_qty.text.toString().toDouble()
                    tv_cart_total.text = Utils().getFormatRupiah(total)
                }
            }
        })
    }

    private fun setupHeader() {
        tv_cart_title.text = arguments?.getString(getString(R.string.name_menu), "").toString()
        tv_cart_price.text = Utils().getFormatRupiah(
            arguments?.getString(getString(R.string.price_menu), "")
                .toString().toDouble()
        )
    }

}
