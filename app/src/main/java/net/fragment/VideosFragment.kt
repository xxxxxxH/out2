package net.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.layout_fragment.*
import net.adapter.VideoAdapter
import net.basicmodel.MainActivity
import net.basicmodel.R
import net.basicmodel.VideoActivity
import net.entity.VideoEntity
import net.interFace.OnItemClickListener
import java.util.*
import kotlin.collections.ArrayList
import net.utils.Contanst;


class VideosFragment : Fragment(), OnItemClickListener {

    var videos: ArrayList<VideoEntity>? = ArrayList()
    var adapter: VideoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videos = (activity as MainActivity).getData()
        if (videos!!.size > 0) {
            progressBar.visibility = View.GONE
            emptyView.visibility = View.GONE
            recycler.visibility = View.VISIBLE
            adapter = VideoAdapter(videos, activity, activity, Contanst.TYPE_VIDEOS)
            adapter!!.setListener(this@VideosFragment)
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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
        val intent = Intent(activity, VideoActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("name", name)
        if (!Contanst.historys.contains(entity)) {
            Contanst.historys.add(entity)
        }
        activity?.startActivity(intent)
    }
}