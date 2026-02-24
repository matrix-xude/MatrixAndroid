package com.xxd.myself.navigation

import android.view.MenuItem
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.navigation.navOptions
import com.orhanobut.logger.Logger
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.extend.onClick
import com.xxd.common.util.toast.ToastUtil
import com.xxd.myself.R
import com.xxd.myself.databinding.MyselfActivityNavigationBinding
import com.xxd.myself.navigation.fragment.NavigationFragment1
import com.xxd.myself.navigation.fragment.NavigationFragment2
import com.xxd.myself.navigation.fragment.NavigationFragment3

/**
 *    author : xxd
 *    date   : 2024/3/16
 *    desc   : Navigation初次使用
 */
class NavigationActivity : BaseTitleActivity() {

    private lateinit var viewBinding: MyselfActivityNavigationBinding

    private lateinit var navController: NavController

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = MyselfActivityNavigationBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "Navigation导航测试"
    }

    override fun initView() {
        super.initView()
        initListener()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        navController.graph = navController.createGraph("first", "route1") {

            fragment<NavigationFragment1>("first") {
                Logger.d("目的地 first 触发")
                label = "go first"
            }

            fragment<NavigationFragment2>("second") {
                Logger.d("目的地 second 触发")
                label = "go second"
            }

            fragment<NavigationFragment3>("third") {
                Logger.d("目的地 third 触发")
                label = "go third"
            }
        }

    }

    override fun initData() {
        super.initData()

    }

    private fun initListener() {
        viewBinding.tv1.onClick {
            navController.navigate("first") {
                popUpTo("first"){
                    saveState = true
                }
            }
        }
        viewBinding.tv2.onClick {
            navController.navigate("second") {
                popUpTo("first"){
                    saveState = true
                }
            }
        }
        viewBinding.tv3.onClick {
            navController.navigate("third") {
                popUpTo("third"){
                    saveState = true
                }
            }
        }

        viewBinding.tv4.onClick {
            navController.navigate("first") {
//                popUpTo("first"){
////                    saveState = true
//                }
//                restoreState = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        ToastUtil.showToast("高端")
        Logger.d("按钮被点击了 ${item.itemId}")
//        when (item.itemId) {
//            android.R.id.home -> {
        navController.popBackStack()
        return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
//        if (!navController.popBackStack(navController.currentDestination!!.route!!,true,true)) {
//            finish()
//        }
        if (!navController.popBackStack())
            finish()
    }
}