package me.fusan.pocketkaraoke

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.fusan.pocketkaraoke.databinding.ActivityMainBinding
import java.io.File
import android.Manifest
import android.content.Intent
import android.os.Build
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestReadWriteAccess()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_karaoke, R.id.navigation_effects, R.id.navigation_challenges
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        }

    private fun requestReadWriteAccess() {
        val folderName = "PocketKaraoke"
        val folderPath = "${Environment.getExternalStorageDirectory()}/$folderName"

        val folder = File(folderPath)

        if (folder.exists()) {
            // Folder exists, do something with it
        } else {
            // Folder does not exist, try to create it
            if (folder.mkdirs()) {
                // Folder created successfully
                // Request permission to read and write to the folder for Android Marshmallow and above
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions, 1)
            } else {
                // Folder creation failed, handle error
            }
        }
    }
}