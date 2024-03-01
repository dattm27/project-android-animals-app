package com.example.prm391x_project_2_dattmfx12040;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prm391x_project_2_dattmfx12040.R;

import java.io.IOException;

public class PhoneStateEmojiReceiver extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent)  {
        mContext = context;
        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) { //Neu co cuoc goi den
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                // Check if the number is valid
                if (incomingNumber != null && !incomingNumber.isEmpty()) {
                    try {
                        makeEmojiToast(mContext, incomingNumber);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //Tao Toast hinh anh con vat khi co cuoc goi toi
    private void makeEmojiToast(Context mContext, String incomingNumber)  throws IOException {
        //Inflate layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_toast, (ViewGroup) null);

        //Anh xa view
        ImageView ivToast = layout.findViewById(R.id.ivIconToast);

        //Goi Preferences
        SharedPreferences sharedPref = mContext.getSharedPreferences("PHONE_TO_PATH", Context.MODE_PRIVATE);
        String photoPath = sharedPref.getString(incomingNumber,"");

        // Neu SDT ton tai
        Log.i("Make Emoji Toast", photoPath);
        if (!photoPath.equals("")){
            // Gan anh vao Toast
            ivToast.setImageBitmap(BitmapFactory.decodeStream(mContext.getAssets().open(photoPath)));

            //Tao va hien Toast
            Toast toast = new Toast(mContext);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();

        }
    }
}
