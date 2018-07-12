package com.hxl.scdjc_kotlin.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.bean.base.RspDto
import com.hxl.scdjc_kotlin.http.RetrofitManager

/**
 * Created by Administrator
 * on 2018/7/4 星期三.
 */
class ColumnAdapter(context: Context, children: List<RspDto.Column>) : RecyclerView.Adapter<ColumnAdapter.ViewHolder>() {
    private var mContext = context
    private var mChildren = children
    private var mOnItemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int = mChildren.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_tab, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = mChildren[position].name
        Glide.with(mContext).load(RetrofitManager.baseUrl + mChildren[position].imgPath).into(holder.ivPhoto)
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener {
                mOnItemClickListener!!.onItemClick(holder.itemView, holder.layoutPosition)
            }
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPhoto = itemView.findViewById<ImageView>(R.id.iv_photo)!!
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)!!
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }
}