package com.nonetxmxy.mmzqfxy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.databinding.ItemBankBinding
import com.nonetxmxy.mmzqfxy.databinding.ItemBankEmptyBinding
import com.nonetxmxy.mmzqfxy.model.auth.BankMessage
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible

class CardsAdapter(
    private val isSelectIconVisible: Boolean = false,
    private val goAddCards: () -> Unit,
    private val onItemSelected: (BankMessage) -> Unit = {}
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: List<BankMessage> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var currentSelectedIndex = 0
        set(value) {
            val oldValue = field
            field = value
            notifyItemChanged(oldValue)
            notifyItemChanged(value)
        }

    companion object {
        val EMPTY_VIEW_HOLDER = 1
        val BANK_ITEM = 2
    }

    class BankViewHolder(private val binding: ItemBankBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(
            bank: BankMessage,
            isSelected: Boolean,
            isSelectIconVisible: Boolean,
            onItemSelected: (BankMessage) -> Unit
        ) {
            binding.tvBankName.text = bank.TtoUz
            binding.tvBankType.text = bank.YfpxWBMDrp
            binding.tvBankNumber.text = bank.zUbbNgrgLl

            binding.ivSelect.setImageResource(if (isSelected) R.mipmap.xuanzhong else R.mipmap.weixuan)
            binding.ivSelect.setVisible(isSelectIconVisible)

            binding.root.setLimitClickListener {
                onItemSelected.invoke(bank)
            }
        }
    }

    class EmptyViewHolder(private val binding: ItemBankEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView() {

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
            holder.bindView(
                currentItem,
                isSelected = position == currentSelectedIndex,
                isSelectIconVisible = isSelectIconVisible
            ) {
                onItemSelected.invoke(it)
            }
        }
    }
}