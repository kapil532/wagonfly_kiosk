package kartify.ezetap.printer;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.util.Printer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cunoraz.gifview.library.GifView;
import com.mindorks.placeholderview.Utils;
import com.wagonfly.R;

import kartify.ezetap.Product_Pojo;
import kartify.ezetap.SplashScreenForRfid;

public class PrinterActivity extends Activity {

    public UsbAdmin mUsbAdmin = null;

    String s = "";
    String orderId, total_amount, tax, email, contact, customer_id, RFIDData, strTxnId, rrNumber, responseTx;
    ArrayList<Product_Pojo> listBen;
    Button yes_print, no_print;
    CheckView check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_screen);

        mUsbAdmin = new UsbAdmin(this);

        yes_print = (Button) findViewById(R.id.yes_print);
        yes_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (conTest()) {
                    try {
                        twoTimePrint();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Exception" + e.getMessage(), Toast.LENGTH_LONG).show();
                        openNextActivity();
                    }
                }

            }
        });
        no_print = (Button) findViewById(R.id.no_print);
        no_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (conTest()) {
                    try {
                        printInThread();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Exception" + e.getMessage(), Toast.LENGTH_LONG).show();
                        openNextActivity();
                    }
                }

            }
        });




      /*  Intent myData = getIntent();
        if (myData != null) {
            orderId = myData.getStringExtra("orderId");
            listBen= (ArrayList<Product_Pojo>)  myData.getSerializableExtra("array");
            total_amount = myData.getStringExtra("total_amount");
            tax = myData.getStringExtra("tax");
            email = myData.getStringExtra("email");
            txnId = myData.getStringExtra("txnId");
            rrNumber = myData.getStringExtra("rrNumber");
            contact = myData.getStringExtra("contact");
            customer_id = myData.getStringExtra("customer_id");
        }*/

        Intent myData = getIntent();
        if (myData != null) {
            orderId = myData.getStringExtra("orderId");
            listBen = (ArrayList<Product_Pojo>) myData.getSerializableExtra("array");
            total_amount = myData.getStringExtra("total_amount");
            tax = myData.getStringExtra("tax");
            strTxnId = myData.getStringExtra("strTxnId");
            rrNumber = myData.getStringExtra("rrNumber");
            responseTx = myData.getStringExtra("responseTx");
            email = myData.getStringExtra("email");
            contact = myData.getStringExtra("contact");
            RFIDData = myData.getStringExtra("RFIDData");
            customer_id = myData.getStringExtra("customer_id");
        }

       /* click.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (conTest()) {
                    s = s + "inside coetst";
                    if (PrintfData(billString("" + (Float.parseFloat(total_amount) - (Float.parseFloat(tax))), tax, total_amount))) {
                        s = s + "Printing";
                        byte SendCut[] = {0x0a, 0x0a, 0x1d, 0x56, 0x01};
                        if (PrintfData(SendCut)) {
                            finish();
                        } else {
                        }
                    }
                }

            }
        });
       */
        check = (CheckView) findViewById(R.id.check);
        check.check();

        //conTest();
        //  my.post(runn);

        //text.setText(s.toString());
    }
 /* Handler my= new Handler();

    Handler my2= new Handler();


    Runnable runn=  new Runnable() {
        @Override
        public void run() {

          conTest();

            my.postDelayed(runn,2000);
        }
    };

    Runnable runn2=  new Runnable() {
        @Override
        public void run() {
            printInThread();
            //my2.postDelayed(runn2,20000);
         //   my2.dump(Printer);

        }
    };
*/

    public boolean conTest() {
        mUsbAdmin.Openusb();
        if (!mUsbAdmin.GetUsbStatus()) {
            return false;
        } else {
            return true;
        }
    }

    /*public boolean conTest() {
        mUsbAdmin.Openusb();
        if (!mUsbAdmin.GetUsbStatus()) {
            return false;
        } else {
      my.removeCallbacks(runn);

            printInThread();
            // my2.post(runn2);
            return true;
        }
    }*/


    void printInThread() {
        byte[] data = (billString("" + (Float.parseFloat(total_amount) - (Float.parseFloat(tax))), tax, total_amount));

        if (PrintfData(data)) {


            byte SendCut[] = {0x0a, 0x0a, 0x1d, 0x56, 0x01};
            if (PrintfData(SendCut)) {
                openNextActivity();
            } else {

                openNextActivity();
            }
        } else {
            openNextActivity();
        }

    }


    void twoTimePrint() {
        byte[] data = (billString("" + (Float.parseFloat(total_amount) - (Float.parseFloat(tax))), tax, total_amount));

        if (PrintfData(data)) {


            byte SendCut[] = {0x0a, 0x0a, 0x1d, 0x56, 0x01};
            if (PrintfData(SendCut)) {
                if (PrintfData(data)) {

                    if (PrintfData(SendCut))
                    {
                        openNextActivity();
                    } else {
                        openNextActivity();
                    }
                } else {
                    openNextActivity();
                }

            } else {

                openNextActivity();
            }
        } else {
            openNextActivity();
        }

    }

    void openNextActivity() {
        Intent myInt = new Intent(this, SuccessActivity.class);
        myInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        myInt.putExtra("total_amount", total_amount);
        myInt.putExtra("tax", tax);
        myInt.putExtra("txnId", strTxnId);
        myInt.putExtra("rrNumber", rrNumber);
        myInt.putExtra("responseTx", responseTx);
        //orderID= ID
        myInt.putExtra("orderId", orderId);
        myInt.putExtra("email", email);
        myInt.putExtra("contact", contact);
        myInt.putExtra("RFIDData", RFIDData);
        myInt.putExtra("customer_id", customer_id);
        Bundle bun = new Bundle();
        bun.putSerializable("array", listBen);
        myInt.putExtras(bun);
        startActivity(myInt);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUsbAdmin.Closeusb();
    }

    public boolean PrintfData(byte[] data) {
        if (!mUsbAdmin.sendCommand(data)) {
            return false;
        } else {
            return true;
        }
    }

    byte[] printPhoto(int img) {
        byte[] command = null;
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            if (bmp != null) {
                command = kartify.ezetap.printer.Utils.decodeBitmap(bmp);
//				outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
//				printText(command);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
        return command;
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getReminingTime() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
        Date date = new Date();
        return dateFormat.format(date);
    }

    byte[] billString(String total_price, String tax, String grandTotal) {
        String BILL = "";

        BILL = "                    \n" +
                "     FR-EBO-WROGN-KRISHNA,\n" + "" +
                "      RETAILS-PHOENIX BANG" + "\n" +
                "        RETAILS INVOICE   \n\n" +
                "SHOP NO F-71A, FLR01,PHOENIX\n" +
                "MARKETCITY,\n" +
                "     WHITEFIELD MAIN ROAD, MAHADEVAPURA,\n" +
                "       BANGALORE - 560048  \n" +
                "GST STATE CODE 29\n" +
                "GST No 29AAOFK3168K1ZO" + "\n";
        BILL = BILL
                + "-----------------------------------------------\n";
        BILL = BILL
                + "invoice Details\n";
        BILL = BILL
                + "Bill No.        " + orderId + "\n";
        BILL = BILL
                + "Date            " + getDate() + "\n";
        BILL = BILL
                + "Time             " + getReminingTime() + "\n";
        BILL = BILL
                + "Type             " + "WagKiosk" + "\n";
        BILL = BILL
                + "Cstomer Details" + "\n";
        BILL = BILL
                + "" + email + "         " + "MOB" + " " + contact + "\n";


        BILL = BILL
                + "-----------------------------------------------\n";


        BILL = BILL + String.format("%1$-10s %2$4s %3$5s %4$5s %5$10s", "ITEM NAME", "SIZE", "QTY", "RATE", "AMOUNT");
        BILL = BILL + "\n";
        BILL = BILL
                + "-----------------------------------------------";


        for (int i = 0; i < listBen.size(); i++) {
            BILL = BILL + "\n " + String.format("%1$-10s %2$4s %3$5s %4$5s %5$10s", "" + listBen.get(i).getItem_name(), "" + listBen.get(i).getSize(), "" + listBen.get(i).getUnit(), "" + listBen.get(i).getManipulate_price(), Integer.parseInt(listBen.get(i).getUnit()) * Float.parseFloat(listBen.get(i).getManipulate_price()));
        }


        BILL = BILL
                + "\n-----------------------------------------------";
        BILL = BILL + "\n\n ";

        BILL = BILL + "                   SUBTOTAL:" + "      " + total_price + "" + "\n";
        BILL = BILL + "                        TAX:" + "      " + tax + " " + "\n";
        BILL = BILL + "                   GRAND TOTAL:" + "     " + grandTotal + " " + "\n";

        BILL = BILL
                + "-----------------------------------------------\n\n\n";
        BILL = BILL + "\n\n ";

        return concatByteArrays(new byte[]{0x1b, 'a', 0x01}, printPhoto(R.drawable.wrogn), BILL.getBytes());
    }

    public static byte[] concatByteArrays(byte[]... inputs) {
        int i = inputs.length - 1, len = 0;
        for (; i >= 0; i--) {
            len += inputs[i].length;
        }
        byte[] r = new byte[len];
        for (i = inputs.length - 1; i >= 0; i--) {
            System.arraycopy(inputs[i], 0, r, len -= inputs[i].length, inputs[i].length);
        }
        return r;
    }
}
