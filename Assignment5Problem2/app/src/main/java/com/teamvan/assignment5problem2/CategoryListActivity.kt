package com.teamvan.assignment5problem2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.product_list_item.view.*


class CategoryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)

        val products = arrayListOf(
            Product("Sport Band Compatible with Apple", 9.99, "Black", "watch.jpg", "23", "Soft Silicone Wristbands Replacement Strap with Classic Clasp for iWatch Series SE 8 7 6 5 4 3 2 1 Utral for Women Men, Black"),
            Product("JBL Clip 4 - Portable Mini Bluetooth Speaker", 49.95, "Red", "jbl_speaker.jpg", "45", "Big audio and punchy bass, integrated carabiner, IP67 waterproof and dustproof, 10 hours of playtime, speaker for home, outdoor and travel - (Red)"),
            Product("Amazon Halo View fitness tracker", 59.99, "Green", "halo_view.jpg", "99", "Fitness tracker, with color display for at-a-glance access to heart rate, activity, and sleep tracking – Sage Green - Medium/Large"),
            Product("Kindle Paperwhite (8 GB)", 139.99, "Black", "kindle.jpg", "243", "Kindle Paperwhite (8 GB) – Now with a 6.8\" display and adjustable warm light"),
            Product("SAMSUNG Galaxy S22 Ultra", 999.99, "Burgundy", "galaxys22.jpg", "83", "SAMSUNG Galaxy S22 Ultra Cell Phone, Factory Unlocked Android Smartphone, 256GB, 8K Camera & Video, Brightest Display Screen, S Pen, Long Battery Life, Fast 4nm Processor, US Version, Burgundy"),
            Product("Wireless Mouse, TECKNET Pro 2.4G", 9.59, "Blue", "mouse.jpg", "22", "Ergonomic Wireless Optical Mouse with USB Nano Receiver for Laptop,PC,Computer,Chromebook,Notebook,6 Buttons,24 Months Battery Life, 2600 DPI, 5 Adjustment Levels")
        )
        categoriesListRV.layoutManager = LinearLayoutManager(this)
        val adapter = MyAdapter(products)
        categoriesListRV.adapter = adapter
    }

    inner class MyAdapter(val productsList: ArrayList<Product>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.productTitleTv.text = productsList[position].title
            holder.itemView.productPriceTV.text = "$${productsList[position].price}"
            holder.itemView.productColorTV.text = productsList[position].color

            val imageName = productsList[position].imageName.split(".")[0]
            val imageUri = "@drawable/${imageName}"
            val imageResource = resources.getIdentifier(imageUri, null, packageName)
            val res = resources.getDrawable(imageResource)
            holder.itemView.productImageIV.setImageDrawable(res)
            holder.itemView.setOnClickListener {
                val intent = Intent(this@CategoryListActivity, ProductDetailsActivity::class.java)
                intent.putExtra("clickedProduct", productsList[position] as java.io.Serializable)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return productsList.size
        }

    }
}
