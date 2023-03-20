package com.nonetxmxy.mmzqfxy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nonetxmxy.mmzqfxy.base.BaseActivity
import com.nonetxmxy.mmzqfxy.databinding.ActivityMainBinding
import com.nonetxmxy.mmzqfxy.tools.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(),
    NavController.OnDestinationChangedListener {

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var navController: NavController

    override fun getViewMode(): MainActivityViewModel = viewModel

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun initPageLayout() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as
                NavHostFragment
        navController = navHostFragment.navController

        binding.nvBottom.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)

//        binding.nvBottom.setOnItemSelectedListener {
//            if (!LocalCache.isLogged) {
//                navController.navigate(ProductListFragmentDirections.actionProductListFragmentToLoginNavigation())
//                false
//            } else {
//                val builder =
//                    NavOptions.Builder()
//                        .setLaunchSingleTop(true)
//                        .setRestoreState(true)
//                        .setPopUpTo(
//                            R.id.productListFragment,
//                            inclusive = false,
//                            saveState = true
//                        )
//                navController.navigate(it.itemId, null, builder.build())
//                navController.currentDestination?.id == it.itemId
//            }
//        }
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
}
