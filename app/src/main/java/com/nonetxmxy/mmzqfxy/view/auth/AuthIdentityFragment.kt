package com.nonetxmxy.mmzqfxy.view.auth

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthIdentityBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.viewmodel.AuthUserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AuthIdentityFragment : BaseFragment<FragmentAuthIdentityBinding, AuthUserInfoViewModel>() {

    private val viewModel: AuthUserInfoViewModel by viewModels()
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {

    }

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

    override fun FragmentAuthIdentityBinding.setLayout() {
        mToolbar.setupWithNavController(navController)
        mToolbar.setNavigationIcon(R.mipmap.fanhui)
        includeAuthTitle.image.setImageResource(R.mipmap.jinbi4)

        ivSubirLaFrontal.setOnClickListener {
            uploadIdentityDialog?.show()
        }

        ivSubirLaTrasera.setOnClickListener {
            uploadIdentityDialog?.show()
        }
        ivIdentidad.setOnClickListener {

        }

    }
}