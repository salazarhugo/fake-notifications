package com.brock.fakenotifications.ui.main.newnotification

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brock.fakenotifications.R

class NewNotificationFragment : Fragment() {

    companion object {
        fun newInstance() = NewNotificationFragment()
    }

    private lateinit var viewModel: NewNotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_notification_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewNotificationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}