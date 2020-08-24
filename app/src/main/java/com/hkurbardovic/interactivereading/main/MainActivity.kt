package com.hkurbardovic.interactivereading.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.hkurbardovic.interactivereading.R
import com.hkurbardovic.interactivereading.adapters.ViewPagerAdapter
import com.hkurbardovic.interactivereading.app.InteractiveReading
import com.hkurbardovic.interactivereading.base.BaseActivity
import com.hkurbardovic.interactivereading.databinding.ActivityMainBinding
import com.hkurbardovic.interactivereading.managers.FileManager
import com.hkurbardovic.interactivereading.models.Book
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var fileManager: FileManager

    private lateinit var binding: ActivityMainBinding

    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        book = getBook(intent.extras?.getString(BOOK_ID))
        val bookId = book?.id ?: return
        val pages = book?.pages ?: return

        binding.viewPager.adapter = ViewPagerAdapter(bookId, pages.size, supportFragmentManager)
    }

    private fun getBook(bookId: String?): Book? {
        if (bookId == null) onBackPressed()
        val data = InteractiveReading.INSTANCE.getData() ?: return null
        val books = data.books ?: return null

        for (book in books) {
            if (book == null) continue
            if (book.id == bookId) return book
        }

        return null
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    companion object {
        const val BOOK_ID = "com.hkurbardovic.interactivereading.main.BOOK_ID"
    }
}