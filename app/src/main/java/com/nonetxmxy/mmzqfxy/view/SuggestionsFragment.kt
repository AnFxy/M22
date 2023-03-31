package com.nonetxmxy.mmzqfxy.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.adjust.sdk.Util
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.customer_view.IDPhotoView
import com.nonetxmxy.mmzqfxy.customer_view.ILoadImageListener
import com.nonetxmxy.mmzqfxy.customer_view.IStartOpenAlbumListener
import com.nonetxmxy.mmzqfxy.databinding.FragmentSuggestionBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.tools.setVisibleWithUnVisual
import com.nonetxmxy.mmzqfxy.viewmodel.SuggestionFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SuggestionsFragment : BaseFragment<FragmentSuggestionBinding, SuggestionFragViewModel>() {

    private val viewModel: SuggestionFragViewModel by viewModels()

    override fun getViewMode(): SuggestionFragViewModel = viewModel

    private lateinit var photoLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                when(viewModel.currentIndex) {
                    0 -> binding.ivImagUpload1.setImageData(IDPhotoView.ALBUM_SUGGESTION, it)
                    1 -> binding.ivImagUpload2.setImageData(IDPhotoView.ALBUM_SUGGESTION, it)
                    2 -> binding.ivImagUpload3.setImageData(IDPhotoView.ALBUM_SUGGESTION, it)
                    3 -> binding.ivImagUpload4.setImageData(IDPhotoView.ALBUM_SUGGESTION, it)
                    4 -> binding.ivImagUpload5.setImageData(IDPhotoView.ALBUM_SUGGESTION, it)
                    5 -> binding.ivImagUpload6.setImageData(IDPhotoView.ALBUM_SUGGESTION, it)
                    6 -> binding.ivImagUpload7.setImageData(IDPhotoView.ALBUM_SUGGESTION, it)
                    7 -> binding.ivImagUpload8.setImageData(IDPhotoView.ALBUM_SUGGESTION, it)
                }
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentSuggestionBinding =
        FragmentSuggestionBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        isHiddenStatus = true

        binding.editSuggesText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val strLength = p0?.toString()?.length ?: 0
                binding.tvAccount.text = "$strLength/200"
                binding.tvUpload.isEnabled = strLength > 0

                binding.tvUpload.setTextColor(
                    if (strLength > 0) {
                        Utils.getApp().getColor(R.color.gray_532e00)
                    } else {
                        Utils.getApp().getColor(R.color.gray_666666)
                    }
                )

            }
        })

        binding.tvUpload.setLimitClickListener {
            val strSuggestText = binding.editSuggesText.text.toString()
            if (strSuggestText.isNotEmpty()) {
                viewModel.uploadSuggestion(strSuggestText)
            }
        }

        binding.ivImagUpload1.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.currentIndex = 0
            }
        })

        binding.ivImagUpload2.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.currentIndex = 1
            }
        })

        binding.ivImagUpload3.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.currentIndex = 2
            }
        })
        binding.ivImagUpload4.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.currentIndex = 3
            }
        })
        binding.ivImagUpload5.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.currentIndex = 4
            }
        })
        binding.ivImagUpload6.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.currentIndex = 5
            }
        })
        binding.ivImagUpload7.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.currentIndex = 6
            }
        })
        binding.ivImagUpload8.setStartOpenAlbumListener(object : IStartOpenAlbumListener {
            override fun onStartAlbum() {
                photoLauncher.launch("image/*")
                viewModel.currentIndex = 7
            }
        })

        binding.ivImagUpload1.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                binding.ivImagUpload1.setImageUrl(url)
                viewModel.picLinkList.add(url)
                binding.ivImagUpload1.isSelect = false

                updateImagesVisual(0)
            }
        })

        binding.ivImagUpload2.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                binding.ivImagUpload2.setImageUrl(url)
                viewModel.picLinkList.add(url)
                binding.ivImagUpload2.isSelect = false

                updateImagesVisual(1)
            }
        })
        binding.ivImagUpload3.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                binding.ivImagUpload3.setImageUrl(url)
                viewModel.picLinkList.add(url)
                binding.ivImagUpload3.isSelect = false

                updateImagesVisual(2)
            }
        })
        binding.ivImagUpload4.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                binding.ivImagUpload4.setImageUrl(url)
                viewModel.picLinkList.add(url)
                binding.ivImagUpload4.isSelect = false

                updateImagesVisual(3)
            }
        })
        binding.ivImagUpload5.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                binding.ivImagUpload5.setImageUrl(url)
                viewModel.picLinkList.add(url)
                binding.ivImagUpload5.isSelect = false

                updateImagesVisual(4)
            }
        })
        binding.ivImagUpload6.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                binding.ivImagUpload6.setImageUrl(url)
                viewModel.picLinkList.add(url)
                binding.ivImagUpload6.isSelect = false

                updateImagesVisual(5)
            }
        })
        binding.ivImagUpload7.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                binding.ivImagUpload7.setImageUrl(url)
                viewModel.picLinkList.add(url)
                binding.ivImagUpload7.isSelect = false

                updateImagesVisual(6)
            }
        })
        binding.ivImagUpload8.setImageUpLoadListener(object : ILoadImageListener {
            override fun onPhotoGot(url: String, flag: Int) {
                binding.ivImagUpload8.setImageUrl(url)
                viewModel.picLinkList.add(url)
                binding.ivImagUpload8.isSelect = false

                updateImagesVisual(7)
            }
        })
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.finishAndClosePage.collect {
                navController.popBackStack()
                ToastUtils.showShort("Operación exitosa")
            }
        }
    }

    private fun updateImagesVisual(index: Int) {
        binding.ivImagUpload2.setVisibleWithUnVisual(index >= 0)
        binding.ivImagUpload3.setVisibleWithUnVisual(index >= 1)
        binding.ivImagUpload4.setVisibleWithUnVisual(index >= 2)
        binding.ivImagUpload5.setVisibleWithUnVisual(index >= 3)
        binding.ivImagUpload6.setVisibleWithUnVisual(index >= 4)
        binding.ivImagUpload7.setVisibleWithUnVisual(index >= 5)
        binding.ivImagUpload8.setVisibleWithUnVisual(index >= 6)

        binding.containerPicsTwo.setVisible(index >= 3)

        binding.tvAccountPic.text = "${viewModel.picLinkList.size}/8"
    }
}