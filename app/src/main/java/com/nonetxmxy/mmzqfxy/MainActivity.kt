package com.nonetxmxy.mmzqfxy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nonetxmxy.mmzqfxy.base.BaseActivity
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.databinding.ActivityMainBinding
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.view.ProductListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(),
    NavController.OnDestinationChangedListener {

    val viewModel: MainActivityViewModel by viewModels()

    private lateinit var navController: NavController

    var specialOnBackPressed: () -> Unit = {
        super.onBackPressed()
    }

    companion object {
        val SPECIAL_PAGE_LIST = listOf(
            "AuthUserInfoFragment",
            "AuthUserWorkFragment",
            "AuthContactPersonFragment",
            "AuthIdentityFragment",
            "UnderReviewFragment",
            "UnderReviewFragmentRepay",
            "UnderReviewFragmentPro",
            "PayCodeFragment"
        )
    }

    override fun getViewMode(): MainActivityViewModel = viewModel

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun initPageLayout() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as
                NavHostFragment
        navController = navHostFragment.navController

        binding.nvBottom.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)

        binding.nvBottom.setOnItemSelectedListener {
            if (!LocalCache.isLogged && it.itemId != R.id.product_list_navigation) {
                navController.navigate(ProductListFragmentDirections.actionProductListFragmentToLoginNavigation())
                false
            } else {
                val builder =
                    NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setRestoreState(true)
                        .setPopUpTo(
                            R.id.productListFragment,
                            inclusive = false,
                            saveState = true
                        )
                navController.navigate(it.itemId, null, builder.build())
                navController.currentDestination?.id == it.itemId
            }
        }
    }

    override fun onBackPressed() {
        val curLabelName = navController.currentDestination?.label ?: ""
        if (curLabelName in SPECIAL_PAGE_LIST) {
            specialOnBackPressed.invoke()
        } else {
            super.onBackPressed()
        }
    }

    override fun setListener() {
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val curLabelName = destination.label

        binding.nvBottom.setVisible(
            curLabelName in listOf("ProductListFragment", "OrderListFragment", "MyFragment")
        )
    }

    override fun onDestroy() {
        requestDataLoadDialog.dismiss()

        super.onDestroy()
    }
}
