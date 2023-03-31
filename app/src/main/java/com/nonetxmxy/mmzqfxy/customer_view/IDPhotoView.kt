package com.nonetxmxy.mmzqfxy.customer_view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.databinding.IdPhotoViewBinding
import com.nonetxmxy.mmzqfxy.model.PhotoInformation
import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import com.nonetxmxy.mmzqfxy.tools.ErrorHandleUtil
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class IDPhotoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    @Inject
    lateinit var homeRepository: IAuthRepository

    private var belongActivity: Activity? = null

    var isSelect = true
    private val imagePickerDialog: RxDialogSet by lazy {
        RxDialogSet.provideDialogBottom(context, R.layout.dia_photo)
    }

    val requestDataLoadDialog: RxDialogSet by lazy {
        RxDialogSet.provideDialog(context, R.layout.dia_loading)
    }

    private var _binding: IdPhotoViewBinding
    protected val binding get() = _binding
    private val mainScope = MainScope()
    private var imagePageType = 1
    private var imageUpLoadListener: ILoadImageListener? = null
    private var startOpenAlbumListener: IStartOpenAlbumListener? = null
    private var startCameraAlbumListener: IStartCameraListener? = null
    var loadLiveData: MutableLiveData<Boolean>? = null


    init {
        val root = View.inflate(context, R.layout.id_photo_view, this)
        _binding = IdPhotoViewBinding.bind(root)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.IDPhotoView)
        imagePageType = ta.getInt(R.styleable.IDPhotoView_Direction, 1)
        val defaultUrl = ta.getDrawable(R.styleable.IDPhotoView_imageSrc)
        binding.ivFixDefaultCard.setImageDrawable(defaultUrl)

        binding.ivFixDefaultCard.setLimitClickListener {
            showImagePickerDialog()
        }
        ta.recycle()
    }

    // 必须要调用的方法
    fun setActivity(activity: Activity) {
        this.belongActivity = activity
    }

    private fun saveImageInfo(imgUrl: String) {
        //如果是工作图片就不保存
        if (imagePageType == 1) {
            return
        }
        val exifInterface = ExifInterface(imgUrl)
        val strLatitude = if (TextUtils.isEmpty(
                exifInterface.getAttribute(
                    ExifInterface
                        .TAG_GPS_LATITUDE_REF
                )
            )
        )
            "0.0"
        else
            exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)
        val strLongitude =
            if (TextUtils.isEmpty(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF))) "0.0" else exifInterface.getAttribute(
                ExifInterface.TAG_GPS_LONGITUDE_REF
            )
        val imageExif = exifInterface.getAttribute(ExifInterface.TAG_DATETIME)?.let {
            PhotoInformation(
                "$strLatitude,$strLongitude",
                it,
                FileUtils.getSize(imgUrl),
                exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH) + "," + exifInterface.getAttribute(
                    ExifInterface.TAG_IMAGE_WIDTH
                ),
                exifInterface.getAttribute(ExifInterface.TAG_MAKE)!!
            )
        }
        LocalCache.photoInfo = Gson().toJson(imageExif)
    }

    companion object {
        const val ALBUM_REQUEST_CODE_TOP = 11
        const val ALBUM_REQUEST_CODE_BEHIND = 12
        const val ALBUM_SUGGESTION = 13
        const val CAMERA_REQUEST_CODE_TOP = 21
        const val CAMERA_REQUEST_CODE_BEHIND = 22
    }

    fun setImageUpLoadListener(imageUpLoadListener: ILoadImageListener) {
        this.imageUpLoadListener = imageUpLoadListener
    }

    fun setStartOpenAlbumListener(listener: IStartOpenAlbumListener) {
        this.startOpenAlbumListener = listener
    }

    fun setStartCameraListener(listener: IStartCameraListener) {
        this.startCameraAlbumListener = listener
    }

    private fun showImagePickerDialog() {
        if (isSelect) {
            with(imagePickerDialog) {
                show()
                setViewState<TextView>(R.id.tv_camera) {
                    setLimitClickListener {
                        PermissionUtils.permission(
                            PermissionConstants.CAMERA,
                            PermissionConstants.STORAGE
                        ).callback(object :
                            SimplePermissionCallBack() {
                            override fun onGranted(granted: MutableList<String>) {
                                imagePickerDialog.dismiss()
                                startCameraAlbumListener?.onStartCamera()
                            }
                        }).request()
                    }
                }.setViewState<TextView>(R.id.tv_photo) {
                    setLimitClickListener {
                        PermissionUtils.permission(PermissionConstants.STORAGE)
                            .callback(object : SimplePermissionCallBack() {
                                override fun onGranted(granted: MutableList<String>) {
                                    imagePickerDialog.dismiss()
                                    startOpenAlbumListener?.onStartAlbum()
                                }
                            }).request()
                    }
                }.setViewState<TextView>(R.id.tv_cancel) {
                    setLimitClickListener {
                        imagePickerDialog.dismiss()
                    }
                }
            }
        }
    }

    fun setImageData(requestCode: Int, uri: Uri? = null) {
        when (requestCode) {
            ALBUM_REQUEST_CODE_TOP, ALBUM_REQUEST_CODE_BEHIND, ALBUM_SUGGESTION -> {
                val imgPath = HandlePhoto.handleImageOkKitKat(uri, context)
                val imageType = ImageUtils.getImageType(imgPath)
                if (imageType == null) {
                    ToastUtils.showShort("Fallo el tipo de imagen obtenida!")
                    return
                }
                val bitmap = BitmapFactory.decodeFile(imgPath)
                handleImage(bitmap, imageType.value)
                saveImageInfo(imgPath)
            }
            CAMERA_REQUEST_CODE_TOP, CAMERA_REQUEST_CODE_BEHIND -> {
                val imageType: ImageUtils.ImageType
                val bitmap: Bitmap
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    imageType =
                        ImageUtils.getImageType(UriUtils.uri2File(HandlePhoto.mCameraUri!!))
                    val inputStream =
                        context.contentResolver.openInputStream(HandlePhoto.mCameraUri)
                    bitmap =
                        BitmapFactory.decodeStream(inputStream)
                    saveImageInfo(UriUtils.uri2File(HandlePhoto.mCameraUri).absolutePath)
                } else {
                    imageType =
                        ImageUtils.getImageType(File(HandlePhoto.mCameraImagePath))
                    bitmap =
                        BitmapFactory.decodeFile(HandlePhoto.mCameraImagePath)
                    saveImageInfo(HandlePhoto.mCameraImagePath)
                }
                handleImage(bitmap, imageType.value)
            }
        }
    }

    private fun handleImage(
        bitmap: Bitmap,
        imageType: String,
    ) {
        loadLiveData?.postValue(true)
        mainScope.launch {
            val base64Str = withContext(Dispatchers.IO) {
                HandlePhoto.compressBitmap2Base64(
                    HandlePhoto.zoomImage(bitmap), 250L
                )
            }
            uploadImageBase64(base64Str, imageType, this)
        }
    }

    private fun uploadImageBase64(base64: String, extName: String, scope: CoroutineScope) {
        scope.apply {
            launch {
                try {
                    updateLoadingViewStatus(true)
                    val image = homeRepository.uploadFile(base64, extName)
                    if (image.code == 1) {
                        imageUpLoadListener?.onPhotoGot(image.data?.url ?: "", imagePageType)
                    } else {
                        ToastUtils.showShort(image.message ?: "")
                        loadLiveData?.postValue(false)
                    }
                } catch (e: Exception) {
                    ErrorHandleUtil.handleError(e)
                    loadLiveData?.postValue(false)
                } finally {
                    updateLoadingViewStatus(false)
                }
            }
        }
    }

    // 更新加载圈的状态，子类也可用
    fun updateLoadingViewStatus(isLoading: Boolean) {
        if (isLoading) {
            if (!requestDataLoadDialog.isShowing)
                requestDataLoadDialog.show()
        } else {
            if (requestDataLoadDialog.isShowing)
                requestDataLoadDialog.dismiss()
        }
    }

    fun setImageUrl(url: String) {
        if (StringUtils.isTrimEmpty(url)) {
            binding.ivIdCard.visibility = View.GONE
            binding.ivFixDefaultCard.visibility = View.GONE
        } else {
            binding.ivIdCard.visibility = View.VISIBLE
            Glide.with(context).load(url).into(binding.ivIdCard)
        }
    }
}