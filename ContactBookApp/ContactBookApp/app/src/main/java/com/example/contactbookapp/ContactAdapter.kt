package com.example.contactbookapp
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*

class ContactAdapter(
    context: Context,
    private var contactList: MutableList<Contact>
) : ArrayAdapter<Contact>(context, 0, contactList), Filterable {

    private var filteredList = contactList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_contact, parent, false)

        val contact = filteredList[position]

        val avatar = view.findViewById<TextView>(R.id.avatar)
        val name = view.findViewById<TextView>(R.id.name)
        val phone = view.findViewById<TextView>(R.id.phone)

        avatar.text = contact.name.first().toString()
        name.text = contact.name
        phone.text = contact.phone

        avatar.setBackgroundColor(randomColor())

        return view
    }

    private fun randomColor(): Int {
        val colors = listOf(
            Color.RED, Color.BLUE, Color.GREEN,
            Color.MAGENTA, Color.CYAN
        )
        return colors.random()
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val resultList = if (constraint.isNullOrEmpty()) {
                    contactList
                } else {
                    contactList.filter {
                        it.name.lowercase().contains(constraint.toString().lowercase())
                    }
                }

                val results = FilterResults()
                results.values = resultList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<Contact>
                notifyDataSetChanged()
            }
        }
    }
}