package com.hkurbardovic.interactivereading.books

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.hkurbardovic.interactivereading.R
import com.hkurbardovic.interactivereading.adapters.BooksAdapter
import com.hkurbardovic.interactivereading.app.InteractiveReading
import com.hkurbardovic.interactivereading.base.BaseActivity
import com.hkurbardovic.interactivereading.databinding.ActivityBooksBinding
import com.hkurbardovic.interactivereading.main.MainActivity
import com.hkurbardovic.interactivereading.managers.FileManager
import com.hkurbardovic.interactivereading.models.Book
import javax.inject.Inject

class BooksActivity : BaseActivity(), BooksAdapter.OnBookClickListener {

    @Inject
    lateinit var fileManager: FileManager

    private lateinit var binding: ActivityBooksBinding

    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_books)

        val a  = null as Context

        val booksAdapter = BooksAdapter()
        val books = InteractiveReading.INSTANCE.getData()?.books ?: return

        booksAdapter.submitList(books)
        booksAdapter.setOnBookClickListener(this)

        binding.booksRecyclerView.adapter = booksAdapter
    }

    override fun onBookClick(book: Book) {
        this.book = book
        requestWriteExternalStoragePermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    downloadBookAssets()
                }
            }
        }
    }

    private fun requestWriteExternalStoragePermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE
                )
            }
        } else {
            downloadBookAssets()
        }
    }

    private fun downloadBookAssets() {
        val bookId = book?.id ?: return
        val bookPages = book?.pages ?: return
        val storage = Firebase.storage(STORAGE_URL)

        for (page in bookPages) {
            val imageStorageLocation = page?.imageStorageLocation ?: continue
            val audioStorageLocation = page.audioStorageLocation ?: continue
            val localImageStorageLocation = page.localImageStorageLocation ?: continue
            val localAudioStorageLocation = page.localAudioStorageLocation ?: continue
            val imageReference: StorageReference = storage.reference.child(imageStorageLocation)
            val audioReference: StorageReference = storage.reference.child(audioStorageLocation)

            val imageFile = fileManager.getFile(this, localImageStorageLocation)
            val audioFile = fileManager.getFile(this, localAudioStorageLocation)

            if (!imageFile.exists()) imageReference.getFile(imageFile)
            if (!audioFile.exists()) audioReference.getFile(audioFile)

            val clickableObjects = page.clickableObjects ?: continue
            for (clickableObject in clickableObjects) {
                val clickableObjectAudioStorageLocation =
                    clickableObject?.audioStorageLocation ?: continue
                val clickableObjectAudioReference: StorageReference =
                    storage.reference.child(clickableObjectAudioStorageLocation)

                val clickableObjectAudioFile =
                    fileManager.getFile(this, "${clickableObject.id}.mp3")

                if (clickableObjectAudioFile.exists()) continue

                clickableObjectAudioReference.getFile(clickableObjectAudioFile)
            }
        }

        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra(MainActivity.BOOK_ID, bookId)
        })
    }

    companion object {
        const val STORAGE_URL = "gs://interactive-reading.appspot.com"
        const val MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE = 999
    }
}