package com.example.notify.fragments.update

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notify.R
import com.example.notify.model.Note
import com.example.notify.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private lateinit var mNoteViewModel: NoteViewModel

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        view.update_UserName.setText(args.currentNote.UserName)
        view.update_Title.setText(args.currentNote.Title)
        view.update_text.setText(args.currentNote.Body)

        view.update_btn.setOnClickListener(){
            updateItems()
        }

        return view
    }


    private fun updateItems(){
        val UserName = update_UserName.text.toString()
        val Title = update_Title.text.toString()
        val Text = update_text.text.toString()

        if (inputCheck(UserName,Title,Text))
        {
            //Create Note Object
            //user stands for one note entry
            val note = Note(args.currentNote.id,UserName,Title,Text)
            // add note to database
            mNoteViewModel.updateUser(note)
            Toast.makeText(requireContext(),"Note Updated with Success", Toast.LENGTH_LONG).show()
            //back to main screen
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else
        {
            Toast.makeText(requireContext(),"No field can be blank ", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(userName: String, textTitle: String, text: String): Boolean{
        return !(TextUtils.isEmpty(userName) && TextUtils.isEmpty(textTitle) && TextUtils.isEmpty(text))
    }
}