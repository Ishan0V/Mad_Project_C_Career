package com.ivpy.career.firestore


import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ivpy.career.AddProductActivity
import com.ivpy.career.LoginActivity
import com.ivpy.career.RegisterActivity
import com.ivpy.career.model.Mentor
import com.ivpy.career.model.User
import com.ivpy.career.ui.fragments.DashboardFragment
import com.ivpy.career.ui.fragments.HomeFragment
import com.ivpy.career.utils.Constants

class FirestoreClass {
    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity,userInfo:User){
        mFirestore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.registerSuccess()
            }
            .addOnFailureListener{
                activity.hideProgress()
            }
    }

    fun getCurrentUserID():String{
        val currentUser=FirebaseAuth.getInstance().currentUser
        var currentUserID=""
        if(currentUser!=null){
            currentUserID=currentUser.uid
        }
        return  currentUserID
    }

    fun getUserDetail(activity: Activity){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener {
                val user= it.toObject(User::class.java)!!

                when (activity){
                    is LoginActivity -> {
                        activity.userLoggedinSucces(user)
                    }
                }
            }
            .addOnFailureListener{
                when (activity){
                    is LoginActivity -> {
                        activity.hideProgress()
                    }
                }
            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?, imageType: String) {

        //getting the storage reference
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )

        //adding the file to reference
        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.e("Downloadable Image URL", uri.toString())

                        // Here call a function of base activity for transferring the result to it.
                        when (activity) {


                            is AddProductActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }
                        }
                    }
            }
            .addOnFailureListener { exception ->

                // Hide the progress dialog if there is any error. And print the error in log.
                when (activity) {


                    is AddProductActivity -> {
                        activity.hideProgress()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    /**
     * A function to make an entry of the user's product in the cloud firestore database.
     */
    fun uploadMentorDetails(activity: AddProductActivity, mentorInfo: Mentor) {

        mFirestore.collection(Constants.PRODUCTS)
            .document()
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(mentorInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.mentorUploadSuccess()
            }
            .addOnFailureListener { e ->

                activity.hideProgress()

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the product details.",
                    e
                )
            }
    }

    /**
     * A function to get the products list from cloud firestore.
     *
     * @param fragment The fragment is passed as parameter as the function is called from fragment and need to the success result.
     */
    fun getMentorList(fragment: Fragment) {
        // The collection name for PRODUCTS
        mFirestore.collection(Constants.PRODUCTS)
            .whereEqualTo(Constants.ID, getCurrentUserID())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val mentorList: ArrayList<Mentor> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Mentor::class.java)
                    product!!.mentor_id = i.id

                    mentorList.add(product)
                }

                when (fragment) {
                    is DashboardFragment -> {
                        fragment.successMentorListFromFireStore(mentorList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is DashboardFragment -> {
                        fragment.hideProgress()
                    }
                }
                Log.e("Get Product List", "Error while getting product list.", e)
            }
    }

    fun getMentorListALL(fragment: Fragment) {
        // The collection name for PRODUCTS
        mFirestore.collection(Constants.PRODUCTS)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val mentorList: ArrayList<Mentor> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Mentor::class.java)
                    product!!.mentor_id = i.id

                    mentorList.add(product)
                }

                when (fragment) {
                    is HomeFragment -> {
                        fragment.successMentorListFromFireStoreALL(mentorList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is HomeFragment -> {
                        fragment.hideProgress()
                    }
                }
                Log.e("Get Product List", "Error while getting product list.", e)
            }
    }

    /**
     * A function to get the dashboard items list. The list will be an overall items list, not based on the user's id.
     */
    /*fun getHomeItemsList(fragment: HomeFragment) {
        // The collection name for PRODUCTS
        mFirestore.collection(Constants.PRODUCTS)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e(fragment.javaClass.simpleName, document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val mentorList: ArrayList<Mentor> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Mentor::class.java)!!
                    product.mentor_id = i.id
                    mentorList.add(product)
                }

                // Pass the success result to the base fragment.
                fragment.successHomeItemsList(mentorList)
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                fragment.hideProgress()
                Log.e(fragment.javaClass.simpleName, "Error while getting dashboard items list.", e)
            }
    }*/
}