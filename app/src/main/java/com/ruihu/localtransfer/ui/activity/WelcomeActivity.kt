package com.ruihu.localtransfer.ui.activity

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.ruihu.localtransfer.R
import com.ruihu.localtransfer.adapter.WelcomePageAdapter
import com.ruihu.localtransfer.util.SharePreferenceDelegate
import com.ruihu.rh_base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_welcome.*

/**
 */
class WelcomeActivity : BaseActivity() {

    private lateinit var viewList : List<View>

    override fun fullScreen() {
        super.fullScreen()
        hideTitleBar()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initView() {
        super.initView()
        val inflater = LayoutInflater.from(this)
        inflater.apply{
            val firstPage = inflate(R.layout.welcome_pager_first,null)
            val secondPage = inflate(R.layout.welcome_pager_second,null)
            val lastPage = inflate(R.layout.welcome_pager_last,null)
            viewList = listOf(firstPage,secondPage,lastPage)

            val btnLastPagerStart = lastPage.findViewById<Button>(R.id.btn_lastPager_start)
            btnLastPagerStart.setOnClickListener {
                val userSp by SharePreferenceDelegate("user","")
                if (userSp.isNotEmpty()){
                    startActivityAndFinish<MainActivity>()
                }else {
                    startActivityAndFinish<CreateOrModifyUserActivity>()
                }
            }
        }

        val viewPager = WelcomePageAdapter(viewList)
        welcomePager.adapter = viewPager

        radioGroup.check(radioGroup.getChildAt(0).id)

        welcomePager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
               radioGroup.check(radioGroup.getChildAt(position).id)
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })
    }
}