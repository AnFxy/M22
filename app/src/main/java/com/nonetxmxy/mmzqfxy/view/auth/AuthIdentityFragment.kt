package com.nonetxmxy.mmzqfxy.view.auth

import ai.advance.liveness.lib.Detector
import ai.advance.liveness.lib.GuardianLivenessDetectionSDK
import ai.advance.liveness.lib.LivenessResult
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.nonetxmxy.liveness_androidx.LivenessActivity
import com.nonetxmxy.mmzqfxy.MainActivity
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.customer_view.*
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthIdentityBinding
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.PageType
import com.nonetxmxy.mmzqfxy.model.PhotoType
import com.nonetxmxy.mmzqfxy.tools.FaceCameraUtil
import com.nonetxmxy.mmzqfxy.tools.PreventMultiClickListener
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.AuthIdentityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.random.Random

@AndroidEntryPoint
class AuthIdentityFragment : BaseFragment<FragmentAuthIdentityBinding, AuthIdentityViewModel>() {

    private val viewModel: AuthIdentityViewModel by viewModels()

    private val args: AuthIdentityFragmentArgs by navArgs()

    private lateinit var photoLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>

    private lateinit var faceLauncher: ActivityResultLauncher<Intent>

    private val authDialogSet: RxDialogSet? by lazy {
        context?.let {
            val dialog = RxDialogSet.provideDialog(it, R.layout.dia_auth)
            dialog.setViewState<ImageView>(R.id.iv_close) {
                setLimitClickListener {
                    dialog.dismiss()
                }
            }.setViewState<TextView>(R.id.tv_continue) {
                setLimitClickListener {
                    dialog.dismiss()
                }
            }.setViewState<TextView>(R.id.tv_abandonar) {
                setLimitClickListener {
                    navController.popBackStack()
                    dialog.dismiss()
                }
            }
            dialog
        }
    }

    override fun getViewMode() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater, parent: ViewGroup?
    ) = FragmentAuthIdentityBinding.inflate(
        inflater, parent, false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isHiddenStatus = true

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
                        PhotoType.FACE -> {
                            FaceCameraUtil(context) {
                                // 当图片链接获取到后，上传面部识别
                                LocalCache.facePhoto = it
                                viewModel.submitFaceWithCamera()
                            }.setImageData()
                        }
                        else -> {}
                    }
                }
            }

        faceLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    if (LivenessResult.isSuccess()) {
                        val bitmap = LivenessResult.getLivenessBitmap()
                        LocalCache.faceLivenessID = LivenessResult.getLivenessId()
                        // 将图片转为 base 64, 然后上传到文件系统换取 url
                        lifecycleScope.launchWhenStarted {
                            val base64Str = withContext(Dispatchers.IO) {
                                HandlePhoto.compressBitmap2Base64(
                                    HandlePhoto.zoomImage(bitmap), 250L
                                )
                            }
                            viewModel.uploadFacePic(base64Str)
                        }
                    } else {
                        ToastUtils.showShort(LivenessResult.getErrorMsg())
                        Timber.e(LivenessResult.getErrorMsg())
                    }
                }
            }
    }

    override fun FragmentAuthIdentityBinding.setLayout() {
        mToolbar.setNavigationOnClickListener(object : PreventMultiClickListener() {
            override fun onSafeClick() {
                activity?.onBackPressed()
            }
        })
        mToolbar.setNavigationIcon(R.mipmap.fanhui)
        includeAuthTitle.image.setImageResource(R.mipmap.jinbi4)

        activity?.let {
            binding.ivTop.setActivity(it)
            binding.ivBehind.setActivity(it)
        }

        activity?.let {
            (it as MainActivity).specialOnBackPressed = {
                if (LocalCache.fourAuth()) {
                    navController.popBackStack()
                } else {
                    authDialogSet?.show()
                }
            }
        }

        binding.mRefresh.setOnRefreshListener {
            viewModel.getIdentifyPageData()
        }

        binding.ivTop.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.photoType.value = PhotoType.TOP_PHOTO
            }
        })

        binding.ivTop.setStartCameraListener(object : IStartCameraListener {
            override fun onStartCamera() {
                cameraLauncher.launch(HandlePhoto.createImageUri(context), )
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

        // 人脸识别
        binding.ivFaceCheck.setLimitClickListener {
//            cameraLauncher.launch(HandlePhoto.createImageUri(context))
//            viewModel.photoType.value = PhotoType.FACE
//            viewModel.updateFaceTime()
            if (LocalCache.faceAccessKey.isEmpty() || LocalCache.faceSecretKey.isEmpty()) {
                viewModel.getFaceConfig()
            } else {
                val faceActions = listOf(
                    Detector.DetectionType.POS_YAW,
                    Detector.DetectionType.MOUTH,
                    Detector.DetectionType.BLINK
                )
                GuardianLivenessDetectionSDK.setActionSequence(true, faceActions[Random.nextInt(0, 3)])
                faceLauncher.launch(Intent(context, LivenessActivity::class.java))
            }
        }
    }

    override fun setObserver() {
        binding.ivTop.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                LocalCache.idCardTop = url
                // 进行 OCR识别
                viewModel.startOCRFlow(url, flag)
            }
        })

        binding.ivBehind.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                LocalCache.idCardBehind = url
                // 进行 OCR识别
                viewModel.startOCRFlow(url, flag)
            }
        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagerEventFlow.collect {
                when (it) {
                    AuthPagerEvent.UpdatePageView -> updatePage()
                    AuthPagerEvent.Finish -> navController.popBackStack()
                    AuthPagerEvent.GoNextPage -> {
                        if (args.isJustBack) navController.popBackStack() else viewModel.checkGoWhichVerificationPage()
                    }
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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.setFacePic.collect {
                Glide.with(this@AuthIdentityFragment).load(LocalCache.photoInfo).into(binding.ivFaceCheck)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.startFace.collect {
                val faceActions = listOf(
                    Detector.DetectionType.POS_YAW,
                    Detector.DetectionType.MOUTH,
                    Detector.DetectionType.BLINK
                )
                GuardianLivenessDetectionSDK.setActionSequence(true, faceActions[Random.nextInt(0, 3)])
                faceLauncher.launch(Intent(context, LivenessActivity::class.java))
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
        binding.inputRfcNumber.inputContent = viewModel.pagerData.DEyLxCETnd ?: ""
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

//        if (LocalCache.faceCredit == 0) {
//            ToastUtils.showShort(getString(R.string.please_face_check))
//            return false
//        }

        return true
    }
}
