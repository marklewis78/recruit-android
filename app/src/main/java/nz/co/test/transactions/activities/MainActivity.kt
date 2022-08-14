package nz.co.test.transactions.activities

import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import nz.co.test.transactions.R
import java.text.DecimalFormat

/* GST Calculator */
fun calc_gst(number:Double): String {
    var gst=number*0.15
    var df=DecimalFormat("#.##")
    return df.format(gst)
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* getting TableLayout from activity_main.xml */
        val table = findViewById(R.id.listTran) as TableLayout

        /* clear the socket error of strict policy */
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        /* transactions instance */
        var transactions=Transactions()
        transactions.url="https://gist.githubusercontent.com/Josh-Ng/500f2716604dc1e8e2a3c6d31ad01830/raw/4d73acaa7caa1167676445c922835554c5572e82/test-data.json";
        transactions.loadData()

        /* Transactions table instance */
        var transactionsTable = TableMaker(this,table);
        transactionsTable.transactions=transactions.TransactionsSorted
        transactionsTable.fillRows()
    }
}
