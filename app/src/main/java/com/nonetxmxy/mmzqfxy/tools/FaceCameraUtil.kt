package com.nonetxmxy.mmzqfxy.tools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.text.TextUtils
import androidx.exifinterface.media.ExifInterface
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UriUtils
import com.google.gson.Gson
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.customer_view.HandlePhoto
import com.nonetxmxy.mmzqfxy.model.PhotoInformation
import com.nonetxmxy.mmzqfxy.repository.create.AuthRepository
import com.nonetxmxy.mmzqfxy.service.ServiceImpl
import kotlinx.coroutines.*
import java.io.File

class FaceCameraUtil(private val context: Context?, private val imageCallBack: (String) -> Unit) {

    private val requestDataLoadDialog: RxDialogSet? by lazy {
        context?.let {
            RxDialogSet.provideDialog(context, R.layout.dia_loading)
        }
    }

    private val mainScope = MainScope()

    fun setImageData() {
        val imageType: ImageUtils.ImageType
        val bitmap: Bitmap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageType =
                ImageUtils.getImageType(UriUtils.uri2File(HandlePhoto.mCameraUri!!))
            val inputStream =
                context?.contentResolver?.openInputStream(HandlePhoto.mCameraUri)
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

    private fun saveImageInfo(imgUrl: String) {
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

    private fun handleImage(
        bitmap: Bitmap,
        imageType: String,
    ) {
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
                    val image = AuthRepository(
                        ServiceImpl.giveIAuthService(),
                        ServiceImpl.giveIFileUploadService()
                    ).uploadFile(base64, extName)
                    if (image.code == 1) {
                        imageCallBack.invoke(image.data?.XGEGbXpdxA ?: "")
                    } else {
                        ToastUtils.showShort(image.message ?: "")
                    }
                } catch (e: Exception) {
                    ErrorHandleUtil.handleError(e)
                } finally {
                    updateLoadingViewStatus(false)
                }
            }
        }
    }

    // 更新加载圈的状态，子类也可用
    private fun updateLoadingViewStatus(isLoading: Boolean) {
        if (isLoading) {
            if (requestDataLoadDialog?.isShowing == false)
                requestDataLoadDialog?.show()
        } else {
            if (requestDataLoadDialog?.isShowing == true)
                requestDataLoadDialog?.dismiss()
        }
    }
}
