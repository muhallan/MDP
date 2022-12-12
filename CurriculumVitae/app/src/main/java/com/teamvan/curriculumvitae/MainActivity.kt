package com.teamvan.curriculumvitae

import android.Manifest.permission.*
import android.R.attr.path
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.teamvan.data.Globals
import com.teamvan.data.LoggedInUser
import com.teamvan.fragments.AboutMeFragment
import com.teamvan.fragments.ContactsFragment
import com.teamvan.fragments.EducationFragment
import com.teamvan.fragments.WorkFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarTB.setTitle(R.string.app_name)
        setSupportActionBar(toolbarTB)

        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()

        mFragmentTransaction.replace(R.id.containerFL, AboutMeFragment()).commit()

        val header: View = navigation_viewNV.getHeaderView(0)
        val nameTv = header.findViewById<TextView>(R.id.loggedInAsTv)

        val user: LoggedInUser = Globals.signedInUser ?: getLoggedInUser(this)
        nameTv.text = "Logged in as ${user.name}"

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigation_viewNV.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            // This method will trigger on item Click of navigation menu
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

                menuItem.isChecked = !menuItem.isChecked

                // Closing drawer on item click
                drawer_layoutDL.closeDrawers()
                when (menuItem.itemId) {
                    R.id.mn_about_me -> {
                        toolbarTB.setTitle(R.string.app_name)
                        val fragmentTransaction2: FragmentTransaction = supportFragmentManager.beginTransaction()
                        fragmentTransaction2.replace(R.id.containerFL, AboutMeFragment())
                        fragmentTransaction2.commit()
                        return true
                    }
                    R.id.mn_work -> {
                        toolbarTB.title = "Work Experience"
                        val fragmentTransaction3: FragmentTransaction = supportFragmentManager.beginTransaction()
                        fragmentTransaction3.replace(R.id.containerFL, WorkFragment())
                        fragmentTransaction3.commit()
                        return true
                    }
                    R.id.mn_education -> {
                        toolbarTB.title = "Education"
                        val fragmentTransaction3: FragmentTransaction = supportFragmentManager.beginTransaction()
                        fragmentTransaction3.replace(R.id.containerFL, EducationFragment())
                        fragmentTransaction3.commit()
                        return true
                    }
                    R.id.mn_contact_me -> {
                        toolbarTB.title = "Contact Me"
                        val fragmentTransaction3: FragmentTransaction = supportFragmentManager.beginTransaction()
                        fragmentTransaction3.replace(R.id.containerFL, ContactsFragment())
                        fragmentTransaction3.commit()
                        return true
                    }
                    R.id.mn_logout -> {
                        startActivity(Intent(this@MainActivity, SignInActivity::class.java))
                        return true
                    }
                }
                return true
            }
        })

        val actionBarDrawerToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layoutDL,
            toolbarTB,
            R.string.openDrawer,
            R.string.closeDrawer
        )

        // Setting the actionbarToggle to drawer layout
        drawer_layoutDL.addDrawerListener(actionBarDrawerToggle)

        // Calling sync state is necessary or else your hamburger icon won't show up
        actionBarDrawerToggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mn_share -> {
                try {
                    sharePdfAsset("MUHWEZIAllan051122Resume.pdf", Intent.ACTION_SEND)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Throws(IOException::class)
    private fun createFileInFilesDir(filename: String): File {
        val file = File(filesDir.path + "/" + filename)
        if (file.exists()) {
            if (!file.delete()) {
                throw IOException()
            }
        }
        if (!file.createNewFile()) {
            throw IOException()
        }
        return file
    }

    @Throws(IOException::class)
    private fun copyAssetToFile(assetName: String, file: File) {
        val buffer = ByteArray(1024)
        val inputStream = assets.open(assetName)
        val outputStream: OutputStream = FileOutputStream(file)
        while (inputStream.read(buffer) > 0) {
            outputStream.write(buffer)
        }
    }

    private fun createIntentForFile(file: File, intentAction: String): Intent {
        val uri = FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", file)
        val intent = Intent(intentAction)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(uri, "application/pdf")
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        return intent
    }

    @Throws(IOException::class)
    private fun sharePdfAsset(assetName: String, intentAction: String) {
        try {
            val file = createFileInFilesDir(assetName)
            copyAssetToFile(assetName, file)
            val intent = createIntentForFile(file, intentAction)
            startActivity(Intent.createChooser(intent, "Sharing PDF"))
        } catch (e: IOException) {
            e.printStackTrace()
            AlertDialog.Builder(this)
                .setTitle("Error sharing the PDF")
                .setMessage("Problem encountered while sharing the PDF file")
                .show()
        }
    }

    companion object {
        fun storeLoggedInUser(ctx: Context, username: String, password: String, name: String) {
            val prefs = ctx.getSharedPreferences(
                MainActivity::class.java.simpleName,
                MODE_PRIVATE
            )
            val editor = prefs.edit()

            editor.putString("username", username)
            editor.putString("password", password)
            editor.putString("name", name)
            editor.apply()
        }

        fun getLoggedInUser(ctx: Context): LoggedInUser {
            val prefs = ctx.getSharedPreferences(MainActivity::class.java.simpleName, Context.MODE_PRIVATE)
            val password: String = prefs.getString("password", "") ?: ""
            val username = prefs.getString("username", "") ?: ""
            val name = prefs.getString("name", "") ?: ""
            return LoggedInUser(name, username, password)
        }
    }

}