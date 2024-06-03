package com.example.firstapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firstapplication.databinding.FragmentAccountBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentAccountBinding ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAccountBinding.inflate(inflater, container, false)
        val AccountTextViewUsername = binding!!.AccountTextViewUsername
        AccountTextViewUsername.setText(UserDatabase.currentUsername)

        val AccountTextViewPhone = binding!!.AccountTextViewPhone
        AccountTextViewPhone.setText(UserDatabase.currentPhone)

       val LogoutBTN = binding!!.profileLogOutButton
        LogoutBTN.setOnClickListener{
            var builder= AlertDialog.Builder(requireContext())
            builder.setTitle("Are You Sure Want To Log Out?")
            builder.setMessage("Don't Worry, You Can Login Again")

            builder.setPositiveButton("Yes") {dialog, which ->
                Toast.makeText(requireContext(), "Log Out Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity:: class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("No") {dialog, which ->
                dialog.dismiss()
            }
            builder.create().show()
        }

        return binding!!.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}