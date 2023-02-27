package com.othman.jassercommerce.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.othman.jassercommerce.R
import com.othman.jassercommerce.utils.Constants
import kotlinx.android.synthetic.main.dialog_add_estate_sld.*
import kotlinx.android.synthetic.main.dialog_add_estate_sld.view.*
import kotlin.math.roundToInt

abstract class AddEstateSliderDialog(context: Context, private var type: String): Dialog(context, R.style.Dialog_Theme) {

    private var isButtonPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_add_estate_sld)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        finishingButtonListenerSetup()
        sliderButtonListenerSetup()
        dialogTypeSetup()

    }

    private fun dialogTypeSetup() {
        if (type == Constants.AREA_DIALOG_TYPE) {
            sliderParameterSetup(1f, 1000f, 1f)
            sld_area_dialog.setLabelFormatter { value -> value.toInt().toString() + " م م" }
            tv_dialog_sld_title.text = context.resources.getString(R.string.dialog_title_area)
            ll_dialog_price_data.visibility = View.GONE
        } else {
            sliderParameterSetup(1f, 999.9f, 0.1f)
            tv_dialog_sld_title.text = context.resources.getString(R.string.dialog_title_price)
            editTextOptionsSetup(et_dialog_price_unit, Constants.priceUnitOptions)
            when (type) {
                Constants.SALE_PRICE_DIALOG_TYPE -> {
                    editTextOptionsSetup(et_dialog_price_type, Constants.salePriceTypeOptions)
                }
                Constants.RENT_PRICE_DIALOG_TYPE -> {
                    editTextOptionsSetup(et_dialog_price_type, Constants.rentPriceTypeOptions)
                }
                Constants.BET_PRICE_DIALOG_TYPE -> {
                    editTextOptionsSetup(et_dialog_price_type, Constants.betPriceTypeOptions)
                }
            }
        }
    }

    private fun sliderParameterSetup(from: Float , to: Float , step: Float){
        sld_area_dialog.value = 50f
        sld_area_dialog.valueFrom = from
        sld_area_dialog.valueTo = to
        sld_area_dialog.stepSize = step
    }

    private fun finishingButtonListenerSetup() {
        val clickListener = OnClickListener{
            if (it == btn_dialog_sld_done) {
                val unit = if (type == Constants.AREA_DIALOG_TYPE)
                    Constants.AREA_UNIT
                else
                    et_dialog_price_unit.text.toString() + " - " + et_dialog_price_type.text.toString()
                onDoneClicked(sld_area_dialog.value.correct(), unit.removeSuffix("- "))
            }
            dismiss()
        }
        btn_dialog_sld_done.setOnClickListener(clickListener)
        btn_dialog_sldr_cancel.setOnClickListener(clickListener)
    }

    private fun editTextOptionsSetup(autoCompleteTextView: AutoCompleteTextView, options: ArrayList<String>){
        val adapter = ArrayAdapter(context, R.layout.list_item, options)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setText(autoCompleteTextView.adapter.getItem(1).toString(), false)
        et_dialog_price_type.setOnItemClickListener { _, _, position, _ ->
            if (position == 0 && type == Constants.SALE_PRICE_DIALOG_TYPE){
                et_dialog_price_type.text.clear()
                et_dialog_price_type.clearFocus()
            }
        }
        et_dialog_price_unit.setOnItemClickListener{ parent, view, position, _ ->
            when(Constants.priceUnitOptions[position]){
                Constants.THOUSANDS_POUNDS -> {
                    sliderParameterSetup(10f, 995f, 5f)
                }
                Constants.MILLION_POUNDS -> {
                    sliderParameterSetup(1f, 999.9f, 0.1f)
                }
                Constants.BILLION_POUNDS -> {
                    sliderParameterSetup(1f, 100f, 0.05f)
                }
                Constants.DOLLARS -> {
                    sliderParameterSetup(1f, 999f, 1f)
                }
                Constants.THOUSANDS_DOLLARS -> {
                    sliderParameterSetup(1f, 999.95f, 0.05f)

                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun sliderButtonListenerSetup(){

        val clickListener = OnClickListener{
            sliderButtonChange(it)
        }
        btn_dialog_slider_plus.setOnClickListener(clickListener)
        btn_dialog_slider_minus.setOnClickListener(clickListener)


        val touchListener = OnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    isButtonPressed = true
                }
                MotionEvent.ACTION_UP -> {
                    isButtonPressed = false
                }
            }
            false
        }
        btn_dialog_slider_plus.setOnTouchListener(touchListener)
        btn_dialog_slider_minus.setOnTouchListener (touchListener)


        val longClickListener = OnLongClickListener {
            sliderButtonFastChanging(it)
            true
        }
        btn_dialog_slider_plus.setOnLongClickListener(longClickListener)
        btn_dialog_slider_minus.setOnLongClickListener(longClickListener)


    }

    private fun sliderButtonChange(btn: View?) {
        if (btn == btn_dialog_slider_plus && sld_area_dialog.value < sld_area_dialog.valueTo) {
            sld_area_dialog.value = (sld_area_dialog.value + sld_area_dialog.stepSize).correct()
        } else if (btn == btn_dialog_slider_minus && sld_area_dialog.value > sld_area_dialog.valueFrom) {
            sld_area_dialog.value = (sld_area_dialog.value - sld_area_dialog.stepSize).correct()
        }
    }

    private fun Float.correct(): Float {
       return ((this * 100).roundToInt())/100f
    }

    private fun sliderButtonFastChanging(view: View) {
        if (isButtonPressed){
            sliderButtonChange(view)
            Handler(Looper.myLooper()!!).postDelayed({
                sliderButtonFastChanging(view)
            },10)
        }
    }

    protected abstract fun onDoneClicked(value: Float, unit: String)

}