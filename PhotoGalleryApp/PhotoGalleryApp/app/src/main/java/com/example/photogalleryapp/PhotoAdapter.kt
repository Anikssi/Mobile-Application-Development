package com.example.photogalleryapp

import android.content.Context
import android.view.*
import android.widget.*

class PhotoAdapter(
    private val context: Context,
    var photoList: MutableList<Photo>
) : BaseAdapter() {

    var selectionMode = false

    override fun getCount() = photoList.size

    override fun getItem(position: Int) = photoList[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_photo, parent, false)

        val img = view.findViewById<ImageView>(R.id.image)
        val title = view.findViewById<TextView>(R.id.title)
        val check = view.findViewById<CheckBox>(R.id.checkBox)

        val photo = photoList[position]

        img.setImageResource(photo.resourceId)
        title.text = photo.title

        check.visibility = if (selectionMode) View.VISIBLE else View.GONE
        check.isChecked = photo.isSelected

        return view
    }
}