package com.adityaikhbalm.pricehunter

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.adityaikhbalm.pricehunter.custom.CustomDrawable
import com.adityaikhbalm.pricehunter.databinding.ActivityMainBinding
import com.adityaikhbalm.pricehunter.databinding.CategoryLayoutBinding
import com.adityaikhbalm.pricehunter.model.Product
import com.adityaikhbalm.pricehunter.utils.Utility.backgroundNeon
import com.adityaikhbalm.pricehunter.utils.Utility.convertDpToPixel
import com.adityaikhbalm.pricehunter.utils.Utility.getMyColor
import com.adityaikhbalm.pricehunter.utils.Utility.showIt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.chip.Chip
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingChip: CategoryLayoutBinding

    private lateinit var chip: List<Chip>
    private var chipBackground = ArrayList<CustomDrawable>()
    private lateinit var bottomNavigationMenuView: BottomNavigationMenuView
    private lateinit var v: View
    private lateinit var itemView: BottomNavigationItemView
    private lateinit var active: View
    private var isFirst = false
    private val productAdapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingChip = CategoryLayoutBinding.bind(binding.root)
        setContentView(binding.root)

        setBanner()
        setCategory()
        setBottomMenu()
        setRecyclerView()
        setProduct()
    }

    private fun setRecyclerView() {
        binding.recyclerView.run {
            setHasFixedSize(false)
            adapter = productAdapter
            addItemDecoration(ItemOffsetDecoration(10))
        }
    }

    private fun setProduct() {
        val data = ArrayList<Product>()
        val image = listOf(
            "https://d29c1z66frfv6c.cloudfront.net/pub/media/catalog/product/large/1bc092270e32a8a740216436db1c33d356e6c6e9_xxl-1.jpg",
            "https://static.zara.net/photos///2021/I/0/2/p/6224/302/251/2/w/1280/6224302251_6_1_1.jpg?ts=1620373735423",
            "https://d29c1z66frfv6c.cloudfront.net/pub/media/catalog/product/large/a48cdc7458604da6582c7b5757601fbd08513985_xxl-1.jpg",
            "https://d29c1z66frfv6c.cloudfront.net/pub/media/catalog/product/large/b21ae9120acc5d346ef9c1107cdc1a7491669947_xxl-1.jpg",
            "https://d29c1z66frfv6c.cloudfront.net/pub/media/catalog/product/large/d0fbe7fdc5f1c34066344ca6b311e73592a04419_xxl-1.jpg",
            "https://d29c1z66frfv6c.cloudfront.net/pub/media/catalog/product/large/f226ffe80d47b2a11b750f2b48e979a082ebdf33_xxl-1.jpg",
            "https://static.zara.net/photos///2021/I/0/2/p/4087/485/251/2/w/1280/4087485251_6_1_1.jpg?ts=1620809045603",
            "https://d29c1z66frfv6c.cloudfront.net/pub/media/catalog/product/large/39d3cff1de75742d1a7cb7e135c3d3112beb2312_xxl-1.jpg",
            "https://d29c1z66frfv6c.cloudfront.net/pub/media/catalog/product/large/20b03bbf5eb1aa23ea7a557a241c11b2260388d2_xxl-1.jpg",
            "https://static.zara.net/photos///2021/V/0/2/p/0526/408/183/2/w/1280/0526408183_6_1_1.jpg?ts=1618505034897"
        )

        val name = listOf(
            "Stranger Things",
            "Tetris",
            "Disney",
            "Rolling Stones",
            "AC/DC",
            "Nirvana",
            "Letter W",
            "Regular Gray Misty",
            "Regular Gray",
            "Jacquard Strip"
        )

        val price = listOf(
            "Rp 149.000",
            "Rp 139.000",
            "Rp 149.000",
            "Rp 189.900",
            "Rp 189.900",
            "Rp 149.900",
            "Rp 139.900",
            "Rp 79.000",
            "Rp 79.000",
            "Rp 169.900"
        )

        for (i in 0 until 10) {
            val d = Product(image[i], name[i], price[i])
            data.add(d)
        }
        Log.d("wtfwtf",data.toString())

        productAdapter.submitList(data)
    }

    private fun setBanner() {
        Glide
            .with(this)
            .load("https://s3.bukalapak.com/trend-qword/ad-inventory/1623/s-581-245/HomeBanner_Desktop_%5B1668x704%5D-1620444509429.jpg.webp")
            .transform(RoundedCorners(15.convertDpToPixel))
            .into(binding.bannerImage)
    }

    private fun setCategory() {
        val colors = resources.getIntArray(R.array.color_neon)

        chip = listOf(
            bindingChip.chip1,
            bindingChip.chip2,
            bindingChip.chip3,
            bindingChip.chip4,
            bindingChip.chip5
        )

        val r = listOf(
            intArrayOf(getMyColor(R.color.p), getMyColor(R.color.p)),
            intArrayOf(getMyColor(R.color.b), getMyColor(R.color.b)),
            intArrayOf(getMyColor(R.color.t), getMyColor(R.color.t)),
            intArrayOf(getMyColor(R.color.m), getMyColor(R.color.m)),
            intArrayOf(getMyColor(R.color.r), getMyColor(R.color.r))
        )

        chip.forEachIndexed { index, chip ->
//            colors.shuffle()
            chipBackground.add(chip.backgroundNeon(r[index], 10, 35))
        }

        chipBackground[0].changeColor(getMyColor(R.color.p))

        bindingChip.category.setOnCheckedChangeListener { _, checkedId ->
            setBackgroundChip(checkedId)
        }
    }

    private fun setBackgroundChip(checkedId: Int) {
        chip.forEachIndexed { index, chip ->
            if (checkedId == chip.id) {
                chipBackground[index].changeColor(getMyColor(R.color.p))
                bindingChip.scrollLayoutChip.smoothScrollTo(
                    chip.left - chip.paddingLeft, chip.top
                )
            }
            else chipBackground[index].changeColor(Color.TRANSPARENT)
        }
    }

    private fun setBottomMenu() {
        bottomNavigationMenuView = binding.bottomMenu.getChildAt(0) as BottomNavigationMenuView
        setMenu(0)

        binding.bottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> setMenu(0)
                R.id.navigation_popular -> setMenu(1)
                R.id.navigation_favorite -> setMenu(2)
                else -> null
            } != null
        }
    }

    private fun setMenu(position: Int) {
        if (isFirst) itemView.removeViewInLayout(active)

        v = bottomNavigationMenuView.getChildAt(position) as View
        itemView = v as BottomNavigationItemView
        active = LayoutInflater.from(this).inflate(R.layout.home_icon, binding.bottomMenu, false)
        when (position) {
            0 -> active.findViewById<ImageView>(R.id.home).showIt()
            1 -> active.findViewById<ImageView>(R.id.popular).showIt()
            else -> active.findViewById<ImageView>(R.id.profile).showIt()
        }
        itemView.addView(active)
        isFirst = true
    }

    inner class ItemOffsetDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.set(
                space.convertDpToPixel, 0, 0, space.convertDpToPixel
            )
        }
    }
}