package nz.co.test.transactions.activities

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.view.setPadding
import java.math.RoundingMode
import java.text.DecimalFormat

/* Main class for displaying table */

class TableMaker(var cont: Context, var table: TableLayout) {
    var transactions: MutableMap<String, Transaction> = LinkedHashMap()
    var row = TableRow(cont)
    var cell = TextView(cont)

    /* initializes the TableRow element */
    fun init_tableRow() {
        this.row = TableRow(this.cont)
        this.row.setBackgroundColor(Color.parseColor("#F0F7F7"))

        this.row.setGravity(Gravity.CENTER);
        this.row.setWeightSum(1f);
        this.row.setPadding(5)

        this.row.setLayoutParams(
            TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
        )
    }

    /* Adds a row to the TableRow with its attributes */
    fun addRow(txt:String,w:Int,textColor:Int) {
        cell = TextView(this.cont)
        cell.setTextColor(textColor)
        cell.width=w
        cell.setText(txt)
        this.row.addView(cell)
    }

    /* Here is the main loop that loads the entire rows of the table */
    fun fillRows() {
        transactions.entries.forEach {

            val objTrans=transactions[it.key]
            var rowColor= Color.GREEN
            this.init_tableRow()
            /* Changes the color and calculate the GST */
            if (objTrans != null) {
                if ( objTrans.debit.toDouble() >0) {
                    rowColor=Color.RED
                    objTrans.gst=calc_gst(objTrans.debit.toDouble())
                } else {                                     /* Remove this else block for GST on debit only */
                    objTrans.gst=calc_gst(objTrans.credit.toDouble())
                }

                /* This block adds all the TextView cells to the TableRow */
                this.addRow(objTrans.date.toString(), 99, rowColor)
                this.addRow(objTrans.summary.toString(), 99, rowColor)
                this.addRow(objTrans.debit.toString(), 10, rowColor)
                this.addRow(objTrans.credit.toString(), 10, rowColor)
                this.addRow(objTrans.gst, 10, rowColor)
            }

            /* Click event of the TableRow executes infoTras() each time you click at the row */
            this.row.setOnClickListener(View.OnClickListener {
                if (objTrans != null) infoTrans(cont,objTrans)
            })

            /* Adds the Row to the table */
            this.table.addView(
                this.row,
                TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }
    }
}