package com.example.notify.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notify.R
import com.example.notify.data.entities.Note
import com.example.notify.data.vm.NoteViewModel
//import com.example.notify.fragments.update.UpdateFragmentArgs
import com.example.notify.utils.Utils.Companion.hideKeyboard
import kotlinx.android.synthetic.main.custom_row.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private  val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.updateNote.setText(args.currentNote.note)

        setHasOptionsMenu(true)

        return  view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_delete) {
            deleteNote()
        }

        if(item.itemId == R.id.menu_update){
            updateNote()
        }

        return super.onOptionsItemSelected(item)
    }

    private  fun updateNote(){
        if(TextUtils.isEmpty(updateNote.text.toString())){
            Toast.makeText(requireContext(), getString(R.string.empty_note), Toast.LENGTH_LONG).show()
        }
        else {
            val note = Note(args.currentNote.id, updateNote.text.toString())

            mNoteViewModel.updateNote(note)

            Toast.makeText(requireContext(), getString(R.string.note_updated_successfull), Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment2)
        }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            mNoteViewModel.deleteNote(args.currentNote)
            Toast.makeText(
                requireContext(),
                getString(R.string.note_deleted_successfully),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment2)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.delete))
        builder.setMessage(getString(R.string.question_delete_note))
        builder.create().show()
    }
}