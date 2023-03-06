package com.nonetxmxy.mmzqfxy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nonetxmxy.mmzqfxy.databinding.ItemBankBinding
import com.nonetxmxy.mmzqfxy.databinding.ItemBankEmptyBinding
import com.nonetxmxy.mmzqfxy.model.SampleBank
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener

class CardsAdapter(private val goAddCards: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: List<SampleBank> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
        val EMPTY_VIEW_HOLDER = 1
        val BANK_ITEM = 2
    }

    class BankViewHolder(private val binding: ItemBankBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(bank: SampleBank) {
            binding.tvBankName.text = bank.bankName
            binding.tvBankType.text = bank.bankType
            binding.tvBankNumber.text = bank.bankNumber
        }
    }

    class EmptyViewHolder(private val binding: ItemBankEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView() {
            // TODO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == EMPTY_VIEW_HOLDER) {
            val emptyBinding = ItemBankEmptyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            emptyBinding.tvAddCards.setLimitClickListener {
                goAddCards.invoke()
            }
            EmptyViewHolder(emptyBinding)
        } else {
            val itemBinding = ItemBankBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            BankViewHolder(itemBinding)
        }

    override fun getItemCount(): Int = if (orders.isEmpty()) 1 else orders.size

    override fun getItemViewType(position: Int): Int =
        if (orders.isEmpty()) EMPTY_VIEW_HOLDER else BANK_ITEM

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EmptyViewHolder) {
            holder.bindView()
        } else if (holder is BankViewHolder) {
            val currentItem = orders[position]
            holder.bindView(currentItem)
        }
    }
}