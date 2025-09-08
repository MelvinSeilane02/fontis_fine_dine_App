package com.example.fontis_fine_dine

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.fontis_fine_dine.models.Receipt
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*
//import com.example.fontis_fine_dine.R

class AddReceiptActivity : AppCompatActivity() {

    private val PICK_IMAGE = 1001
    private var imageUri: Uri? = null

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_receipt)

        val etOrderId = findViewById<EditText>(R.id.etOrderId)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val ivPreview = findViewById<ImageView>(R.id.ivPreview)
        val btnPick = findViewById<Button>(R.id.btnPickImage)
        val btnUpload = findViewById<Button>(R.id.btnUpload)

        btnPick.setOnClickListener { pickImageFromGallery() }

        btnUpload.setOnClickListener {
            val orderId = etOrderId.text.toString().trim()
            val notes = etNotes.text.toString().trim()

            if (imageUri == null) {
                Toast.makeText(this, "Please pick an image first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            uploadReceiptImage(imageUri!!, orderId, notes)
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            findViewById<ImageView>(R.id.ivPreview).setImageURI(imageUri)
        }
    }

    private fun uploadReceiptImage(uri: Uri, orderId: String, notes: String) {
        val id = db.collection("receipts").document().id
        val filename = "receipts/$id.jpg"
        val ref = storage.reference.child(filename)

        val uploadTask = ref.putFile(uri)
        Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show()

        uploadTask.addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { downloadUri ->
                // Save receipt doc
                val receipt = Receipt(
                    id = id,
                    orderId = orderId,
                    notes = notes,
                    receiptUrl = downloadUri.toString(),
                    uploadedBy = "admin", // replace with actual admin username
                    timestamp = System.currentTimeMillis()
                )

                db.collection("receipts").document(id).set(receipt)
                    .addOnSuccessListener {
                        // Optionally link to order doc if orderId supplied
                        if (orderId.isNotEmpty()) {
                            db.collection("orders").document(orderId)
                                .update("receiptUrl", downloadUri.toString(), "paid", true)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Receipt uploaded & order updated", Toast.LENGTH_LONG).show()
                                    finish()
                                }
                                .addOnFailureListener {
                                    // if update fails (maybe orderDoc doesn't exist), still finish
                                    Toast.makeText(this, "Receipt saved but failed to link to order", Toast.LENGTH_LONG).show()
                                    finish()
                                }
                        } else {
                            Toast.makeText(this, "Receipt uploaded", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to save receipt: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
