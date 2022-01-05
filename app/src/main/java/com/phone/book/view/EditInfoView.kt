package com.phone.book.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.phone.book.R
import com.phone.book.common.utils.LogUtil

class EditInfoView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    private var edit_info_item_name: TextView? = null
    var edit_info_box_value: EditText? = null
    var name: String = ""
        set(value) {
            field = value
            edit_info_item_name?.text = value
        }

    var desc: String = ""
        set(value) {
            field = value
            edit_info_box_value?.setText(value)
        }
        get() = edit_info_box_value?.text.toString().trim()


    //R.style.CommonSettingItemView
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    init {
        LayoutInflater.from(context).inflate(R.layout.edit_info_item_layout, this);
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditInfoView, defStyleAttr, defStyleRes)
            name = typedArray.getString(R.styleable.EditInfoView_editInfoName) ?: ""
            desc = typedArray.getString(R.styleable.EditInfoView_editInfoDesc) ?: ""
//            val maxEms = typedArray.getInt(R.styleable.EditInfoView_editInfoMaxEms, 50)
            val hint = typedArray.getString(R.styleable.EditInfoView_editInfoHint)
            edit_info_item_name = findViewById<TextView>(R.id.edit_info_item_name)
            edit_info_box_value = findViewById<EditText>(R.id.edit_info_box_item_value)
            edit_info_item_name?.text = name
            edit_info_box_value?.hint = hint
//            if (maxEms != 50) {
//                LogUtil.d("EditInfoView maxEms =$maxEms")
//                edit_info_box_value?.maxEms = maxEms
//            }
            edit_info_box_value?.setText(desc)
            typedArray.recycle()
        }
    }

}