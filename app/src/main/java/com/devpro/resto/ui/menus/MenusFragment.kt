package com.devpro.resto.ui.menus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.devpro.resto.BuildConfig
import com.devpro.resto.R
import com.devpro.resto.data.source.remote.response.ResponseJSON
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.databinding.FragmentMenusBinding
import com.devpro.resto.utils.Utils
import com.devpro.resto.utils.common.EventObserver
import com.devpro.resto.utils.common.Status
import com.devpro.resto.utils.findNavController
import kotlinx.android.synthetic.main.fragment_menus.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MenusFragment : Fragment() {

    private val model: MenusViewModel by viewModel()
    private val _menusAdapter by lazy {
        MenusAdapter(model)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentMenusBinding>(
                inflater,
                R.layout.fragment_menus,
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
        setupRvAdapter()
        setupGetMenusByCategory()
        setupObservers()
    }

    private fun setupObservers() {
        model.onItemClick.observe(viewLifecycleOwner, EventObserver {
            Utils().formDialogList(
                requireContext(),
                arrayListOf(getString(R.string.new_order), getString(R.string.add_order))
            ) { materialDialog: MaterialDialog, i: Int ->
                when (i) {
                    1 -> navigateToCart(it, "1")
                    0 -> navigateToCart(it, "0")
                }
            }
        })
    }

    private fun navigateToCart(it: ValuesItems, statusInsert: String) {
        val actions = MenusFragmentDirections.actionMenusFragmentToCartFragment(
            it.nameMenu,
            it.priceMenu,
            it.idMenu,
            statusInsert
        )
        findNavController().navigate(actions)
    }

    private fun setupHeader() {
        tv_menus_category.text = String.format(
            "%s : %s", getString(R.string.menu), arguments?.getString(
                getString(R.string.key_menus_fragment_name_category),
                ""
            ).toString()
        )
    }

    private fun setupGetMenusByCategory() {
        model.getMenuByCategory(
            ValuesItems(
                apikey = BuildConfig.API_Key,
                idKategori = arguments?.getString(
                    getString(R.string.key_menus_fragment_id_category),
                    ""
                ).toString()
            )
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loading_menus.visibility = View.GONE
                        resource.data.let { data -> retrieveList(data) }
                    }
                    Status.ERROR -> {
                        loading_menus.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        loading_menus.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(data: ResponseJSON?) {
        if (data?.error == false) {
            model.setDataMenu(data)
            _menusAdapter.notifyDataSetChanged()
        } else
            Utils().toast(requireContext(), data?.message.toString())
    }

    private fun setupRvAdapter() {
        rv_menus.adapter = _menusAdapter
    }

}
