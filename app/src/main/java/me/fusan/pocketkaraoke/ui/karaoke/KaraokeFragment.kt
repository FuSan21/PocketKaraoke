package me.fusan.pocketkaraoke.ui.karaoke

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fusan.pocketkaraoke.CustomList
import me.fusan.pocketkaraoke.R
import me.fusan.pocketkaraoke.ui.karaoke.presenter.KaraokePresenter

class KaraokeFragment : Fragment() {
    private lateinit var karaokePresenter: KaraokePresenter
    private lateinit var list: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v: View = inflater.inflate(R.layout.fragment_karaoke, container, false)
        val activity: AppCompatActivity = activity as AppCompatActivity
        karaokePresenter = KaraokePresenter(this)
        list = v.findViewById(R.id.listView) as ListView
        list.adapter = karaokePresenter.generateCustomListView(v.context)
        list.onItemClickListener = ItemClicked()
        return v
    }
    inner class ItemClicked : AdapterView.OnItemClickListener {
        override fun onItemClick(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle(karaokePresenter.returnSongTitle(i))
            builder.setMessage("Ready to sing?")
            builder.setPositiveButton("Yes") { _, _ ->
                val intent = Intent(activity, KaraokePresenter::class.java) //temp class, must fix
                startActivity(intent)
            }

            builder.setNegativeButton("No") { _, _ ->
                //No Clicked
            }
            builder.show()
        }
    }
}