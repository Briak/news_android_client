package com.briak.newsclient.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.briak.newsclient.R
import com.briak.newsclient.extensions.onClick
import kotlinx.android.synthetic.main.dialog_fragment_error.*

class ErrorDialogFragment: MvpAppCompatDialogFragment() {
    private lateinit var description: String

    companion object {
        fun getInstance(description: String): ErrorDialogFragment {
            val fragment = ErrorDialogFragment()
            val bundle = Bundle()
            bundle.putString("description", description)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, 0)

        description = arguments?.getString("description")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.dialog_fragment_error, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        descriptionView.text = description

        okView.onClick {
            dismiss()
        }
    }
}