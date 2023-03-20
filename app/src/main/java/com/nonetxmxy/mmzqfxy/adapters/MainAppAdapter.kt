package com.nonetxmxy.mmzqfxy.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.databinding.ItemMainBinding
import com.nonetxmxy.mmzqfxy.model.response.AppBean
import com.nonetxmxy.mmzqfxy.tools.days
import com.nonetxmxy.mmzqfxy.tools.jinE
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible

class MainAppAdapter : RecyclerView.Adapter<MainAppAdapter.MainAppViewHolder>() {

    var datas: List<AppBean> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClick: (AppBean) -> Unit = {}

    class MainAppViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(appBean: AppBean, doIt: () -> Unit) {
            if (appBean.jrkS == 1) {
                binding.root.alpha = 1F
            } else {
                binding.root.alpha = 0.5F
            }

            Glide.with(binding.root.context).load(appBean.EJfFZ).into(binding.ivLogo)
            binding.tvProName.text = appBean.CaktfKRiG
            binding.tvAmount.text = appBean.CbPtlpa.jinE()
            binding.tvDays.text = appBean.KSB.days()
            binding.containerRate.setVisible(false)


            binding.tvApply.text = Utils.getApp().getString(R.string.open_app)
            binding.tvApply.setTextColor(Color.WHITE)
            binding.tvApply.setBackgroundResource(R.drawable.green_8)

            binding.root.setLimitClickListener {
                doIt.invoke()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAppViewHolder =
        MainAppViewHolder(
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MainAppViewHolder, position: Int) =
        holder.bindView(datas[position]) {
            onItemClick.invoke(datas[position])
        }

    override fun getItemCount(): Int = datas.size
}