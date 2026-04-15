import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private var allProducts = mutableListOf<Product>() // Your data source
    private var cartCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        setupData()
        setupRecyclerView()
        setupTouchHelper()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ProductAdapter(allProducts.toMutableList()) { product ->
            cartCount++
            invalidateOptionsMenu() // Refresh badge
            Snackbar.make(recyclerView, "${product.name} added to cart", Snackbar.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupTouchHelper() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                adapter.moveItem(vh.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                val pos = vh.adapterPosition
                val deletedItem = adapter.removeItem(pos)

                Snackbar.make(recyclerView, "Item deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") { adapter.insertItem(pos, deletedItem) }
                    .show()
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText ?: "")
                return true
            }
        })
        return true
    }

    private fun filter(query: String) {
        val filtered = allProducts.filter { it.name.contains(query, ignoreCase = true) }
        adapter.updateList(filtered)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_toggle_view) {
            adapter.isGridView = !adapter.isGridView
            recyclerView.layoutManager = if (adapter.isGridView) GridLayoutManager(this, 2) else LinearLayoutManager(this)
            adapter.notifyDataSetChanged()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupData() {
        // Initialize with dummy data
        allProducts.add(Product(1, "Laptop", 999.99, 4.5f, "Electronics", R.drawable.ic_launcher_foreground))
        allProducts.add(Product(2, "T-Shirt", 19.99, 4.0f, "Clothing", R.drawable.ic_launcher_foreground))
        // Add more...
    }
}