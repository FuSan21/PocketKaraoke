package fusan.pocketkaraoke

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import me.fusan.pocketkaraoke.R

/**
 * Created by Fusan on 26-Mar-22.
 */
class CustomList(
    private val context: Context,
    private val songs: Array<String>,
    private val artist: Array<String>,
    private val imgID: Array<Int>
) :
    ArrayAdapter<String?>(context, R.layout.list_single) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_single, parent, false)
        }
        val songTitle = convertView!!.findViewById<View>(R.id.song_name) as TextView
        val artistTitle = convertView.findViewById<View>(R.id.art_name) as TextView
        val imageView = convertView.findViewById<View>(R.id.album_art) as ImageView
        songTitle.text = songs[position]
        songTitle.setTypeface(null, Typeface.BOLD)
        artistTitle.text = artist[position]
        imageView.setImageResource(imgID[position])
        return convertView
    }

    override fun getCount(): Int {
        return 2
    }
}