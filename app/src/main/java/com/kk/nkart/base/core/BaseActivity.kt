package com.kk.nkart.base.core

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.util.Pair
import com.kk.nkart.base.core.BaseViewModel
import com.kk.nkart.ui.common.ProgressDialog
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

open class BaseActivity : AppCompatActivity() {

    val progressDialog = ProgressDialog()
    private val publishSubjectCounter = AtomicInteger()
    private val publishSubjectMap: HashMap<Int, PublishSubject<Pair<Int, Intent>>> = HashMap()
    var textCartItemCount: TextView? = null

    fun initToolbar(
        toolbar: Toolbar,
        navigationUp: Boolean,
        showTitle: Boolean,
        title: String?
    ) {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(navigationUp)
            supportActionBar?.setDisplayShowTitleEnabled(showTitle)
            if (showTitle && title != null) {
                supportActionBar?.title = title
            }
        }
    }

    fun setupBedge(count: Int) {
        if (count == 0) {
            textCartItemCount?.visibility = View.GONE
        } else {
            textCartItemCount?.visibility = View.VISIBLE
        }
        textCartItemCount?.text = count.toString()
    }

    open fun startActivityForResult(intent: Intent?): Observable<Pair<Int, Intent>>? {
        val key: Int = publishSubjectCounter.getAndIncrement()
        val subject = PublishSubject.create<Pair<Int, Intent>>()
        publishSubjectMap.put(key, subject)
        startActivityForResult(intent, key)
        return subject.hide()
    }

    fun registerGoBack(baseViewModel: BaseViewModel) {
        baseViewModel.backEvent.observe(this, androidx.lifecycle.Observer { finish() })
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}