package com.ellison.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ellison.demo.capture.ScreenShotActivity
import com.ellison.demo.databinding.ActivityListBinding
import com.ellison.demo.path.PathActivity
import com.ellison.demo.personalization.GenderActivity
import com.ellison.demo.recognition.client.RecognitionActivity
import com.ellison.demo.share.ShareSheetActivity
import com.ellison.demo.textView.TextViewActivity
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private val mMap: LinkedHashMap<String, Class<out Activity>> = linkedMapOf(
        "语音识别" to RecognitionActivity::class.java,
        "Android 14新API：直接监听截屏操作" to ScreenShotActivity::class.java,
        "Android 14新功能HighLights：快速实现文本高亮" to TextViewActivity::class.java,
        "Android 14新特性：语法性别" to GenderActivity::class.java,
        "Android 14新特性：自定义分享界面" to ShareSheetActivity::class.java,
        "Android 14新特性：Path的操作记录与数据" to PathActivity::class.java,
    )

    val keys = mMap.keys.toList()

    val targets = mMap.values.toList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            object : CommonAdapter<String>(this, android.R.layout.simple_list_item_1, keys) {

                override fun convert(holder: ViewHolder, s: String, position: Int) {
                    holder.setText(android.R.id.text1, s)
                    holder.setOnClickListener(android.R.id.text1) {
                        startActivity(Intent(this@ListActivity, targets[position]))
                    }
                }
            }
    }
}