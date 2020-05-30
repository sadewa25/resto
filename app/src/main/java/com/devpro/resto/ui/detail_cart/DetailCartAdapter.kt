package com.devpro.resto.ui.detail_cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.databinding.RvItemDetailCartBinding
import com.devpro.resto.utils.databinding.AppRecyclerView


class DetailCartAdapter(viewModel: DetailCartViewModel) :
    AppRecyclerView<DetailCartViewModel, RvItemDetailCartBinding, ValuesItems>(
        viewModel,
        DiffUtilDrug()
    ) {

    override fun onCreateViewBindingHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ViewHolder<RvItemDetailCartBinding> =
        ViewHolder(RvItemDetailCartBinding.inflate(inflater, parent, false))

    override fun onPrepareBindViewHolder(
        binding: RvItemDetailCartBinding,
        viewModel: DetailCartViewModel,
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
        return oldItem.idCart == newItem.idCart
    }

}