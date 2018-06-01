package com.onwordiesquire.android.getyourreviews.ui.inputReview


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onwordiesquire.android.getyourreviews.OnNavigationEventListener
import com.onwordiesquire.android.getyourreviews.R
import kotlinx.android.synthetic.main.fragment_input.*
import org.koin.android.architecture.ext.viewModel

class InputFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = InputFragment()
    }

    private val viewModel by viewModel<InputViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubmitButton()
        setupEventObservation()
    }

    private fun setupEventObservation() {
        viewModel.navigationEventLiveData.observe(this, Observer {
            (activity as OnNavigationEventListener).pop()
        })
    }

    private fun setupSubmitButton() {
        searchBtn.setOnClickListener {
            viewModel.onSubmitData(
                    ReviewSubmission(
                            title = titleInput.textValue,
                            message = messageInput.textValue,
                            reviewerName = authorInput.textValue)
            )
        }
    }
}

private fun CharSequence.toDouble(): Double? = this.toString().toDoubleOrNull()
private val TextInputLayout.textValue
    get() = editText?.text.toString()