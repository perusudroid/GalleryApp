package com.perusudroid.galleryapp.view

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.perusudroid.core.networking.Outcome
import com.perusudroid.galleryapp.R
import com.perusudroid.galleryapp.adapter.GalleryAdapter
import com.perusudroid.galleryapp.common.GalleryDH
import com.perusudroid.galleryapp.common.ViewSwitcher
import com.perusudroid.galleryapp.viewmodel.GalleryViewModel
import com.perusudroid.galleryapp.viewmodel.GalleryViewModelFactory
import javax.inject.Inject


class GalleryActivity : AppCompatActivity() {


    private lateinit var tvTitle: TextView
    private lateinit var tvCollapsingTitle: TextView
    private lateinit var ivBack: ImageView
    private lateinit var myTool: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var vsSwitcher: ViewSwitcher

    private val component by lazy {
        GalleryDH.galleryComponent()
    }


    @Inject
    lateinit var adapter: GalleryAdapter

    @Inject
    lateinit var viewModelFactory: GalleryViewModelFactory

    private val viewModel: GalleryViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GalleryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransWindow(R.color.gradient_start)
        setContentView(R.layout.activity_gallery)
        bindViews()
        setAssets()
        setToolbarProperties()
        component.inject(this)
        checkPerAndProceed()


    }

    private fun setAssets() {
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.isNestedScrollingEnabled = true
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun checkPerAndProceed() {

        if (ContextCompat.checkSelfPermission(applicationContext,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) run {
            ActivityCompat.requestPermissions(this@GalleryActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                    100)
        } else {
            viewModel.getGalleryPics()
            initObserver()
        }
    }

    private fun bindViews() {
        tvTitle = findViewById(R.id.tvTitle)
        tvCollapsingTitle = findViewById(R.id.tvTitleX)
        myTool = findViewById(R.id.myTool)
        ivBack = myTool.findViewById(R.id.ivBack)
        recyclerView = findViewById(R.id.recyclerView)
        vsSwitcher = findViewById(R.id.vsContent)
    }


    protected fun setTransWindow(color: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, color)
        }
    }


    private fun setToolbarProperties() {
        val appBarLayout = findViewById<AppBarLayout>(R.id.appbar)
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    tvTitle.text = tvCollapsingTitle.text
                    isShow = true
                } else if (isShow) {
                    tvTitle.text = ""
                    isShow = false
                }
            }
        })
    }


    internal var onSpanSizeLookup: GridLayoutManager.SpanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (adapter.getItemViewType(position) == 1) 1 else 3
        }
    }

    private fun initObserver() {
        viewModel.galleryOutCome.observe(this,
                Observer<Outcome<ArrayList<Any>>>(function = fun(outcome: Outcome<ArrayList<Any>>?) {
                    when (outcome) {
                        is Outcome.Progress -> {
                            Toast.makeText(this, "Progress", Toast.LENGTH_LONG).show()
                        }
                        is Outcome.Success -> {

                            val data = outcome.data

                            adapter.setGalleryListData(data)

                            val manager = GridLayoutManager(this, 3)
                            manager.spanSizeLookup = onSpanSizeLookup
                            recyclerView.layoutManager = manager
                            recyclerView.adapter = adapter
                            Log.d("GalleryX","Adapter Set")
                            vsSwitcher.setChildVisible()
                            Log.d("GalleryX","Child Visibility Set")
                        }

                        is Outcome.Failure -> {
                            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                        }

                    }
                }
                ))
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            100 -> {
                for (grantResult in grantResults) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        viewModel.getGalleryPics()
                        initObserver()
                    } else {
                        Toast.makeText(this@GalleryActivity, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
