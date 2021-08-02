package net.basicmodel

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.layout_bottom.*
import net.entity.VideoEntity
import net.fragment.FoldersFragment
import net.fragment.HistoryFragment
import net.fragment.VideosFragment
import net.utils.Contanst
import net.utils.TimeUtils
import net.utils.Utils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class MainActivity : AppCompatActivity() {

    var videosFragment: VideosFragment? = null
    var foldersFragment: FoldersFragment? = null
    var historyFragment: HistoryFragment? = null

    var videos: ArrayList<VideoEntity>? = ArrayList()

    val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MMKV.initialize(this)
        requestPermissions()
        showPosition(0)
        initView()
        videos = getAllVideos()
        Contanst.historys = ArrayList()
    }

    private fun initView() {
        video.setOnClickListener { showPosition(0) }
        folder.setOnClickListener { showPosition(1) }
        history.setOnClickListener { showPosition(2) }
    }

    private fun showPosition(position: Int) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        hideAll(ft)
        if (position == 0) {
            videosFragment = fm.findFragmentByTag("videos") as VideosFragment?
            if (videosFragment == null) {
                videosFragment = VideosFragment()
                ft.add(R.id.content, videosFragment!!, "videos")
            } else {
                ft.show(videosFragment!!)
            }
            if (historyFragment != null) {
                ft.remove(historyFragment!!)
            }
        }

        if (position == 1) {
            foldersFragment = fm.findFragmentByTag("folder") as FoldersFragment?
            if (foldersFragment == null) {
                foldersFragment = FoldersFragment()
                ft.add(R.id.content, foldersFragment!!, "folder")
            } else {
                ft.show(foldersFragment!!)
            }
            if (historyFragment != null) {
                ft.remove(historyFragment!!)
            }

        }

        if (position == 2) {
            historyFragment = fm.findFragmentByTag("history") as HistoryFragment?
            if (historyFragment == null) {
                historyFragment = HistoryFragment()
                ft.add(R.id.content, historyFragment!!, "history")
            } else {
                ft.show(historyFragment!!)
            }

        }
        ft.commit()
    }

    private fun hideAll(ft: FragmentTransaction) {
        if (videosFragment != null) {
            ft.hide(videosFragment!!)
        }
        if (foldersFragment != null) {
            ft.hide(foldersFragment!!)
        }
        if (historyFragment != null) {
            ft.hide(historyFragment!!)
        }
    }

    @SuppressLint("Recycle", "SimpleDateFormat")
    private fun getAllVideos(): ArrayList<VideoEntity> {
        var videos: ArrayList<VideoEntity>? = null
        val cursor = this@MainActivity.contentResolver?.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
            null, null
        )
        if (cursor != null) {
            videos = ArrayList()
            while (cursor.moveToNext()) {

                val id = cursor.getInt(
                    cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                )

                val img = getVideoThumbnail(id)

                val name = cursor
                    .getString(
                        cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
                    )
                val size = cursor
                    .getLong(
                        cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                    )
                val url = cursor
                    .getString(
                        cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                    )
                val duration = cursor
                    .getInt(
                        cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                    )
                val time = cursor
                    .getInt(
                        cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
                    )

                val video = VideoEntity()
                video.name = name.toString()
                video.size = Utils.byteToString(size)
                video.url = url.toString()
                video.duration = TimeUtils().times(duration.toLong())
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                video.time = simpleDateFormat.format(time.toLong() * 1000)
                videos.add(video)
            }
            cursor.close()
        }
        return videos!!
    }

    private fun getVideoThumbnail(id: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        options.inDither = false
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(
            this@MainActivity.contentResolver,
            id.toLong(),
            MediaStore.Images.Thumbnails.MICRO_KIND,
            options
        )
        return bitmap
    }


    fun getData(): ArrayList<VideoEntity> {
        return videos!!
    }

    private fun requestPermissions() {
        if (checkPermission(permissions[0]) && checkPermission(permissions[1])) {

        } else {
            ActivityCompat.requestPermissions(this, permissions, 321)
        }
    }

    private fun checkPermission(per: String): Boolean {
        return ContextCompat.checkSelfPermission(this, per) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                } else {
                    Log.i("xxxxxxH", "获取权限成功")
                }
            }
        }
    }
}