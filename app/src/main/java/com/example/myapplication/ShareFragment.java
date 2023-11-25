package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ShareFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        Button btnShareWhatsApp = view.findViewById(R.id.btnShareWhatsApp);
        Button btnShareFacebook = view.findViewById(R.id.btnShareFacebook);
        Button btnShareTwitter = view.findViewById(R.id.btnShareTwitter);

        btnShareWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareViaWhatsApp();
            }
        });

        btnShareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareViaFacebook();
            }
        });

        btnShareTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareViaTwitter();
            }
        });

        return view;
    }

    private void shareViaWhatsApp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome app!");

        if (isIntentAvailable(whatsappIntent)) {
            startActivity(whatsappIntent);
        } else {
            Toast.makeText(getActivity(), "WhatsApp not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareViaFacebook() {
        Intent facebookIntent = new Intent(Intent.ACTION_SEND);
        facebookIntent.setType("text/plain");
        facebookIntent.setPackage("com.facebook.katana");
        facebookIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome app!");

        if (isIntentAvailable(facebookIntent)) {
            startActivity(facebookIntent);
        } else {
            Toast.makeText(getActivity(), "Facebook not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareViaTwitter() {
        Intent twitterIntent = new Intent(Intent.ACTION_SEND);
        twitterIntent.setType("text/plain");
        twitterIntent.setPackage("com.twitter.android");
        twitterIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome app!");

        if (isIntentAvailable(twitterIntent)) {
            startActivity(twitterIntent);
        } else {
            Toast.makeText(getActivity(), "Twitter not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isIntentAvailable(Intent intent) {
        PackageManager packageManager = getActivity().getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }
}
