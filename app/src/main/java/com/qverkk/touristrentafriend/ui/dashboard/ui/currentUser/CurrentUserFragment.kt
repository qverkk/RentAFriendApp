package com.qverkk.touristrentafriend.ui.dashboard.ui.currentUser


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.database.AppDatabase
import com.qverkk.touristrentafriend.helpers.GZIPCompression
import com.qverkk.touristrentafriend.helpers.PicturesHelper
import com.qverkk.touristrentafriend.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_current_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream

/**
 * A simple [Fragment] subclass.
 */
class CurrentUserFragment : Fragment() {

    private lateinit var currentUserViewModel: CurrentUserViewModel
    private lateinit var userImageView: ImageView
    private val GALLERY_REQUEST_CODE = 10001
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_DATA = 10002

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentUserViewModel =
            ViewModelProviders.of(this).get(CurrentUserViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_current_user, container, false)
        val fullNameTextView: TextView = root.findViewById(R.id.textView_current_user_full_name)
        val birthDateTextView: TextView = root.findViewById(R.id.textView_current_user_birth_date)
        val countryTextView: TextView = root.findViewById(R.id.textView_current_user_country)
        val cityTextView: TextView = root.findViewById(R.id.textView_current_user_city)
        val priceTextView: TextView = root.findViewById(R.id.textView_current_user_price)
        val descriptionTextView: TextView =
            root.findViewById(R.id.textView_current_user_description)
        userImageView = root.findViewById(R.id.image_current_user_profile_picture)

        initializeUserImage(userImageView)

        initializeLogoutButton(root)

        initializeObservables(
            fullNameTextView,
            birthDateTextView,
            countryTextView,
            cityTextView,
            priceTextView,
            descriptionTextView
        )

        return root
    }

    private fun initializeUserImage(imageView: ImageView) {
        imageView.setOnClickListener {
            if (checkForExternalPermission()) {
                showGallery()
            }
        }
    }

    private fun showGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val types = arrayListOf("image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, types)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
//                currentUserViewModel.changeImageUri(selectedImage)
                val uri = data?.data ?: return
                val inputStream = activity?.contentResolver?.openInputStream(uri)
                val imageBitmap = BitmapFactory.decodeStream(inputStream)

                val byteOutputStream = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteOutputStream)
                val bytes = byteOutputStream.toByteArray()
                val baseEncoded = Base64.encodeToString(bytes, Base64.NO_WRAP)

                PicturesHelper().postUserProfilePicture(baseEncoded)
                currentUserViewModel.changeImageUri(baseEncoded)
                return
            }
        }
    }

    private fun checkForExternalPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_DATA
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_DATA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showGallery()
                }
            }
        }
    }

    private fun initializeObservables(
        fullNameTextView: TextView,
        birthDateTextView: TextView,
        countryTextView: TextView,
        cityTextView: TextView,
        priceTextView: TextView,
        descriptionTextView: TextView
    ) {
        currentUserViewModel.fullName.observe(this@CurrentUserFragment, Observer {
            fullNameTextView.text = it
        })

        currentUserViewModel.birthDate.observe(this@CurrentUserFragment, Observer {
            birthDateTextView.text = it
        })

        currentUserViewModel.country.observe(this@CurrentUserFragment, Observer {
            countryTextView.text = it
        })

        currentUserViewModel.city.observe(this@CurrentUserFragment, Observer {
            cityTextView.text = it
        })

        currentUserViewModel.price.observe(this@CurrentUserFragment, Observer {
            priceTextView.text = it.toPlainString()
        })

        currentUserViewModel.description.observe(this@CurrentUserFragment, Observer {
            descriptionTextView.text = it
        })

        currentUserViewModel.imageSource.observe(this@CurrentUserFragment, Observer {
//            image_current_user_profile_picture.setImageURI(it)
//            val drawable = image_current_user_profile_picture.drawable
//            val bitmap = drawable.toBitmap()

//            val byteOutputStream = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteOutputStream)
            val base64 = Base64.decode(it, Base64.NO_WRAP)
            val bitmap = BitmapFactory.decodeByteArray(base64, 0, base64.size)
            image_current_user_profile_picture.setImageBitmap(bitmap)
        })
    }

    private fun initializeLogoutButton(root: View) {
        val buttonLogout: Button = root.findViewById(R.id.button_current_user_logout)

        buttonLogout.setOnClickListener {
            val database = AppDatabase.getDatabase(context!!)
            GlobalScope.launch {
                database.userDao().deleteAll()
                database.userInformationDao().deleteAll()

                activity?.runOnUiThread {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }
    }
}
