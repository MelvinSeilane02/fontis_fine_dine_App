package com.example.fontis_fine_dine.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fontis_fine_dine.AddEditDishActivity
import com.example.fontis_fine_dine.adapters.FoodAdapter
import com.example.fontis_fine_dine.databinding.FragmentMenuBinding
import com.example.fontis_fine_dine.models.FoodItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp

import com.google.firebase.firestore.FirebaseFirestore

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: FoodAdapter
    private val menuItems = mutableListOf<FoodItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //FirebaseApp.initializeApp(this)
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize Firebase
        FirebaseApp.initializeApp(requireContext())

        // RecyclerView setup
        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
        adapter = FoodAdapter(
            menuItems.toMutableList(),
            onItemClick = { /* Optional: handle normal click here */ },
            onEdit = { item -> openEditDish(item) },
            onDelete = { item -> confirmDelete(item) }
        )
        binding.rvMenu.adapter = adapter

        // FloatingActionButton setup
        binding.fabAddDish.setOnClickListener {
            val intent = Intent(requireContext(), AddEditDishActivity::class.java)
            startActivity(intent)
        }

        // Load menu from Firestore
        fetchMenuItems()

        return view
    }

    private fun fetchMenuItems() {
        db.collection("menu")
            .orderBy("name")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load menu: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@addSnapshotListener
                }

                val list = snapshots?.documents?.mapNotNull { doc ->
                    val item = doc.toObject(FoodItem::class.java)
                    item?.copy(id = doc.id)
                } ?: emptyList()

                menuItems.clear()
                menuItems.addAll(list)
                adapter.update(menuItems)
            }
    }

    private fun openEditDish(item: FoodItem) {
        val intent = Intent(requireContext(), AddEditDishActivity::class.java)
        intent.putExtra("food", item)
        startActivity(intent)
    }

    private fun confirmDelete(item: FoodItem) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Dish")
            .setMessage("Are you sure you want to delete \"${item.name}\"?")
            .setPositiveButton("Delete") { _, _ -> deleteDish(item) }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteDish(item: FoodItem) {
        if (item.id.isEmpty()) {
            Toast.makeText(requireContext(), "Cannot delete local item", Toast.LENGTH_SHORT).show()
            return
        }
        db.collection("menu").document(item.id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Dish deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to delete dish", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
