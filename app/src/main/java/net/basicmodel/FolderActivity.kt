package net.basicmodel

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.layout_fragment.*
import net.adapter.VideoAdapter
import net.entity.VideoEntity
import net.interFace.OnItemClickListener
import net.utils.Contanst
import net.utils.Utils

@Suppress("UNCHECKED_CAST")
class FolderActivity : AppCompatActivity(), OnItemClickListener {
    var adapter: VideoAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragment)
        val intent = intent
        val bundle = intent.getBundleExtra("bundle")
        val data = bundle?.get("data") as ArrayList<*>
        progressBar.visibility = View.GONE
        if (data.size > 0) {
            emptyView.visibility = View.GONE
            recycler.visibility = View.VISIBLE
            adapter =
                VideoAdapter(data as ArrayList<VideoEntity>?, this, this, Contanst.TYPE_VIDEOS)
            adapter!!.setListener(this)
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recycler.layoutManager = layoutManager
            recycler.adapter = adapter
        } else {
            emptyView.visibility = View.VISIBLE
            recycler.visibility = View.GONE
        }
    }

    override fun onItemClick(view: View, position: Int, type: Int) {
        val entity = adapter!!.data[position]
        val url = entity.url
        val name = entity.name
        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("name", name)
        if (!Contanst.historys.contains(entity)) {
            Contanst.historys.add(entity)
        }
        Utils.saveKey(name)
        MMKV.defaultMMKV()?.encode(name,entity)
        this.startActivity(intent)
    }
}