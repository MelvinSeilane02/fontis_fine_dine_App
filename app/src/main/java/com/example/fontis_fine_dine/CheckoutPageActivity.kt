package com.example.fontis_fine_dine

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CheckoutPageActivity : AppCompatActivity() {

    private lateinit var name : String
    private lateinit var phone : String
    private var selectedPayment : Int = 0
    private var totalPrice : Double = 0.0
    private lateinit var normalLst : List<GroupedFoodItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.checkout_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        totalPrice = intent.getDoubleExtra("total_price", 0.0)
        //findViewById<TextView>(R.id.tvTotalPrice).text = "R %.2f".format(totalPrice)
    }

    fun create_order(view: View) {
        val name = findViewById<EditText>(R.id.etName).text.toString().trim()
        val phone = findViewById<EditText>(R.id.etPhone).text.toString().trim()
        val checkedId = findViewById<RadioGroup>(R.id.paymentGroup).checkedRadioButtonId

        val receivedList = intent.getSerializableExtra("groupedItems") as? ArrayList<GroupedFoodItem>

        if (receivedList != null) {
            normalLst = receivedList
            Toast.makeText(this, "Items you ordered received: ${normalLst.size}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No items received", Toast.LENGTH_SHORT).show()
            normalLst = emptyList()
        }

        placeOrder(name, phone, checkedId, normalLst, this)

    }

    fun placeOrder(
        name: String,
        phone: String,
        checkedId: Int,
        items: List<GroupedFoodItem>,
        context: CheckoutPageActivity
    ) {
        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        val paymentMethod = getPaymentMethod(checkedId)
        if (paymentMethod == "Not selected") {
            Toast.makeText(context, "Select a payment method.", Toast.LENGTH_SHORT).show()
            return
        }

        val itemSummary = generateItemSummary(items)
        val total = calculateTotalPrice(items)
        val message = createWhatsAppMessage(name, phone, paymentMethod, itemSummary, total)

        launchWhatsApp(message, context)
    }



    fun getPaymentMethod(checkedId: Int): String {
        return when (checkedId) {
            R.id.rbCash -> "Cash"
            R.id.rbCard -> "Card"
            else -> "Not selected"
        }
    }

    fun generateItemSummary(items: List<GroupedFoodItem>): String {
        return items.joinToString("\n") { item ->
            "- ${item.name} x${item.quantity} @ R${item.price} each"
        }
    }

    fun calculateTotalPrice(items: List<GroupedFoodItem>): Double {
        return items.sumOf { it.price.toDouble() * it.quantity }
    }


    fun createWhatsAppMessage(
        name: String,
        phone: String,
        paymentMethod: String,
        itemSummary: String,
        total: Double
    ): String {
        return """
        *Fontis Fine Dine Order*
        
        👤 Name: $name
        📞 Phone: $phone
        💳 Payment: $paymentMethod

        🍔 Items:
        $itemSummary

        💰 Total: R%.2f
    """.trimIndent().format(total)
    }


    fun launchWhatsApp(message: String, context: CheckoutPageActivity) {
        //val phoneNumber = "27685202264" // replace with your business number
        val phoneNumber = "27793761586" // replace with your business number
        val url = "https://wa.me/$phoneNumber?text=" + Uri.encode(message)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }

}