package com.example.notify.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notify.R
import com.example.notify.data.User
import com.example.notify.data.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.add_btn.setOnClickListener{
            InsertDatatoDataBase()
        }

        return view
    }

    private fun InsertDatatoDataBase() {
        val userName = add_UserName.text.toString()
        val textTitle = add_Title.text.toString()
        val textContent = add_text.text.toString()

        if (inputCheck(userName,textTitle,textContent))
        {
            //Create Note Object
            //user stands for one note entry
            val note = User(0,userName,textTitle,textContent)
            // add note to database
            mUserViewModel.addUser(note)
            Toast.makeText(requireContext(),"Note Added with Success",Toast.LENGTH_LONG).show()
            //back to main screen
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else
        {
            Toast.makeText(requireContext(),"No field can be blank ",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(userName: String, textTitle: String, text: String): Boolean{
        return !(TextUtils.isEmpty(userName) && TextUtils.isEmpty(textTitle) && TextUtils.isEmpty(text))
    }


}