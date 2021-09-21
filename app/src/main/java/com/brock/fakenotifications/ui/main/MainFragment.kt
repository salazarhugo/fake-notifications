package com.brock.fakenotifications.ui.main

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.brock.fakenotifications.R


class MainFragment : Fragment() {

    companion object {
        const val CHANNEL_ID = "34"
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
//    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        binding = MainFragmentBinding.inflate(inflater, container, false).apply {
//        }

        return ComposeView(requireContext()).apply {
            setContent {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()
        viewModel.appName.observe(viewLifecycleOwner, {
            binding.message.text = it
        })

        binding.apply {
            button.setOnClickListener {
                createNotification(
                    textInputName.text.toString(),
                    textInputBody.text.toString()
                )
            }
            button2.setOnClickListener {

                val sAgeFormat = resources.getString(R.string.app_name)
                val sFinalAge = String.format(sAgeFormat, "Hugo", "HUGOGF")
            }
        }
    }

    @Composable
    fun Notification(appName: String, title: String) {
        Column {
            Text(appName)
            Text(title)
        }
    }

    private fun createNotification(appName: String, bodyText: String = "") {
        val remoteViews = RemoteViews(requireContext().packageName, R.layout.notif_instagram)

        remoteViews.setTextViewText(
            R.id.text_appname,
            "Instagram"
//            context?.getString(if (true) R.string.app_name_alternative else R.string.app_name)
        )

//        remoteViews.setTextViewText(R.id.text_title, "Serial console enabled")
//        remoteViews.setTextViewText(R.id.text_message, "Performance is impacted. To disable, check bootloader.")
        val snoozeIntent = Intent(requireContext(), Activity::class.java).apply {
            action = ""
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }
        val pendingIntent =
            PendingIntent.getActivity(
                requireContext(),
                1,
                snoozeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val notificationBuilder = NotificationCompat.Builder(
            requireContext(), CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_insta)
            .addAction(R.drawable.ic_insta, getString(R.string.action_like), pendingIntent)
            .addAction(R.drawable.ic_insta, getString(R.string.action_reply), pendingIntent)
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
        val notificationManager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}