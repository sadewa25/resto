package com.devpro.resto.ui.detail_cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.devpro.resto.data.source.remote.response.DetailItems
import com.devpro.resto.databinding.RvItemDetailCartBinding
import com.devpro.resto.utils.databinding.AppRecyclerView


class DetailCartAdapter(viewModel: DetailCartViewModel) :
    AppRecyclerView<DetailCartViewModel, RvItemDetailCartBinding, DetailItems>(
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
        model: DetailItems
    ) {
        binding.menus = model
        binding.model = viewModel
    }

}

class DiffUtilDrug : DiffUtil.ItemCallback<DetailItems>() {

    override fun areItemsTheSame(oldItem: DetailItems, newItem: DetailItems): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DetailItems, newItem: DetailItems): Boolean {
        return oldItem.idCart == newItem.idCart
    }

}