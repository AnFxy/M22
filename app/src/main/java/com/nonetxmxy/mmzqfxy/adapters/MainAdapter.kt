package com.nonetxmxy.mmzqfxy.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.databinding.ItemMainBinding
import com.nonetxmxy.mmzqfxy.model.ProductsBean
import com.nonetxmxy.mmzqfxy.tools.days
import com.nonetxmxy.mmzqfxy.tools.jinE
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var datas: List<ProductsBean> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClick: (ProductsBean) -> Unit = {}

    class MainViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(productsBean: ProductsBean, doIt: () -> Unit) {
            if (productsBean.ZnXsyPioYz == 1) {
                binding.root.alpha = 1F
            } else {
                binding.root.alpha = 0.5F
            }

            Glide.with(binding.root.context).load(productsBean.MScSWMj).into(binding.ivLogo)
            binding.tvProName.text = productsBean.eFV
            binding.tvAmount.text = productsBean.FCmiye.toDouble().jinE()
            binding.tvDays.text = productsBean.OJjgAEGVuO.toInt().days()
            // binding.tvRate.text = "≥${productsBean.edVaOsmgaab}"
            binding.tvRate.text = "≥0.1%"

            when (productsBean.jMdbPgc) {
                1 -> {
                    binding.tvApply.text = Utils.getApp().getString(R.string.apply)
                    binding.tvApply.setTextColor(Utils.getApp().getColor(R.color.gray_532e00))
                    binding.tvApply.setBackgroundResource(R.drawable.yellow_8)
                }
                2 -> {
                    binding.tvApply.text = Utils.getApp().getString(R.string.on_reviewing)
                    binding.tvApply.setTextColor(Color.WHITE)
                    binding.tvApply.setBackgroundResource(R.drawable.green_8)
                }
                3 -> {
                    binding.tvApply.text = Utils.getApp().getString(R.string.reviewing_pass)
                    binding.tvApply.setTextColor(Color.WHITE)
                    binding.tvApply.setBackgroundResource(R.drawable.green_8)
                }
                4 -> {
                    binding.tvApply.text = Utils.getApp().getString(R.string.refused)
                    binding.tvApply.setTextColor(Color.WHITE)
                    binding.tvApply.setBackgroundResource(R.drawable.red_8)
                }
                7 -> {
                    binding.tvApply.text = Utils.getApp().getString(R.string.repay)
                    binding.tvApply.setTextColor(Color.WHITE)
                    binding.tvApply.setBackgroundResource(R.drawable.green_8)
                }
                8 -> {
                    binding.tvApply.text = Utils.getApp().getString(R.string.over_due)
                    binding.tvApply.setTextColor(Color.WHITE)
                    binding.tvApply.setBackgroundResource(R.drawable.red_8)
                }
                else -> {
                    binding.tvApply.text = Utils.getApp().getString(R.string.apply)
                    binding.tvApply.setTextColor(Utils.getApp().getColor(R.color.gray_532e00))
                    binding.tvApply.setBackgroundResource(R.drawable.yellow_8)
                }
            }

            binding.root.setLimitClickListener {
                if (productsBean.ZnXsyPioYz == 1) {
                    doIt.invoke()
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bindView(datas[position]) {
            onItemClick.invoke(datas[position])
        }

    override fun getItemCount(): Int = datas.size
}