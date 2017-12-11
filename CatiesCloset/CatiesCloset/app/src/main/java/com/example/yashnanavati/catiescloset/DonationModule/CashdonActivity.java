package com.example.yashnanavati.catiescloset.DonationModule;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yashnanavati.catiescloset.Home.HomeActivity;
import com.example.yashnanavati.catiescloset.Model.Cash;
import com.example.yashnanavati.catiescloset.Model.Donations;
import com.example.yashnanavati.catiescloset.Model.Package;
import com.example.yashnanavati.catiescloset.R;
import com.example.yashnanavati.catiescloset.Util.PaymentsUtil;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardInfo;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.Stripe;
import com.stripe.android.model.Token;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CashdonActivity extends AppCompatActivity {

    //init Buttoms
    private Button btn25;
    private Button btn50;
    private Button btn100;
    private Button btn250;
    private Button btnOther;
    private Button btnSubmit;
    private EditText txtOther;
    private TextView txtValue;
    private TextView txtTotalValue;

    //init TextViews
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtAddress;
    private TextView txtCountry;
    private TextView txtState;
    private TextView txtZipcode;

    //Init Payment Variables
    private PaymentsClient mPaymentsClient;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("tag", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashdon);

        // It's recommended to create the PaymentsClient object inside of the onCreate method.
        mPaymentsClient = PaymentsUtil.createPaymentsClient(this);
        checkIsReadyToPay();

        //Buttons
        btn25 = (Button) findViewById(R.id.btn25);
        btn50 = (Button) findViewById(R.id.btn50);
        btn100 = (Button) findViewById(R.id.btn100);
        btn250 = (Button) findViewById(R.id.btn250);
        btnOther = (Button) findViewById(R.id.btnOther);
        btnSubmit = (Button) findViewById(R.id.btn_signup);
        txtOther = (EditText) findViewById(R.id.txtOther);
        txtValue = (TextView) findViewById(R.id.txtValue);
        txtTotalValue = (TextView) findViewById(R.id.txtTotalValue);

        //TextViews
        txtName = (TextView) findViewById(R.id.signup_input_name);
        txtEmail = (TextView) findViewById(R.id.signup_input_email);
        txtAddress = (TextView) findViewById(R.id.signup_input_address);
        txtCountry = (TextView) findViewById(R.id.signup_input_country);
        txtState = (TextView) findViewById(R.id.signup_input_state);
        txtZipcode = (TextView) findViewById(R.id.signup_input_zip);


        btn25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtValue.setText("$25");
                txtTotalValue.setText("$25");
            }
        });

        btn50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtValue.setText("$50");
                txtTotalValue.setText("$50");
            }
        });

        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtValue.setText("$100");
                txtTotalValue.setText("$100");
            }
        });

        btn250.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtValue.setText("$250");
                txtTotalValue.setText("$250");
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = txtOther.getText().toString();
                txtValue.setText("$" + value);
                txtTotalValue.setText("$" + value);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("tag", "btnSubmit");
                String name = txtName.getText().toString();
                String email = txtEmail.getText().toString();
                String address = txtAddress.getText().toString();
                String country = txtCountry.getText().toString();
                String state = txtState.getText().toString();
                String zipcode = txtZipcode.getText().toString();
                double amount = Double.parseDouble(txtValue.getText().toString().replace("$", ""));

                Cash cash = new Cash(0);
                cash.setCash(name, email, address, country, state, zipcode, amount);

                FirebaseDatabase.getInstance()
                        .getReference().child("donations").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "|"))
                        .push().setValue(new Donations(null, (new Timestamp(System.currentTimeMillis())).getTime(), new Package(0, false, null, null, null), cash));

                requestPayment(v);
            }
        });
    }

    private void checkIsReadyToPay() {
        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        PaymentsUtil.isReadyToPay(mPaymentsClient).addOnCompleteListener(
                new OnCompleteListener<Boolean>() {
                    public void onComplete(Task<Boolean> task) {
                        try {
                            boolean result = task.getResult(ApiException.class);
                            setPwgAvailable(result);
                        } catch (ApiException exception) {
                            // Process error
                            Log.w("isReadyToPay failed", exception);
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("*** Code:", String.valueOf(LOAD_PAYMENT_DATA_REQUEST_CODE));
        switch (requestCode) {
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        // You can get some data on the user's card, such as the brand and last 4 digits
                        CardInfo info = paymentData.getCardInfo();
                        // You can also pull the user address from the PaymentData object.
                        UserAddress address = paymentData.getShippingAddress();
                        // This is the raw JSON string version of your Stripe token.
                        String rawToken = paymentData.getPaymentMethodToken().getToken();
                        Log.d("*** TOKEN: ", rawToken);
                        // Now that you have a Stripe token object, charge that by using the id
                        try {
                            Token token = Token.fromString(rawToken);
                            new ProcessCharge().execute(token.getId()).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Transaction Completed!", Toast.LENGTH_LONG).show();
                        finish();
                        //Token stripeToken = Token .fromString(rawToken);
                        //if (stripeToken != null) {
                        // This chargeToken function is a call to your own server, which should then connect
                        // to Stripe's API to finish the charge.
                        //chargeToken(stripeToken.getId());
                        //}
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        // Log the status for debugging
                        // Generally there is no need to show an error to
                        // the user as the Google Payment API will do that
                        break;
                    default:
                        // Do nothing.
                }
                break; // Breaks the case LOAD_PAYMENT_DATA_REQUEST_CODE
            // Handle any other startActivityForResult calls you may have made.
            default:
                // Do nothing.
        }
    }

    private class ProcessCharge extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... token) {
            Stripe.apiKey = "sk_test_NktY45XxcVfSYOEyKeXm2CxH";

            //String token = request.getParameter("stripeToken");

            // Charge the user's card:
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("amount", Long.parseLong(txtTotalValue.getText().toString().substring(1)) * 100L);
            params.put("currency", "usd");
            params.put("description", "Catie's Closet Donation : " + new Date());
            params.put("source", token[0]);

            try {
                Charge charge = Charge.create(params);
            } catch (APIConnectionException e) {
                e.printStackTrace();
            } catch (InvalidRequestException e) {
                e.printStackTrace();
            } catch (AuthenticationException e) {
                e.printStackTrace();
            } catch (APIException e) {
                e.printStackTrace();
            } catch (CardException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Void result) {
            Log.d(" **** COMPLETED: ", "FINISHED");
        }
    }

    private void handlePaymentSuccess(PaymentData paymentData) {
        // PaymentMethodToken contains the payment information, as well as any additional
        // requested information, such as billing and shipping address.
        //
        // Refer to your processor's documentation on how to proceed from here.
        PaymentMethodToken token = paymentData.getPaymentMethodToken();

        // getPaymentMethodToken will only return null if PaymentMethodTokenizationParameters was
        // not set in the PaymentRequest.
        if (token != null) {
            String billingName = paymentData.getCardInfo().getBillingAddress().getName();
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_LONG).show();

            // Use token.getToken() to get the token string.
            Log.d("PaymentData", "PaymentMethodToken received");
        }
    }

    private void handleError(int statusCode) {
        // At this stage, the user has already seen a popup informing them an error occurred.
        // Normally, only logging is required.
        // statusCode will hold the value of any constant from CommonStatusCode or one of the
        // WalletERROR_CODE_*
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    // This method is called when the Pay with Google button is clicked.
    public void requestPayment(View view) {
        // Disables the button to prevent multiple clicks.
        btnSubmit.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
//        String price = PaymentsUtil.microsToString(Long.parseLong(amount.getText().toString()));
        String price = PaymentsUtil.microsToString(Long.parseLong(txtTotalValue.getText().toString().substring(1)));
        TransactionInfo transaction = PaymentsUtil.createTransaction(price);
        PaymentDataRequest request = PaymentsUtil.createPaymentDataRequest(transaction);
        Task<PaymentData> futurePaymentData = mPaymentsClient.loadPaymentData(PaymentsUtil.createPaymentDataRequest(transaction));

        futurePaymentData.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("**** EXP: ", e.getMessage());
//                Intent i = new Intent(getApplicationContext(), UserProfileActivity.class);
//                startActivity(i);
            }
        });

        futurePaymentData.addOnSuccessListener(new OnSuccessListener<PaymentData>() {
            @Override
            public void onSuccess(PaymentData paymentData) {
                Log.d("**** SUC: ", paymentData.getCardInfo().getCardDescription());
            }
        });

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        //Uncomment the line below this to
        AutoResolveHelper.resolveTask(futurePaymentData, this, LOAD_PAYMENT_DATA_REQUEST_CODE);
    }

    private void setPwgAvailable(boolean available) {
        // If isReadyToPay returned true, show the button and hide the "checking" text. Otherwise,
        // notify the user that Pay with Google is not available.
        // Please adjust to fit in with your current user flow. You are not required to explicitly
        // let the user know if isReadyToPay returns false.
        if (!available) {
            Toast.makeText(getApplicationContext(), "Unfortunately Google Pay is not available for this...", Toast.LENGTH_SHORT).show();
        }
    }

}


