package com.nonetxmxy.mmzqfxy.customer_view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.animation.addListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.nonetxmxy.mmzqfxy.databinding.ItemSubtitleBinding
import com.nonetxmxy.mmzqfxy.model.SubtitleInfo
import com.nonetxmxy.mmzqfxy.tools.setVisible
import java.lang.ref.WeakReference
import kotlin.math.abs
import kotlin.random.Random

class RollText : FrameLayout, LifecycleEventObserver {
    private val mSubtitleInfoList = ArrayList<SubtitleInfo>()
    private val mCacheViewList = ArrayList<ItemSubtitleBinding>()
    private val mAnimatorMap = HashMap<ItemSubtitleBinding, ObjectAnimator>()
    private var mLeftMargin = 30
    private var mLines = 1
    private var mScreenWidth = 0
    private var mCurrentPosition = 0
    private var mSpeed = 0.1f
    private var mSubtitleClickCallback: WeakReference<SubtitleItemClickCallback>? = null
    private var isHidden = true

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0, 0)

    constructor(
        context: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attributeSet, defStyleAttr, defStyleRes) {
        mScreenWidth = context.resources.displayMetrics.widthPixels
    }

    /**
     * 设置数据
     */
    fun setData(subtitleInfoList: List<SubtitleInfo>) {
        mSubtitleInfoList.clear()
        mSubtitleInfoList.addAll(subtitleInfoList)
        initData()
    }

    /**
     * 追加数据
     */
    fun addData(subtitleInfoList: ArrayList<SubtitleInfo>) {
        mSubtitleInfoList.addAll(subtitleInfoList)
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        if (mSubtitleInfoList.isEmpty()) {
            return
        }

        for (line in 0 until mLines) {
            initView(line)
        }
    }

    /**
     * 初始化并启动滚动的弹幕
     */
    private fun initView(line: Int) {
        //初始化布局
        val itemSubtitleBinding: ItemSubtitleBinding
        if (mCacheViewList.isNotEmpty()) {
            itemSubtitleBinding = mCacheViewList[0]
            mCacheViewList.removeAt(0)
        } else {
            itemSubtitleBinding = ItemSubtitleBinding.inflate(LayoutInflater.from(context))
            itemSubtitleBinding.root.setOnClickListener {
                run {
                    mSubtitleClickCallback?.apply {
                        get()?.clickSubTitleItem(itemSubtitleBinding.tvContent.text.toString())
                    }
                }
            }
        }

        //索引归位
        if (mCurrentPosition >= mSubtitleInfoList.size) {
            mCurrentPosition = 0
        }

        //绑定数据
        itemSubtitleBinding.tvContent.text = mSubtitleInfoList[mCurrentPosition].message
        itemSubtitleBinding.ivTouxiang.setImageResource(mSubtitleInfoList[mCurrentPosition].icon)
        itemSubtitleBinding.viewHidden.setVisible(isHidden)
        itemSubtitleBinding.root.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)

        //初始化宽高 边距
        val measuredHeight = itemSubtitleBinding.root.measuredHeight
        val measuredWidth = itemSubtitleBinding.root.measuredWidth
        val layoutParams = LayoutParams(measuredWidth, measuredHeight)
        layoutParams.leftMargin = mScreenWidth
        layoutParams.topMargin = measuredHeight * line
        itemSubtitleBinding.root.layoutParams = layoutParams
        addView(itemSubtitleBinding.root)

        //初始化动画
        val totalScrollX = mScreenWidth + measuredWidth
        val animator = ObjectAnimator.ofFloat(
            itemSubtitleBinding.root,
            "translationX",
            0f,
            -totalScrollX.toFloat()
        )
        mAnimatorMap[itemSubtitleBinding] = animator

        //开启动画
        val duration = abs(totalScrollX / mSpeed)
        animator.duration = duration.toLong()
        animator.interpolator = LinearInterpolator()
        animator.start()

        //监听动画滑动距离
        val animatorUpdateListener = ValueAnimator.AnimatorUpdateListener { animator ->
            var animatedValue: Float = abs(animator.animatedValue as Float)
            if (animatedValue >= (measuredWidth + mLeftMargin)) {
                animator.removeAllUpdateListeners()
                initView(line)
            }
        }
        animator.addUpdateListener(animatorUpdateListener)

        //监听动画执行完毕
        animator.addListener(onEnd = {
            mCacheViewList.add(itemSubtitleBinding)
            mAnimatorMap.remove(itemSubtitleBinding)
            removeView(itemSubtitleBinding.root)
        })

        //索引自增
        mCurrentPosition++
    }

    /**
     * 设置弹幕之间的边距
     */
    fun setLeftMargin(leftMargin: Int) {
        this.mLeftMargin = leftMargin
    }

    fun setHiddenIt(isHidden: Boolean) {
        this.isHidden = isHidden
        invalidate()
    }

    /**
     * 设置弹幕行数
     */
    fun setLines(lines: Int) {
        this.mLines = lines
    }

    /**
     * 设置宽度
     */
    fun setScreenWidth(width: Int) {
        this.mScreenWidth = width
    }

    /**
     * 设置滚动速度
     */
    fun setScrollSpeed(speed: Float) {
        this.mSpeed = speed
    }

    /**
     * 设置滚动弹幕点击回调
     */
    fun setItemClickCallback(subtitleItemClickCallback: SubtitleItemClickCallback) {
        this.mSubtitleClickCallback = WeakReference(subtitleItemClickCallback)
    }

    /**
     * 生命周期回调
     */
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_PAUSE -> stopScroll()
            Lifecycle.Event.ON_RESUME -> startScroll()
            Lifecycle.Event.ON_DESTROY -> destroy()
            else -> {}
        }
    }

    /**
     * 停止滚动
     */
    private fun stopScroll() {
        if (mAnimatorMap.isNotEmpty()) {
            for (animator in mAnimatorMap) {
                animator.value.pause()
            }
        }
    }

    /**
     * 恢复滚动
     */
    private fun startScroll() {
        if (mAnimatorMap.isNotEmpty()) {
            for (animator in mAnimatorMap) {
                animator.value.resume()
            }
        }
    }

    /**
     * 停止动画 销毁弹幕
     */
    private fun destroy() {
        if (mAnimatorMap.isNotEmpty()) {
            for (animator in mAnimatorMap) {
                animator.value.cancel()
            }
        }

        removeAllViews()
        mCacheViewList.clear()
        mSubtitleInfoList.clear()
    }

    /**
     * 点击回调接口
     */
    interface SubtitleItemClickCallback {
        fun clickSubTitleItem(subtitleInfo: String)
    }
}