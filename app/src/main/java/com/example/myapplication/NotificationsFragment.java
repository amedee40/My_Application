package com.example.myapplication;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class NotificationsFragment extends Fragment {

    public static final String BUS_FULL_CHANNEL = "Bus Full Channel";
    public static final String BUS_DELAYED_CHANNEL = "Bus Delayed Channel";
    private NotificationManagerCompat notificationManagerCompat;
    Button btnFull, btnDelay;
    Notification notificationReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationManagerCompat = NotificationManagerCompat.from(requireContext());
        btnDelay = view.findViewById(R.id.btnDelay);
        btnFull = view.findViewById(R.id.btnFull);

        // Create notification channels
        createChannels();

        btnDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDelayedNotification();
            }
        });

        btnFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFullNotification();
            }
        });
    }

    private void createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel busFullChannel = new NotificationChannel(BUS_FULL_CHANNEL,
                    "Bus Full Channel", NotificationManager.IMPORTANCE_HIGH);

            busFullChannel.setDescription("Bus Full");

            NotificationChannel busDelayedChannel = new NotificationChannel(BUS_DELAYED_CHANNEL,
                    "Bus Delayed Channel", NotificationManager.IMPORTANCE_HIGH);

            busDelayedChannel.setDescription("Bus Delayed");

            NotificationManager notificationManager = requireActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(busFullChannel);
            notificationManager.createNotificationChannel(busDelayedChannel);
        }
    }

    @SuppressLint("MissingPermission")
    private void sendFullNotification() {
        Notification notification = new NotificationCompat.Builder(requireContext(), BUS_FULL_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Bus Full")
                .setContentText("Please note the bus is at capacity")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        Intent intent = new Intent(MainActivity.NotificationReceiver.ACTION_NOTIFICATION_RECEIVED);
        intent.putExtra("content", "Please note the bus is at capacity");
        requireContext().sendBroadcast(intent);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            return;
        }
        notificationManagerCompat.notify(1, notification);
    }

    @SuppressLint("MissingPermission")
    private void sendDelayedNotification() {
        Notification notification = new NotificationCompat.Builder(requireContext(), BUS_DELAYED_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Bus Delayed")
                .setContentText("Please note we're experiencing delays")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        Intent intent = new Intent(MainActivity.NotificationReceiver.ACTION_NOTIFICATION_RECEIVED);
        intent.putExtra("content", "Please note we're experiencing delays");
        requireContext().sendBroadcast(intent);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            return;
        }
        notificationManagerCompat.notify(2, notification);
    }
}
