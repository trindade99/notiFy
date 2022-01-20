package com.example.notify.fragments.reports.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notify.R
import com.example.notify.api_notify.dto.NoteDto
import com.example.notify.api_notify.requests.NotesApi
import com.example.notify.api_notify.retrofit.ServiceBuilder
import com.example.notify.utils.Utils.Companion.getToken
import com.example.notify.utils.Utils.Companion.getUserIdInSession
import com.example.notify.utils.Utils.Companion.hideKeyboard
import com.example.notify.utils.Utils.Companion.somethingWentWrong
import com.example.notify.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_add_report.*
import kotlinx.android.synthetic.main.fragment_reports_list.view.*
import kotlinx.android.synthetic.main.fragment_update_report.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddReportFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_report, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu_report, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_add_report) {
            addReport()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addReport() {
        if (TextUtils.isEmpty(add_report_title.text.toString()) || TextUtils.isEmpty(
                add_report_description.text.toString()
            )
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_title_and_description),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            llProgressBar.bringToFront()
            llProgressBar.visibility = View.VISIBLE

            val request = ServiceBuilder.buildService(NotesApi::class.java)
            val call = request.createReport(
                token = "Bearer ${getToken()}",
                users_id = getUserIdInSession(),
                title = add_report_title.text.toString(),
                description = add_report_description.text.toString()
            )

            call.enqueue(object : Callback<NoteDto> {
                override fun onResponse(call: Call<NoteDto>, response: Response<NoteDto>) {
                    llProgressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val report: NoteDto = response.body()!!

                        if (report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_added_new_note),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_addReportFragment_to_reportsListFragment)
                        } else {
                            Toast.makeText(
                                requireContext(), getString(
                                    resources.getIdentifier(
                                        report.description, "string",
                                        context?.packageName
                                    )
                                ), Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        if (response.code() == 401) {
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_addReportFragment_to_userLoginFragment)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<NoteDto>, t: Throwable) {
                    llProgressBar.visibility = View.GONE
                    somethingWentWrong()
                }
            })
        }
    }
}
