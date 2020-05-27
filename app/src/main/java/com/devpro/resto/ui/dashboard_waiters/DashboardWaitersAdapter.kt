package com.devpro.resto.ui.dashboard_waiters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.databinding.RvItemDashboardWaitersBinding
import com.devpro.resto.utils.databinding.AppRecyclerView

class DashboardWaitersAdapter(viewModel: DashboardWaitersViewModel) :
    AppRecyclerView<DashboardWaitersViewModel, RvItemDashboardWaitersBinding, ValuesItems>(
        viewModel,
        DiffUtilDrug()
    ) {

    override fun onCreateViewBindingHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewHolder<RvItemDashboardWaitersBinding> =
        ViewHolder(RvItemDashboardWaitersBinding.inflate(inflater, parent, false))

    override fun onPrepareBindViewHolder(
        binding: RvItemDashboardWaitersBinding,
        viewModel: DashboardWaitersViewModel,
        model: ValuesItems
    ) {
        binding.menus = model
        binding.model = viewModel
    }

}

class DiffUtilDrug : DiffUtil.ItemCallback<ValuesItems>() {

    override fun areItemsTheSame(oldItem: ValuesItems, newItem: ValuesItems): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ValuesItems, newItem: ValuesItems): Boolean {
        return oldItem.nameKategoriMenu == newItem.nameKategoriMenu
    }

}