package com.example.notify.fragments.reports_list

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notify.R
import com.example.notify.api_notify.model.Note
import com.example.notify.api_notify.requests.NotesApi
import com.example.notify.api_notify.retrofit.ServiceBuilder
import com.example.notify.utils.Utils.Companion.getToken
import com.example.notify.utils.Utils.Companion.getUserIdInSession
import com.example.notify.utils.Utils.Companion.hideKeyboard
import com.example.notify.utils.Utils.Companion.somethingWentWrong
import com.example.notify.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_reports_list.*
import kotlinx.android.synthetic.main.fragment_reports_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReportsListFragment : Fragment() {
    private  var  _view : View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reports_list, container, false)
        _view = view

        setHasOptionsMenu(true)

        getAndSetData(view)

        view.btn_add_new_report_from_reports_list.setOnClickListener() {
            findNavController().navigate(R.id.action_reportsListFragment_to_addReportFragment)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.reports_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.user_logout) {
            logout()
        }

        if(item.itemId == R.id.reports_list_refresh){
            _view?.let { getAndSetData(it) }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getAndSetData(view: View) {

        view.llProgressBarList.bringToFront()
        view.llProgressBarList.visibility = View.VISIBLE


        val adapter = ReportsListAdapter(getUserIdInSession())

        val recyclerView = view.recyclerview_reports_list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val request = ServiceBuilder.buildService(NotesApi::class.java)
        val call = request.getReports(token = "Bearer ${getToken()}")

        call.enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {

                llProgressBarList.visibility = View.GONE

                if (response.isSuccessful) {
                    val reports: List<Note> = response.body()!!
                    adapter.setData(reports)
                } else {
                    if (response.code() == 401) {
                        unauthorized(navigatonHandlder = {
                            findNavController().navigate(R.id.action_reportsListFragment_to_userLoginFragment)
                        })
                    } else {
                        somethingWentWrong()
                    }
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                llProgressBarList.visibility = View.GONE
                somethingWentWrong()
            }
        })
    }

    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val preferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
            preferences.edit().putString("token", null).apply()
            findNavController().navigate(R.id.action_reportsListFragment_to_userLoginFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString((R.string.logout_question)))
        builder.create().show()
    }
}