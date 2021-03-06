package com.app.stority.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.app.stority.R
import com.app.stority.databinding.DialogAddDataBinding
import com.app.stority.helper.Logger
import com.app.stority.helper.afterTextChanged
import com.app.stority.homeSpace.data.HomeSpaceTable
import com.app.stority.homeSpace.owner.fragment.HomeSpaceFragment.Companion.ACTION_CANCEL


class AddDataDailog(
    @get:JvmName("getContext_") val context: Context,
    private var data: HomeSpaceTable? = HomeSpaceTable(),
    private val action: Int,
    private var dataBindingComponent: DataBindingComponent,
    private val onSaveCallback: ((HomeSpaceTable?, Int) -> Unit),
    private val onCancelCallback: ((Int) -> Unit)
) : Dialog(context, R.style.EditTextDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.inflate<DialogAddDataBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_add_data,
            null,
            false,
            dataBindingComponent
        ).also { bind ->
            setContentView(bind.root)
            bind.data = data

            bind.saveButton.setOnClickListener {
                bind?.data?.let {

                    onSaveCallback.invoke(data, action)
                    this.dismiss()
                }
            }
            bind.cancelButton.setOnClickListener {
                this.dismiss()
                onCancelCallback.invoke(ACTION_CANCEL)
            }

            bind.categoryEt.afterTextChanged {

                bind.categoryEt.layout?.let {
                    if (it.lineCount > 2) {
                        Logger.e(
                            Thread.currentThread(),
                            "line count ${it.lineCount}"
                        )
                        bind.categoryEt.text.delete(
                            bind.categoryEt.text.length - 1,
                            bind.categoryEt.text.length
                        )
                    }
                }

            }


            this.setCanceledOnTouchOutside(false)
        }
    }


}