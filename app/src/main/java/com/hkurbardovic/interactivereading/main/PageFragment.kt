package com.hkurbardovic.interactivereading.main

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.hkurbardovic.interactivereading.app.InteractiveReading
import com.hkurbardovic.interactivereading.custom.WordClickableSpannable
import com.hkurbardovic.interactivereading.databinding.FragmentPageBinding
import com.hkurbardovic.interactivereading.handlers.ClickHandler
import com.hkurbardovic.interactivereading.handlers.commands.*
import com.hkurbardovic.interactivereading.main.viewmodels.MainViewModel
import com.hkurbardovic.interactivereading.main.viewmodels.MainViewModelFactory
import com.hkurbardovic.interactivereading.managers.FileManager
import com.hkurbardovic.interactivereading.managers.MediaManager
import com.hkurbardovic.interactivereading.managers.PolygonManager
import com.hkurbardovic.interactivereading.managers.ToastManager
import com.hkurbardovic.interactivereading.polygon.Polygon
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PageFragment : DaggerFragment(), View.OnTouchListener,
    WordClickableSpannable.OnClickListener, MediaManager.OnCompletionListener {

    private lateinit var binding: FragmentPageBinding

    @Inject
    lateinit var mediaManager: MediaManager

    @Inject
    lateinit var polygonManager: PolygonManager

    @Inject
    lateinit var toastManager: ToastManager

    @Inject
    lateinit var fileManager: FileManager

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val mainViewModel: MainViewModel by viewModels {
        mainViewModelFactory
    }

    private var polygons: List<Polygon>? = null

    private val scaleImage = InteractiveReading.INSTANCE.getScaleImage()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageBinding.inflate(inflater, container, false)

        init()
        observeLiveData()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val bookId = arguments?.getString(BOOK_ID, "") ?: ""
        val position = arguments?.getInt(POSITION, -1) ?: -1

        if (bookId.isEmpty() || position == -1) return

        mainViewModel.getPolygons(bookId, position)
    }

    override fun onPause() {
        super.onPause()
        mediaManager.stop()
    }

    override fun onWordClick(id: String) {
//        mediaManager.play(id)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                checkIfInside(event)
                return true
            }
            MotionEvent.ACTION_UP -> {
                v?.performClick()
                return true
            }
        }

        return false
    }

    override fun onCompleted() {
        binding.isPlaying = false
    }

    private fun checkIfInside(event: MotionEvent?) {
        polygonManager.checkIfInside(event, scaleImage, polygons)?.let {
            toastManager.showToast(
                event?.rawX?.toInt(),
                event?.rawY?.toInt(),
                it.display,
                binding.textView,
                lifecycleScope
            )

            val context = context ?: return
            val file = fileManager.getFile(context, "${it.id}.mp3")
            mediaManager.play(file.toUri())
        }
    }

    private fun init() {
        initClickListeners()
//        initSpannableText()
        initImage()
    }

    private fun initImage() {
        val position = arguments?.getInt(POSITION, -1) ?: -1
        if (position == -1) return

        readImageFromFile(position)

        binding.imageView.setOnTouchListener(this)
    }

    private fun readImageFromFile(position: Int) {
        val context = context ?: return
        val bookId = arguments?.getString(BOOK_ID) ?: return

        val books = InteractiveReading.INSTANCE.getData()?.books ?: return
        for (book in books) {
            if (book == null) continue
            if (book.id ?: "" == bookId) {
                val pages = book.pages ?: return

                val af = pages[position]?.localImageStorageLocation ?: ""
                val bitmap = fileManager.readImage(context, af) ?: return

                if (scaleImage == null) return

                binding.imageView.layoutParams.width = (scaleImage.imageWidth ?: 0.0).toInt()
                binding.imageView.layoutParams.height = (scaleImage.imageHeight ?: 0.0).toInt()

                binding.imageView.setImageBitmap(bitmap)
            }
        }
    }

//    private fun initSpannableText() {
//        val position = arguments?.getInt(POSITION) ?: -1
//
//        binding.movementMethod = LinkMovementMethod.getInstance()
//        binding.spannableText = SpanUtils.getSpannableText(this, position)
//
//        val scaleImage = scaleImage ?: return
//        val scaleX = scaleImage.scaleX ?: return
//        val scaleY = scaleImage.scaleY ?: return
//        val heightToReduce = scaleImage.heightToReduce ?: return
//
//        val pageMargins = PageDataUtils.getPageMargins(position) ?: return
//
//        val marginStart = pageMargins.start
//        val topMargin = pageMargins.top
//        val width = pageMargins.width
//
//        val lp = binding.spannableTextView.layoutParams as FrameLayout.LayoutParams
//        lp.marginStart = (marginStart * scaleX).toInt()
//        lp.topMargin = (topMargin * scaleY + heightToReduce).toInt()
//
//        binding.spannableTextView.width = (width * scaleY).toInt()
//        binding.spannableTextView.layoutParams = lp
//    }

    private fun initClickListeners() {
        val position = arguments?.getInt(POSITION) ?: return
        val bookId = arguments?.getString(BOOK_ID) ?: return
        val context = context ?: return

        val books = InteractiveReading.INSTANCE.getData()?.books ?: return
        for (book in books) {
            if (book == null) continue
            if (book.id ?: "" == bookId) {
                val pages = book.pages ?: return

                val af = pages[position]?.localAudioStorageLocation ?: ""

                val file = fileManager.getFile(context, af)
                binding.playPauseCommand = generatePlayPauseClickHandler(file.toUri())
                binding.replayCommand = generateReplayClickHandler(file.toUri())
            }
        }
    }

    private fun observeLiveData() {
        mainViewModel.polygonsLiveData.observe(viewLifecycleOwner) {
            polygons = it
        }
    }

    private val isPlayingFunction = {
        binding.isPlaying = true
    }

    private val isNotPlayingFunction = {
        binding.isPlaying = false
    }

    private fun generatePlayPauseClickHandler(uri: Uri) = ClickHandler(
        listOf(
            PlayPauseCommand(
                mediaManager,
                PlayCommand(mediaManager, uri, true, this, isPlayingFunction),
                PauseCommand(mediaManager, isNotPlayingFunction)
            )
        )
    )

    private fun generateReplayClickHandler(uri: Uri) =
        ClickHandler(
            listOf(
                ReplayCommand(
                    StopCommand(mediaManager),
                    PlayCommand(mediaManager, uri, false, this, isPlayingFunction)
                )
            )
        )

    companion object {
        private const val BOOK_ID = "com.hkurbardovic.interactivereading.book_id"
        private const val POSITION = "com.hkurbardovic.interactivereading.position"

        fun newInstance(bookId: String, position: Int) = PageFragment()
            .apply {
                arguments = Bundle().apply {
                    putString(BOOK_ID, bookId)
                    putInt(POSITION, position)
                }
            }
    }
}