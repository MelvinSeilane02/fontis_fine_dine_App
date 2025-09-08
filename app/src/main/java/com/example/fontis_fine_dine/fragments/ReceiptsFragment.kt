package com.example.fontis_fine_dine.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fontis_fine_dine.AddReceiptActivity
import com.example.fontis_fine_dine.R
import com.example.fontis_fine_dine.adapters.ReceiptsAdapter
import com.example.fontis_fine_dine.databinding.FragmentReceiptsBinding
import com.example.fontis_fine_dine.models.Receipt
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
//import kotlinx.android.synthetic.main.fragment_receipts.view.*

class ReceiptsFragment : Fragment() {

    private var _binding: FragmentReceiptsBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private val receipts = mutableListOf<Receipt>()
    private lateinit var adapter: ReceiptsAdapter

    // Firestore listener registration (optional to remove later)
    private var receiptsListener: com.google.firebase.firestore.ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiptsBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize Firebase
        FirebaseApp.initializeApp(requireContext())

        // Adapter with click handler
        adapter = ReceiptsAdapter(receipts) { receipt ->
            // Open detail or show image fullscreen - for now a toast
            Toast.makeText(requireContext(), "Receipt for order ${receipt.orderId}", Toast.LENGTH_SHORT).show()
            // Example: open a receipt detail activity
            // val intent = Intent(requireContext(), ReceiptDetailActivity::class.java)
            // intent.putExtra("receipt", receipt)
            // startActivity(intent)
        }

        // RecyclerView setup
        binding.rvReceipts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReceipts.adapter = adapter

        // FAB click -> open AddReceiptActivity
        binding.fabAddReceipt.setOnClickListener {
            startActivity(Intent(requireContext(), AddReceiptActivity::class.java))
        }

        // Start listening to receipts collection
        listenReceipts()

        return view
    }

    private fun listenReceipts() {
        // Order by timestamp descending so newest receipts show first
        receiptsListener = db.collection("receipts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snaps, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "Failed to load receipts: ${error.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                val list = snaps?.documents?.mapNotNull { doc ->
                    try {
                        val r = doc.toObject(Receipt::class.java)
                        r?.copy(id = doc.id)
                    } catch (e: Exception) {
                        null
                    }
                } ?: emptyList()

                receipts.clear()
                receipts.addAll(list)
                adapter.update(list)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // detach listener to avoid leaks
        receiptsListener?.remove()
        _binding = null
    }
}
