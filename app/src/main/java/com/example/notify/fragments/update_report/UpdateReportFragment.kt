package com.example.notify.fragments.update_report

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notify.R
import com.example.notify.api_notify.dto.NoteDto
import com.example.notify.api_notify.requests.NotesApi
import com.example.notify.api_notify.retrofit.ServiceBuilder
import com.example.notify.utils.Utils.Companion.getToken
import com.example.notify.utils.Utils.Companion.hideKeyboard
import com.example.notify.utils.Utils.Companion.somethingWentWrong
import com.example.notify.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_update_report.*
import kotlinx.android.synthetic.main.fragment_update_report.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateReportFragment : Fragment() {

    private val args by navArgs<UpdateReportFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_report, container, false)

        setHasOptionsMenu(true)

        view.update_report_title.setText(args.currentReport.title)
        view.update_report_description.setText(args.currentReport.description)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_delete_menu_report, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()

        if (item.itemId == R.id.menu_delete_report) {
            deleteReport()
        }

        if (item.itemId == R.id.menu_update_report) {
            updateReport()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateReport() {
        if (TextUtils.isEmpty(update_report_title.text.toString()) || TextUtils.isEmpty(
                update_report_description.text.toString()
            )
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_title_and_description),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            val request = ServiceBuilder.buildService(NotesApi::class.java)
            val call = request.updateReport(
                token = "Bearer ${getToken()}",
                id = args.currentReport.id,
                title = update_report_title.text.toString(),
                description = update_report_description.text.toString()
            )

            call.enqueue(object : Callback<NoteDto> {
                override fun onResponse(call: Call<NoteDto>, response: Response<NoteDto>) {
                    if (response.isSuccessful) {
                        val report: NoteDto = response.body()!!

                        if (report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_updated_report),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_updateReportFragment_to_reportsListFragment)
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
                                findNavController().navigate(R.id.action_updateReportFragment_to_userLoginFragment)
                            })
                        } else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<NoteDto>, t: Throwable) {
                    somethingWentWrong()
                }
            })
        }
    }

    private fun deleteReport() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->

            val request = ServiceBuilder.buildService(NotesApi::class.java)
            val call = request.deleteReport(
                token = "Bearer ${getToken()}",
                id = args.currentReport.id
            )

            call.enqueue(object : Callback<NoteDto> {
                override fun onResponse(call: Call<NoteDto>, response: Response<NoteDto>) {
                    if (response.isSuccessful) {
                        val report: NoteDto = response.body()!!

                        if(report.status == "OK") {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.successfull_deleted_report),
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_updateReportFragment_to_reportsListFragment)
                        }
                        else {
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

                        if(response.code() == 401){
                            unauthorized(navigatonHandlder = {
                                findNavController().navigate(R.id.action_updateReportFragment_to_userLoginFragment)
                            })
                        }
                        else {
                            somethingWentWrong()
                        }
                    }
                }

                override fun onFailure(call: Call<NoteDto>, t: Throwable) {
                    somethingWentWrong()
                }
            })
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.delete_report))
        builder.setMessage(getString(R.string.question_delete_report))
        builder.create().show()
    }
}