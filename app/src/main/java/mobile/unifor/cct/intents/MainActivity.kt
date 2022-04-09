package mobile.unifor.cct.intents

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var mButtonCall: Button
    private lateinit var mButtonSpotify: Button
    private lateinit var mButtonCamera: Button

    private lateinit var mImageCamera: ImageView
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mButtonCall = findViewById(R.id.main_button_call)
        mButtonSpotify = findViewById(R.id.main_button_webmusic)
        mButtonCamera = findViewById(R.id.main_button_camera)

        mImageCamera = findViewById(R.id.main_imageView_camera)


        mButtonCamera.setOnClickListener(this)
        mButtonSpotify.setOnClickListener(this)
        mButtonCall.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.main_button_camera -> dispatchTakePictureIntent()
            R.id.main_button_webmusic -> goToPlaylist("https://www.youtube.com/watch?v=LatorN4P9aA")
            R.id.main_button_call -> dialPhoneNumber("+5585997238135")
        }

    }


    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun goToPlaylist(url: String) {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

            }
        }
    }

    //visualiza miniatura
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            mImageCamera.setImageBitmap(imageBitmap)
        }
    }
}