package com.teamvan.assignment5problem2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.product_list_item.view.*

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val product = intent.getSerializableExtra("clickedProduct") as Product
        productDetailsTitleTV.text = product.title
        productDetailsItemIdTV.text = product.itemId
        productDetailsColorTV.text = product.color
        productDetailsDescriptionTV.text = product.description

        val imageName = product.imageName.split(".")[0]
        val imageUri = "@drawable/${imageName}"
        val imageResource = resources.getIdentifier(imageUri, null, packageName)
        val res = resources.getDrawable(imageResource)
        productDetailsImageIV.setImageDrawable(res)
    }
}