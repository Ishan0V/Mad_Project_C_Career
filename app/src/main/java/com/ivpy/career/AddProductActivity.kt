package com.ivpy.career

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.ivpy.career.firestore.FirestoreClass
import com.ivpy.career.model.Mentor
import com.ivpy.career.utils.Constants
import com.ivpy.career.utils.GlideLoader
import java.io.IOException

class AddProductActivity : AppCompatActivity() , View.OnClickListener{

    lateinit var progressBar : Dialog
    private var mSelectedImageFileUri: Uri? = null
    private var mProductImageURL: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        findViewById<ImageView>(R.id.add_photo_icon).setOnClickListener(this)
        findViewById<Button>(R.id.addMentorSubmit).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v!=null){
            when(v.id){
                R.id.add_photo_icon -> {
                    if(ContextCompat.checkSelfPermission(
                            this,Manifest.permission.READ_EXTERNAL_STORAGE
                    )== PackageManager.PERMISSION_GRANTED){
                        Constants.showImageChooser(this@AddProductActivity)
                    }else{
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            2
                        )
                    }
                }
                R.id.addMentorSubmit -> {
                    uploadProductImage()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this@AddProductActivity)
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(Intent, int)}.  This follows the
     * related Activity API as described there in
     * {@link Activity#onActivityResult(int, int, Intent)}.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {

                        // The uri of selected image from phone storage.
                        mSelectedImageFileUri = data.data!!

                        GlideLoader(this@AddProductActivity).loadUserPicture(
                            mSelectedImageFileUri!!,
                            findViewById(R.id.iv_mentor_image)
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@AddProductActivity,
                            resources.getString(R.string.image_selection_failed),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // A log is printed when user close or cancel the image selection.
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }

    private fun uploadProductImage() {

        showProgress()

        FirestoreClass().uploadImageToCloudStorage(
            this@AddProductActivity,
            mSelectedImageFileUri,
            Constants.PRODUCT_IMAGE
        )


    }

    /**
     * A function to get the successful result of product image upload.
     */
    fun imageUploadSuccess(imageURL: String) {
        //hideProgress()
        // Initialize the global image url variable.
        mProductImageURL = imageURL

        uploadMentor()
    }

    fun uploadMentor(){
        val mentor =Mentor(
            findViewById<TextInputLayout>(R.id.mentor_name).editText!!.text.toString().trim(),
            findViewById<TextInputLayout>(R.id.mentor_add_degree).editText!!.text.toString().trim(),
            findViewById<TextInputLayout>(R.id.mentor_add_exp).editText!!.text.toString().trim().toInt(),
            findViewById<TextInputLayout>(R.id.mentor_add_institute).editText!!.text.toString().trim(),
            mProductImageURL,
            FirestoreClass().getCurrentUserID()
        )
        FirestoreClass().uploadMentorDetails(this@AddProductActivity,mentor)
    }



    fun mentorUploadSuccess(){
        hideProgress()
        Toast.makeText(applicationContext,resources.getText(R.string.mentor_uploaded_success_message),Toast.LENGTH_SHORT).show()
        finish()
    }

    fun showProgress(){
        progressBar = Dialog(this)
        progressBar.setContentView(R.layout.progress_fullscreen)
        progressBar.setCancelable(false)
        progressBar.setCanceledOnTouchOutside(false)
        progressBar.show()
    }
    fun hideProgress(){
        progressBar.dismiss()
    }
}