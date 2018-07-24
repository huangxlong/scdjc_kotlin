package com.hxl.scdjc_kotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.bean.ArticleBean
import com.hxl.scdjc_kotlin.bean.base.RspDto
import com.hxl.scdjc_kotlin.db.SearchHistoryDb
import com.hxl.scdjc_kotlin.mvp.contract.SearchContract
import com.hxl.scdjc_kotlin.mvp.presenter.SearchPresenter
import com.hxl.scdjc_kotlin.ui.adapter.NewsAdapter
import com.hxl.scdjc_kotlin.ui.adapter.SearchHistoryAdapter
import com.hxl.scdjc_kotlin.util.ToastUtil
import com.hxl.scdjc_kotlin.view.MyLoadMoreView
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by Administrator
 * on 2018/7/16 星期一.
 */
class SearchActivity : BaseActivity(), SearchContract.View, View.OnClickListener {
    private val mPresenter by lazy { SearchPresenter() }
    private val newsCustomAdapter by lazy { NewsAdapter(R.layout.item_news_custom, newsList, false) }
    private val searchHistoryAdapter by lazy { SearchHistoryAdapter(keyList) }
    private var searchDb: SearchHistoryDb? = null
    private var newsList = mutableListOf<RspDto.Article>()
    private var keyList = mutableListOf<String>()
    private var isLoadMore = false
    private var isRefresh = false
    private var currentPage: Int = 1
    private var keyWords: String? = null

    override fun getLayout(): Int = R.layout.activity_search

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        searchDb = SearchHistoryDb(this)
        keyList.addAll(searchDb!!.getAllRecords())
        initRecycler()
        tv_search.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        tv_clear.setOnClickListener(this)
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            isRefresh = true
            currentPage = 1
            newsCustomAdapter.setEnableLoadMore(true)
            mPresenter.search(keyWords!!, currentPage)
        }
        et_search.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //隐藏软键盘
                val imm = v.context
                        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm.isActive) {
                    imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
                }
                search()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val key = et_search.text.toString().trim()
                if (swipeRefresh.visibility == View.VISIBLE) {
                    if (key.isEmpty()) {
                        changeVisibleOnHistory(true)
                    } else {
                        changeVisibleOnHistory(false)
                    }
                } else {
                    notifyHistory(true, key)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }


    override fun loadData() {}

    override fun showLoading() {
        if (!isRefresh && !isLoadMore) {
            multipleStatusView.showLoading()
        }
    }

    override fun dismissLoading() {
        if (isRefresh) {
            swipeRefresh.isRefreshing = false
        } else if (!isRefresh && !isLoadMore) {
            multipleStatusView.showContent()
        }
    }

    override fun setSearchResult(article: ArticleBean) {
        if (article.articleList == null || article.articleList!!.isEmpty()) {
            if (isLoadMore) {
                newsCustomAdapter.loadMoreEnd()
            } else {
                multipleStatusView.showEmpty()
            }
        } else {
            if (isLoadMore) {
                newsCustomAdapter.loadMoreComplete()
            }
            if (isRefresh) {
                newsList.clear()
            }
            newsList.addAll(article.articleList!!)
        }
        notifyRecycler()
        isLoadMore = false
        isRefresh = false
    }

    private fun notifyRecycler() {
        if (isRefresh) {
            newsCustomAdapter.setNewData(newsList)
        }
        newsCustomAdapter.notifyDataSetChanged()
    }

    private fun notifyHistory(isFilter: Boolean, key: String) {
        keyList.clear()
        keyList.addAll(searchDb!!.getAllRecords())
        if (isFilter) {
            if (!key.isEmpty()) {
                val filterList = keyList.filter { it.startsWith(key) }
                searchHistoryAdapter.setNewData(filterList)
            } else {
                searchHistoryAdapter.setNewData(keyList)
            }
        } else {
            searchHistoryAdapter.setNewData(keyList)
        }
        searchHistoryAdapter.notifyDataSetChanged()
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        if (isLoadMore) {
            isLoadMore = false
            newsCustomAdapter.loadMoreFail()
        }
        if (isRefresh) {
            isRefresh = false
            swipeRefresh.isRefreshing = false
        }
        ToastUtil.show(this@SearchActivity, errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    private fun initRecycler() {
        val newsManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        news_recycler.layoutManager = newsManager
        news_recycler.adapter = newsCustomAdapter

        val searchManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        search_recycler.layoutManager = searchManager
        search_recycler.adapter = searchHistoryAdapter

        newsCustomAdapter.setOnItemClickListener { _, _, position ->
            intent = Intent(this@SearchActivity, NewsDetailActivity::class.java)
            intent.putExtra(AppConstant.ARTICLE_ID, newsList[position].id)
            intent.putExtra(AppConstant.LINK_TITLE, newsList[position].title)
            startActivity(intent)
        }

        searchHistoryAdapter.setOnItemClickListener { _, _, position ->
            currentPage = 1
            keyWords = keyList[position]
            et_search.setText(keyList[position])
            et_search.setSelection(keyList[position].length)
            closeKeyBord(et_search, this@SearchActivity)
            changeVisibleOnHistory(false)
            mPresenter.search(keyWords!!, currentPage)
        }

        searchHistoryAdapter.setOnItemChildClickListener { _, _, position ->
            searchDb!!.deleteByRecords(keyList[position])
            notifyHistory(false, "")
        }

        newsCustomAdapter.setLoadMoreView(MyLoadMoreView())
        newsCustomAdapter.setOnLoadMoreListener {
            currentPage++
            isLoadMore = true
            mPresenter.search(keyWords!!, currentPage)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_search -> search()
            R.id.iv_back -> onBackPressed()
            R.id.tv_clear -> {
                searchDb!!.deleteAllRecords()
                notifyHistory(false, "")
            }
        }
    }

    private fun search() {
        keyWords = et_search.text.toString().trim()
        if (keyWords.isNullOrEmpty()) return
        searchDb!!.addRecords(keyWords!!)
        changeVisibleOnHistory(false)
        closeKeyBord(et_search, this@SearchActivity)
        notifyHistory(false, "")
        mPresenter.search(keyWords!!, currentPage)
    }

    private fun changeVisibleOnHistory(visible: Boolean) {
        layout_history.visibility = if (visible) View.VISIBLE else View.GONE
        swipeRefresh.visibility = if (visible) View.GONE else View.VISIBLE
    }
}