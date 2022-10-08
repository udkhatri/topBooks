package  com.uday.topbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.uday.topbooks.databinding.ActivityMainBinding
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var textViewCopyright:TextView
    lateinit var recyclerView: RecyclerView
    lateinit var books:List<Books>

    lateinit var recyclerViewAdapter: RecyclerView.Adapter<*>
    lateinit var recyclerViewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initialize binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setup variables
        textViewCopyright = findViewById(R.id.textViewCopyright)
        recyclerView = findViewById(R.id.recyclerView)

        books = listOf<Books>()
        // setup recyclerView
        recyclerViewManager = LinearLayoutManager(applicationContext)
        recyclerView.setLayoutManager(recyclerViewManager)
        recyclerView.setHasFixedSize(true)

        // launch URL call on thread
        getBookData().start()
    }

    // call the Book API
    private fun getBookData(): Thread
    {
        return Thread {
            val url = URL("https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=T2EAcub58TIt7xmggIdm3XwbmsPbHMWl")
            val connection  = url.openConnection() as HttpsURLConnection
            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, APIFormat::class.java)
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                binding.textViewCopyright.text = "Failed Connection"
            }
        }
    }

    // update UI through binding
    private fun updateUI(request: APIFormat){
        runOnUiThread{
            kotlin.run {
                binding.textViewCopyright.text = request.copyright
                binding.recyclerView.adapter = RecyclerAdapter(request.results?.books!!)
//                binding.tvDate.text = request.days?.get(0)?.datetime
//                binding.tvCurrent.text = String.format("Current Temperature: %.1f",request.currentConditions.temp)
//                binding.tvMax.text = String.format("Maximum Temperature: %.1f",request.days?.get(0)?.tempmax)
//                binding.tvMin.text = String.format("Minimum Temperature: %.1f",request.days?.get(0)?.tempmin)
//                binding.tvPrecip.text = String.format("POP: %.1f",request.days?.get(0)?.precipprob)
//                binding.tvDescrip.text = "Description: " + request.days?.get(0)?.description
            }
        }
    }

}