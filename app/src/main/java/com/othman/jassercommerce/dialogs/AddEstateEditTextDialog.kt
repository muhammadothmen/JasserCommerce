package com.othman.jassercommerce.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.othman.jassercommerce.R
import kotlinx.android.synthetic.main.dialog_add_estate_et.*

abstract class AddEstateEditTextDialog(context: Context, private val title: String): Dialog(context, R.style.Dialog_Theme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_add_estate_et)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

       tv_dialog_et_title.append(" $title")
       til_dialog.hint = title

        finishingButtonListenerSetup()

    }


    override fun dismiss() {
        super.dismiss()
        if (et_dialog.text.isNullOrEmpty()){
            onCancelClicked()
        }
    }



    private fun finishingButtonListenerSetup() {
        val clickListener = View.OnClickListener {
            if (it == btn_dialog_et_done) {
                if (et_dialog.text.isNullOrEmpty()){
                    Toast.makeText(context, "رجاءً أدخل المعلومات" ,Toast.LENGTH_SHORT).show()
                }else{
                    onDoneClicked(et_dialog.text.toString())
                    dismiss()
                }
            }else{
                dismiss()
            }

        }
        btn_dialog_et_done.setOnClickListener(clickListener)
        btn_dialog_et_cancel.setOnClickListener(clickListener)    }

    protected abstract fun onDoneClicked(data: String)
    protected abstract fun onCancelClicked()

}