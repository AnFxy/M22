package com.nonetxmxy.mmzqfxy.customer_view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.TimeUtils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.databinding.ViewCommonSelectBinding
import com.nonetxmxy.mmzqfxy.dialogs.AddressSelectDialog
import com.nonetxmxy.mmzqfxy.dialogs.BanksDialog
import com.nonetxmxy.mmzqfxy.dialogs.CommonSelectDialog
import com.nonetxmxy.mmzqfxy.dialogs.IStrSelectListener
import com.nonetxmxy.mmzqfxy.model.Regions
import com.nonetxmxy.mmzqfxy.model.response.Tags
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import java.util.*

class CommonSelectView constructor(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs) {

    private val binding = ViewCommonSelectBinding.inflate(LayoutInflater.from(context), this, true)

    private val commonSelectDialog by lazy {
        CommonSelectDialog(context, R.style.SimpleDialog)
    }

    private val addressSelectDialog by lazy {
        AddressSelectDialog(context, R.style.SimpleDialog)
    }

    private val bankSelectDialog by lazy {
        BanksDialog(context, R.style.SimpleDialog)
    }

    var clickOptionItemBlock: ((Tags) -> Unit)? = null
    var addressSelectOKBlock: ((String, String) -> Unit)? = null
    var contactPersonOKBlock: (() -> Unit)? = null
    var dateSelectOkBlock: ((String) -> Unit)? = null
    var bankSelectOkBlock: ((String) -> Unit)? = null

    var specailActivity: FragmentActivity? = null

    private var pvCustomLunar: TimePickerView? = null

    private var selectType = 0

    var selectTitle = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) binding.title.text = value
        }

    var selectContent = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                binding.content.typeface = Typeface.DEFAULT_BOLD
                binding.content.text = value
            }
        }

    init {
        initAttribute(attrs)
        initListener()
    }

    private fun initListener() {
        binding.rootLayout.setOnClickListener {
            when (selectType) {
                0 -> {
                    commonSelectDialog.show()
                }

                1 -> {
                    addressSelectDialog.show()
                }
                2 -> {
                    //联系人选择回调
                    contactPersonOKBlock?.invoke()
                }
                3 -> {
                    showDate(false)
                }
                4 -> {
                    bankSelectDialog.show()
                }
            }
        }

        //通用选择回调
        commonSelectDialog.clickItemBlock = {
            binding.content.text = it.cnTVzVSsBYV
            binding.content.typeface = Typeface.DEFAULT_BOLD
            clickOptionItemBlock?.invoke(it)
        }

        //地址選擇回调
        addressSelectDialog.addressSelectOKBlock = { province: String, city: String ->
            binding.content.text = "$province/$city"
            binding.content.typeface = Typeface.DEFAULT_BOLD
            addressSelectOKBlock?.invoke(province, city)
        }

        // 银行选择 dialog
        bankSelectDialog.selectorListener = object : IStrSelectListener {
            override fun onStrSelect(value: String) {
                binding.content.text = value
                binding.content.typeface = Typeface.DEFAULT_BOLD
                bankSelectOkBlock?.invoke(value)
            }
        }
    }

    fun showOptionDialog() {
        commonSelectDialog.show()
    }

    fun showAddressSelectDialog() {
        addressSelectDialog.show()
    }

    fun setOptionShowList(data: List<Tags>?) {
        if (data.isNullOrEmpty()) return
        commonSelectDialog.setOptionShowList(selectTitle, data)
    }

    fun setAdministrativeList(data: Regions) {
        addressSelectDialog.setAdministrativeList(selectTitle, data)
    }

    fun setBanks(data: List<String>) {
        bankSelectDialog.setData(data, binding.title.text.toString())
    }

    private fun showDate(isDay: Boolean) {
        val booleanArray = if (isDay) {
            booleanArrayOf(false, false, true, false, false, false)
        } else {
            booleanArrayOf(true, true, true, false, false, false)
        }

        val selectDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.set(1950, 0, 1)
        if (isDay) {
            selectDate.set(2000, 11, 31)
        }
        pvCustomLunar = TimePickerBuilder(specailActivity) { date, _ ->
            val time = if (isDay) {
                TimeUtils.date2String(date, "dd")
            } else {
                TimeUtils.date2String(date, "dd-MM-yyyy")
            }

            binding.content.text = time
            binding.content.typeface = Typeface.DEFAULT_BOLD
            dateSelectOkBlock?.invoke(time)
        }
            .setDate(selectDate)
            .setRangDate(startDate, selectDate)
            .setLayoutRes(R.layout.dia_date){ v ->
                val ivCancel = v.findViewById<TextView>(R.id.tv_cancel_date_pick)
                val tvTitle = v.findViewById<TextView>(R.id.tv_title_date_pick)
                val tvSubmit = v.findViewById<TextView>(R.id.tv_submit_date_pick)
                tvSubmit.text = "Completado"
                tvTitle.text = "Fecha de entrada"
                ivCancel.text = "Cancelacion"

                tvSubmit.setLimitClickListener {
                    pvCustomLunar?.returnData()
                    pvCustomLunar?.dismiss()
                }
                ivCancel.setLimitClickListener {
                    pvCustomLunar?.dismiss()
                }
            }
            .setTextColorCenter(ContextCompat.getColor(context, R.color.black))
            .isCenterLabel(false)
            .setDividerColor(Color.WHITE)
            .setLabel("", "", "", "", "","")
            .setType(booleanArray)
            .build()

        pvCustomLunar?.show()
    }

    private fun initAttribute(attrs: AttributeSet?) {
        val t = context.obtainStyledAttributes(attrs, R.styleable.CommonSelectView)
        selectType = t.getInt(R.styleable.CommonSelectView_select_type, 0)
        selectTitle = t.getString(R.styleable.CommonSelectView_select_title) ?: ""
        val textViewEndSrc = t.getDrawable(R.styleable.CommonSelectView_android_drawableEnd)
        val tvIconVisibility = t.getBoolean(R.styleable.CommonSelectView_tvIconVisibility, true)
        if (!tvIconVisibility) {
            binding.content.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                null,
                null
            )
        } else {
            if (textViewEndSrc != null) {
                binding.content.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    textViewEndSrc,
                    null
                )
            }
        }
        t.recycle()
    }
}