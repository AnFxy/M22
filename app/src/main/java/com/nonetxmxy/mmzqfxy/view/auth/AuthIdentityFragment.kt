package com.nonetxmxy.mmzqfxy.view.auth

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.customer_view.*
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthIdentityBinding
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.PageType
import com.nonetxmxy.mmzqfxy.model.PhotoType
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.AuthIdentityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AuthIdentityFragment : BaseFragment<FragmentAuthIdentityBinding, AuthIdentityViewModel>() {

    private val viewModel: AuthIdentityViewModel by viewModels()

    private lateinit var photoLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>

    override fun getViewMode() = viewModel

    private val uploadIdentityDialog by lazy {
        val dialog =
            context?.let { RxDialogSet.provideDialogBottom(it, R.layout.dia_upload_identity) }
        dialog?.setViewState<TextView>(R.id.cancelar) {
            setLimitClickListener {
                dialog.dismiss()
            }
        }?.setViewState<TextView>(R.id.grabar) {
            setLimitClickListener {
                PermissionUtils.permission(PermissionConstants.CAMERA)
                    .callback(object : PermissionUtils.FullCallback {
                        override fun onGranted(granted: MutableList<String>) {
                            cameraLauncher.launch(Uri.fromFile(File("")))
                        }

                        override fun onDenied(
                            deniedForever: MutableList<String>,
                            denied: MutableList<String>
                        ) {

                        }
                    }).request()
                dialog.dismiss()
            }
        }?.setViewState<TextView>(R.id.album) {
            setLimitClickListener {
                PermissionUtils.permission(PermissionConstants.CAMERA)
                    .callback(object : PermissionUtils.FullCallback {
                        override fun onGranted(granted: MutableList<String>) {

                        }

                        override fun onDenied(
                            deniedForever: MutableList<String>,
                            denied: MutableList<String>
                        ) {

                        }
                    })
                dialog.dismiss()
            }
        }
        dialog
    }

    override fun getViewBinding(
        inflater: LayoutInflater, parent: ViewGroup?
    ) = FragmentAuthIdentityBinding.inflate(
        inflater, parent, false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                when (viewModel.photoType.value) {
                    PhotoType.TOP_PHOTO -> binding.ivTop.setImageData(
                        IDPhotoView.ALBUM_REQUEST_CODE_TOP,
                        it
                    )
                    PhotoType.BEHIND_PHOTO -> binding.ivBehind.setImageData(
                        IDPhotoView.ALBUM_REQUEST_CODE_BEHIND,
                        it
                    )
                    else -> {}
                }
            }
        }

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccss ->
                if (isSuccss) {
                    when (viewModel.photoType.value) {
                        PhotoType.TOP_CAME -> binding.ivTop.setImageData(IDPhotoView.CAMERA_REQUEST_CODE_TOP)
                        PhotoType.BEHIND_CAME -> binding.ivBehind.setImageData(IDPhotoView.CAMERA_REQUEST_CODE_BEHIND)
                        else -> {}
                    }
                }
            }
    }

    override fun FragmentAuthIdentityBinding.setLayout() {
        mToolbar.setupWithNavController(navController)
        mToolbar.setNavigationIcon(R.mipmap.fanhui)
        includeAuthTitle.image.setImageResource(R.mipmap.jinbi4)

        activity?.let {
            binding.ivTop.setActivity(it)
            binding.ivBehind.setActivity(it)
        }

        binding.ivTop.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.photoType.value = PhotoType.TOP_PHOTO
            }
        })

        binding.ivTop.setStartCameraListener(object : IStartCameraListener {
            override fun onStartCamera() {
                cameraLauncher.launch(HandlePhoto.createImageUri(context))
                viewModel.photoType.value = PhotoType.TOP_CAME
            }
        })

        binding.ivBehind.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.photoType.value = PhotoType.BEHIND_PHOTO
            }
        })

        binding.ivBehind.setStartCameraListener(object : IStartCameraListener {
            override fun onStartCamera() {
                cameraLauncher.launch(HandlePhoto.createImageUri(context))
                viewModel.photoType.value = PhotoType.BEHIND_CAME
            }
        })

        binding.includeAuthBottom.enviarBtn.setLimitClickListener {
            if (checkData()) {
                viewModel.submitIdentifyInfo()
            }
        }
    }

    override fun setObserver() {
        binding.ivTop.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                LocalCache.idCardTop = url
                // 进行 OCR人脸识别
                viewModel.startOCRFlow(url, flag)
            }
        })

        binding.ivBehind.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                LocalCache.idCardBehind = url
                // 进行 OCR人脸识别
                viewModel.startOCRFlow(url, flag)
            }
        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagerEventFlow.collect {
                when (it) {
                    AuthPagerEvent.UpdatePageView -> updatePage()
                    AuthPagerEvent.Finish -> navController.popBackStack()
                    AuthPagerEvent.GoNextPage -> viewModel.checkGoWhichVerificationPage()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel._baseGoPage.collect {
                when (it) {
                    PageType.USER -> navController.navigate(AuthIdentityFragmentDirections.actionAuthIdentityFragmentToAuthUserInfoFragment())
                    PageType.WORK -> navController.navigate(AuthIdentityFragmentDirections.actionAuthIdentityFragmentToAuthUserWorkFragment())
                    PageType.CONTRACT -> navController.navigate(AuthIdentityFragmentDirections.actionAuthIdentityFragmentToAuthContactPersonFragment())
                    PageType.ID -> {}
                    PageType.BANK -> navController.navigate(AuthIdentityFragmentDirections.actionAuthIdentityFragmentToAddCardsFragment())
                    PageType.FACE -> {}
                    PageType.CONFIRM -> navController.navigate(AuthIdentityFragmentDirections.actionAuthIdentityFragmentToConfirmRequestFragment())
                }
            }
        }
    }

    private fun updatePage() {
        if (viewModel.pagerData.AgzmxkTVhrb.isNotEmpty()) {
            binding.ivTop.setImageUrl(viewModel.pagerData.AgzmxkTVhrb)
        }

        if (viewModel.pagerData.MpOHLbXEBT.isNotEmpty()) {
            binding.ivBehind.setImageUrl(viewModel.pagerData.MpOHLbXEBT)
        }

        binding.containerOcrResult.setVisible(
            viewModel.pagerData.AgzmxkTVhrb.isNotEmpty() && viewModel.pagerData.MpOHLbXEBT.isNotEmpty()
        )

        binding.containerIdCardCondition.setVisible(
            viewModel.pagerData.AgzmxkTVhrb.isEmpty() || viewModel.pagerData.MpOHLbXEBT.isEmpty()
        )

        binding.inputName.inputContent = viewModel.pagerData.fOEEzcNxpv
        binding.inputFaName.inputContent = viewModel.pagerData.JkfImZtlQ
        binding.inputMaName.inputContent = viewModel.pagerData.Tjq
        binding.inputIdNumber.inputContent = viewModel.pagerData.ExHTUA
        binding.inputRfcNumber.inputContent = viewModel.pagerData.DEyLxCETnd
    }

    private fun checkData(): Boolean {
        viewModel.pagerData = viewModel.pagerData.copy(
            fOEEzcNxpv = binding.inputName.editValue,
            JkfImZtlQ = binding.inputFaName.editValue,
            Tjq = binding.inputMaName.editValue,
            ExHTUA = binding.inputIdNumber.editValue,
            DEyLxCETnd = binding.inputRfcNumber.editValue
        )
        binding.root.clearFocus()

        if (viewModel.pagerData.AgzmxkTVhrb.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    getString(R.string.id_card_top_des)
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.ivTop.top)
            return false
        }

        if (viewModel.pagerData.MpOHLbXEBT.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    getString(R.string.id_card_behind_des)
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.ivBehind.top)
            return false
        }

        if (viewModel.pagerData.fOEEzcNxpv.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.input_error_hint),
                    binding.inputName.inputTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.inputName.top)
            return false
        }

        if (viewModel.pagerData.JkfImZtlQ.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.input_error_hint),
                    binding.inputFaName.inputTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.inputFaName.top)
            return false
        }

        if (viewModel.pagerData.Tjq.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.input_error_hint),
                    binding.inputMaName.inputTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.inputMaName.top)
            return false
        }

        if (viewModel.pagerData.ExHTUA.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.input_error_hint),
                    binding.inputIdNumber.inputTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.inputIdNumber.top)
            return false
        }

//        if (viewModel.pagerData.DEyLxCETnd.isEmpty()) {
//            ToastUtils.showShort(
//                StringUtils.format(
//                    StringUtils.getString(R.string.input_error_hint),
//                    binding.inputRfcNumber.inputTitle
//                )
//            )
//            binding.scrollView.smoothScrollTo(0, binding.inputRfcNumber.top)
//            return false
//        }

        if (LocalCache.faceCredit == 0) {
            ToastUtils.showShort(getString(R.string.please_face_check))
            return false
        }

        return true
    }
}
