package com.example.firstapplication

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


import com.example.firstapplication.databinding.FragmentCartBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var transactionAdapter: TransactionAdapter

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

        fun generateUserId(): String {
            val formattedCounter = String.format("%03d", UserDatabase.checkOutList.size)
            return "COID-$formattedCounter"
        }

        val transactionList = UserDatabase.getTransactions()
        var sortedTransactionList: MutableList<Transaction> = mutableListOf()
        for (transaction in transactionList) {
            if(transaction.userID  == UserDatabase.currentID) {
                sortedTransactionList.add(transaction)
            }
        }

        val binding: FragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false)
        val recyclerView = binding.recyclerView2

        transactionAdapter = TransactionAdapter(sortedTransactionList, object: TransactionAdapter.OnItemClickListener{
            override fun onDelBtnClick(transaction: Transaction) {
                val position = sortedTransactionList.indexOf(transaction)
                if (position != -1) {

                    UserDatabase.transactionList.remove(transaction)

                    sortedTransactionList.removeAt(position)

                    transactionAdapter.notifyItemRemoved(position)
                    Toast.makeText(requireContext(), "Transaction deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Transaction not found", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onUpdateBtnClick(transaction: Transaction) {
                val position = transactionList.indexOf(transaction)
                if (position != -1) {
                    showCustomDialogBoc(transaction, position)
                } else {
                    Toast.makeText(requireContext(), "Transaction not found", Toast.LENGTH_SHORT).show()
                }
            }
        })
        val checkOutButton = binding.checkOutButton

        checkOutButton.setOnClickListener {
            if (UserDatabase.transactionList.isEmpty()) {
                Toast.makeText(requireContext(), "Cannot check out. Cart is empty.", Toast.LENGTH_SHORT).show()
            } else {
                var checkoutID = generateUserId()
                Toast.makeText(requireContext(), "Check out button clicked. Checkout ID: $checkoutID", Toast.LENGTH_SHORT).show()

                var out = CheckOut(checkoutID)
                UserDatabase.checkOutList.add(out)

                val checkout = CheckOut(checkoutID)
                UserDatabase.checkOutList.add(checkout)

                UserDatabase.transactionList.forEach { transaction ->
                    transaction.newID = checkoutID
                }
                UserDatabase.transactionList.clear()
                transactionAdapter.notifyDataSetChanged()

                sortedTransactionList.clear()
            }
        }

        recyclerView.adapter = transactionAdapter
        return binding.root
    }
    fun showCustomDialogBoc(transaction: Transaction, position: Int){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.update_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val quantity : EditText = dialog.findViewById(R.id.UpdateQuantity)
        val btnYes : Button = dialog.findViewById(R.id.dialogUpdate)
        val btnNo : Button = dialog.findViewById(R.id.dialogNoUpdate)

        btnYes.setOnClickListener {
            val newQuantity = quantity.text.toString()
            if (newQuantity.isNotEmpty()) {
                val quantityValue = newQuantity.toIntOrNull()
                if (quantityValue != null && quantityValue > 0) {
                    val updatedTransaction = transaction.copy(quantity = newQuantity)
                    transactionAdapter.updateTransaction(position, updatedTransaction)
                    val index = UserDatabase.transactionList.indexOf(transaction)
                    if (index != -1) {
                        UserDatabase.transactionList[index] = updatedTransaction
                        transactionAdapter.notifyItemChanged(index)
                    }
                }
                dialog.dismiss()
            }
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}