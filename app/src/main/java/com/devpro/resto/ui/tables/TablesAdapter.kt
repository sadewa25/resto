package com.devpro.resto.ui.tables

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.databinding.RvItemTablesBinding
import com.devpro.resto.utils.databinding.AppRecyclerView

class TablesAdapter (viewModel: TablesViewModel) :
    AppRecyclerView<TablesViewModel, RvItemTablesBinding, ValuesItems>(
        viewModel,
        DiffUtilDrug()
    ) {

    override fun onCreateViewBindingHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewHolder<RvItemTablesBinding> =
        ViewHolder(RvItemTablesBinding.inflate(inflater, parent, false))

    override fun onPrepareBindViewHolder(
        binding: RvItemTablesBinding,
        viewModel: TablesViewModel,
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