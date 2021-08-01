package net.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.layout_fragment.*
import kotlinx.android.synthetic.main.layout_fragment_folder.*
import net.adapter.FolderAdapter
import net.basicmodel.FolderActivity
import net.basicmodel.MainActivity
import net.basicmodel.R
import net.entity.FolderEntity
import net.entity.VideoEntity
import net.interFace.OnItemClickListener

@Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
class FoldersFragment : Fragment(), OnItemClickListener {

    var videos: ArrayList<VideoEntity> = ArrayList()
    var folders: ArrayList<FolderEntity> = ArrayList()
    var folderName: HashSet<String> = HashSet()
    var data: HashMap<String, ArrayList<VideoEntity>> = HashMap()
    var adapter: FolderAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_folder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videos = (activity as MainActivity).getData()
        getFolderData(videos)
        progressBarF.visibility = View.GONE
        if (folders.size > 0) {
            emptyViewF.visibility = View.GONE
            recyclerF.visibility = View.VISIBLE
            adapter = FolderAdapter(folders, activity)
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerF.layoutManager = layoutManager
            recyclerF.adapter = adapter
            adapter!!.setListener(this)
        } else {
            emptyViewF.visibility = View.VISIBLE
            recyclerF.visibility = View.GONE
        }
    }

    private fun getFolderData(videoList: ArrayList<VideoEntity>) {
        for (item in videoList) {
            val url = item.url.split("/")
            val name = url[url.size - 2]
            folderName.add(name)
        }

        for (item in folderName) {
            val video: ArrayList<VideoEntity> = ArrayList()
            for (entity in videoList) {
                if (entity.url.contains(item)) {
                    video.add(entity)
                }
            }
            data[item] = video
        }

        data.forEach {
            val entity = FolderEntity()
            entity.name = it.key
            entity.num = it.value.size
            folders.add(entity)
        }
    }

    override fun onItemClick(view: View, position: Int, type: Int) {
        val name = (adapter?.data?.get(position) as FolderEntity).name
        val videoData: ArrayList<VideoEntity> = data[name] as ArrayList<VideoEntity>
        val intent = Intent(activity, FolderActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("data", videoData)
        intent.putExtra("bundle", bundle)
        activity?.startActivity(intent)
    }


}