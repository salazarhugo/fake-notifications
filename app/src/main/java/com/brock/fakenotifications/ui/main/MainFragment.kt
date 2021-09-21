package com.brock.fakenotifications.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.brock.fakenotifications.R
import com.brock.fakenotifications.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    companion object {
        const val CHANNEL_ID = "34"
        fun newInstance() = MainFragment()
    }

    private val viewModel : MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = MainFragmentBinding.inflate(inflater, container, false).apply {
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()
        viewModel.appName.observe(viewLifecycleOwner, {
            binding.message.text = it
        })

        binding.apply {
            button.setOnClickListener {
                createNotification(textInputName.text.toString(),
                    textInputBody.text.toString())
            }
            button2.setOnClickListener {

                val sAgeFormat = resources.getString(R.string.app_name)
                val sFinalAge = String.format(sAgeFormat, "Hugo", "HUGOGF")
            }
        }
    }

    private fun createNotification(appName: String, bodyText: String = "") {
        // Create an explicit intent for an Activity in your app
//        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setContentTitle(appName)
//            .setContentText(bodyText)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            // Set the intent that will fire when the user taps the notification
////            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(requireContext())) {
//            // notificationId is a unique int for each notification that you must define
//            notify(2, builder.build())
//        }
        val remoteViews = RemoteViews(requireContext().packageName, R.layout.notif_custom_view)
        remoteViews.setTextViewText(
            R.id.text_appname,
            "Hugo"
//            context?.getString(if (true) R.string.app_name_alternative else R.string.app_name)
        )
        remoteViews.setTextViewText(R.id.text_title, "This is my title")
        remoteViews.setTextViewText(R.id.text_message, "This is my text")

        val notificationBuilder = NotificationCompat.Builder(
            requireContext(), CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setCustomContentView(remoteViews)
            .setColor(ContextCompat.getColor(requireContext(), R.color.purple_700))

        NotificationManagerCompat.from(requireContext()).notify(0, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, API 26+
        val name = "CHANNEL_NAME"//getString(R.string.channel_name)
        val descriptionText = "CHANNEL_DESCRIPTION"//getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager = requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}