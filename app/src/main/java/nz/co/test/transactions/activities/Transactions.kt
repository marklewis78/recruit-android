package nz.co.test.transactions.activities

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import nz.co.test.transactions.R
import org.json.JSONArray
import java.net.URL

/* Transaction object definition */
class Transaction {
    var id = 0
    var date=""
    var time=""
    var summary =""
    var debit = 0.0000
    var credit = 0.0000
    var gst= "0.0000"
}

/* Displays dialog with the information of the transaction*/
fun infoTrans(cont:Context,objTransact:Transaction) {
    val builder = AlertDialog.Builder(cont)
    with(builder)
    {
            var msg = "Trans. id:" + objTransact.id + "\n"
            msg += "Date:" + objTransact.date + "\n"
            msg += "Time:" + objTransact.time + "\n"
            msg += "Summary:" + objTransact.summary + "\n"
            msg += "Credit:" + objTransact.credit + "\n"
            msg += "Debit:" + objTransact.debit + "\n"
            msg += "GST:" + objTransact.gst + "\n"

            setTitle("Transaction Detail")
            setMessage(msg)
            show()
    }
}

class Transactions {
    var url=""
    val TransactionsMap: MutableMap<String, Transaction> = HashMap()
    val TransactionsSorted: MutableMap<String, Transaction> = LinkedHashMap()

    fun sortTransactions() {
        this.TransactionsMap.entries.sortedBy { it.key }.forEach { this.TransactionsSorted[it.key] =
            it.value
        }
    }
    fun getTransObjects(jsonStr:String) {
        var trJSONArray = JSONArray(jsonStr)

        (0 until trJSONArray.length()).forEach {
            val tObj = trJSONArray.getJSONObject(it)

            var transactionObject=Transaction()
            var td=tObj.getString("transactionDate")      /* Sorted by Date/time */
            var strDateTime=td.split('T')            /* Split Date Time */
            transactionObject.date=strDateTime[0]               /* 0 for date */
            transactionObject.time=strDateTime[1]               /* 1 for time */
            transactionObject.id=tObj.getInt("id")
            transactionObject.summary=tObj.getString("summary")
            transactionObject.debit=tObj.getDouble("debit")
            transactionObject.credit=tObj.getDouble("credit")
            transactionObject.gst="0.0000"
            this.TransactionsMap[td]=transactionObject
        }
    }

    fun loadData() {
        val json = URL(this.url).readText()
        this.getTransObjects(json)
        this.sortTransactions()
    }
}