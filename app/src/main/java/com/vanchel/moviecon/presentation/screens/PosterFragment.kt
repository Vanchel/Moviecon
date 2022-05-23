package com.vanchel.moviecon.presentation.screens

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.vanchel.moviecon.R
import com.vanchel.moviecon.data.network.IMAGES_URL
import com.vanchel.moviecon.databinding.FragmentPosterBinding
import com.vanchel.moviecon.presentation.viewmodels.PosterViewModel
import com.vanchel.moviecon.util.SIZE_ORIGINAL
import com.vanchel.moviecon.util.generatePictureFileName
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

/**
 * @author Иван Тимашов
 *
 * Фрагмент, отображающий один постер.
 */
@AndroidEntryPoint
class PosterFragment : Fragment() {
    private var _binding: FragmentPosterBinding? = null
    private val binding: FragmentPosterBinding
        get() = _binding!!

    private val args: PosterFragmentArgs by navArgs()

    private val posterViewModel: PosterViewModel by viewModels()

    private val imageLoadListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?, model: Any?, target: Target<Drawable>?,
            dataSource: DataSource?, isFirstResource: Boolean
        ): Boolean {
            posterViewModel.imageLoadSuccess()
            return false
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setViewModelObservers()

        Glide.with(view)
            .load("$IMAGES_URL$SIZE_ORIGINAL${args.imagePath}")
            .listener(imageLoadListener)
            .into(binding.imageView)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_round_arrow_back_24)
            setNavigationOnClickListener { findNavController().popBackStack() }

            inflateMenu(R.menu.poster_menu)
            menu.findItem(R.id.menu_item_save_to_gallery).isEnabled = false
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_item_save_to_gallery -> {
                        posterViewModel.saveToGallery()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setViewModelObservers() {
        posterViewModel.apply {
            saveToGallery.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { saveImage() }
            }
            savedToGallery.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    Snackbar.make(binding.root, R.string.saved_to_gallery, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
            isImageAvailable.observe(viewLifecycleOwner) {
                binding.toolbar.menu.findItem(R.id.menu_item_save_to_gallery).isEnabled = it
            }
        }
    }

    private fun saveImage() {
        saveImageToStorage(binding.imageView.drawable.toBitmap())
    }

    @Suppress("DEPRECATION")
    private fun saveImageToStorage(
        bitmap: Bitmap,
        mimeType: String = "image/jpeg",
        directory: String = Environment.DIRECTORY_PICTURES,
        mediaContentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    ) {
        val filename = generatePictureFileName("jpg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                put(MediaStore.Images.Media.RELATIVE_PATH, directory)
            }

            requireContext().contentResolver.run {
                val uri = insert(mediaContentUri, values) ?: return
                val imageOutStream = openOutputStream(uri) ?: return

                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                imageOutStream.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
            }
        } else {
            if (!requestStoragePermission()) return

            val imagePath = Environment.getExternalStoragePublicDirectory(directory).absolutePath
            val image = File(imagePath, filename)
            val imageOutStream = FileOutputStream(image)

            imageOutStream.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }

            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, image.toUri())
            requireActivity().sendBroadcast(intent)
        }

        posterViewModel.imageSaveSuccess()
    }

    private fun requestStoragePermission(): Boolean {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                return true
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                showRationale()
                return false
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                return false
            }
        }
    }

    private fun showRationale() {
        Snackbar.make(
            requireView(),
            R.string.write_external_storage_permission_rationale,
            Snackbar.LENGTH_LONG
        ).setAction(R.string.provide_permission) {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }.show()
    }
}