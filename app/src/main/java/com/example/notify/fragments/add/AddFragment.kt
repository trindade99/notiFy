package com.example.notify.fragments.add

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notify.R
import com.example.notify.data.entities.Note
import com.example.notify.data.vm.NoteViewModel
import com.example.notify.utils.Utils.Companion.hideKeyboard
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_add) {
            addNote()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addNote(){
        if(TextUtils.isEmpty(addNote.text.toString())){
            Toast.makeText(requireContext(), getString(R.string.empty_note), Toast.LENGTH_LONG).show()
        }
        else {
            val note = Note(0, addNote.text.toString())

            mNoteViewModel.addNote(note)

            Toast.makeText(requireContext(), getString(R.string.successfull_added_new_note), Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }


}