package me.fusan.pocketkaraoke.ui.karaoke

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import me.fusan.pocketkaraoke.MainActivity
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.karaoke.model.KaraokeViewModel
import me.fusan.pocketkaraoke.ui.karaoke.presenter.KaraokeViewPresenter

class KaraokeFragment : Fragment() {
    private lateinit var karaokeViewPresenter: KaraokeViewPresenter
    private lateinit var list: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v: View = inflater.inflate(R.layout.fragment_karaoke, container, false)
        val activity: AppCompatActivity = activity as AppCompatActivity
        karaokeViewPresenter = KaraokeViewPresenter(this)
        list = v.findViewById(R.id.listView) as ListView
        list.adapter = karaokeViewPresenter.generateCustomListView(v.context)
        list.onItemClickListener = ItemClicked()
        return v
    }
    inner class ItemClicked : AdapterView.OnItemClickListener {
        override fun onItemClick(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
            if (!checkRecordingPermission()) requestRecordingPermission()
            else createReadyToSingAlert(i)
        }
    }

    private fun createReadyToSingAlert(i: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle(karaokeViewPresenter.returnSongTitle(i))
        builder.setMessage("Ready to sing?")
        builder.setPositiveButton("Yes") { _, _ ->
            val intent = Intent(activity, KaraokeActivity::class.java)
            intent.putExtra("songID", i)
            startActivity(intent)
        }

        builder.setNegativeButton("No") { _, _ ->
            //No Clicked
        }
        builder.show()
    }

    private fun checkRecordingPermission(): Boolean {
        return activity?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.RECORD_AUDIO) } == PackageManager.PERMISSION_GRANTED
    }
    private fun requestRecordingPermission() {
        activity?.let {
            ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.RECORD_AUDIO), karaokeViewPresenter.returnRecordingPermissionCode())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == karaokeViewPresenter.returnRecordingPermissionCode()){
            if (grantResults.isNotEmpty()){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //External Storage Permission granted
                    Log.d(karaokeViewPresenter.returnRecordingPermissionTag(), "onRequestPermissionsResult: Recording Permission granted")
                }
                else{
                    //External Storage Permission denied...
                    Log.d(karaokeViewPresenter.returnRecordingPermissionTag(), "onRequestPermissionsResult: Recording Permission denied...")
                    Toast.makeText(activity, "Recording Permission denied...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}