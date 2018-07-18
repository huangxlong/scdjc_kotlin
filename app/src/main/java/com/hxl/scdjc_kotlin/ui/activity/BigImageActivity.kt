package com.hxl.scdjc_kotlin.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.ui.fragment.PhotosViewFragment
import kotlinx.android.synthetic.main.activty_big_image.*

/**
 * Created by Administrator
 * on 2018/7/5 星期四.
 */
class BigImageActivity : BaseActivity() {
    private lateinit var imgList: List<String>

    override fun loadData() {
    }

    override fun getLayout(): Int {
        return R.layout.activty_big_image
    }

    override fun initView() {
        setWindowStatusBarColor(this, R.color.colorPrimaryDark)
        val img = intent.extras.get("image") as Array<String>
        imgList = img.toList()
        val position = intent.extras.getInt("position")

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                tv_title.text = "查看图片(" + (position + 1) + "/" + imgList.size + ")"
            }

            override fun onPageSelected(position: Int) {
            }
        })
        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getCount(): Int = imgList.size


            override fun getItem(position: Int): Fragment {
                return PhotosViewFragment.newInstance(imgList[position])
            }
        }
        viewPager.currentItem = position
        iv_back.setOnClickListener { onBackPressed() }
    }
}