package com.habibfr.myflexiblefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DetailCategoryFragment : Fragment(), OnClickListener {
    var description: String? = null

    companion object {
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESC = "extra_desc"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtCategoryName: TextView = view.findViewById(R.id.tv_category_name)
        val txtCategoryDesc: TextView = view.findViewById(R.id.tv_category_description)

        val btnProfile: Button = view.findViewById(R.id.btn_profile)
        val btnShowDialog: Button = view.findViewById(R.id.btn_show_dialog)

        btnProfile.setOnClickListener(this)
        btnShowDialog.setOnClickListener(this)

        if (savedInstanceState != null) {
            val descFromBundle = savedInstanceState.getString(EXTRA_DESC)
            description = descFromBundle
        }

        if (arguments != null) {
            val categoryName = arguments?.getString(EXTRA_NAME)
            txtCategoryName.text = categoryName
            txtCategoryDesc.text = description
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_profile -> {

            }

            R.id.btn_show_dialog -> {
                val optionDialogFragment = OptionDialogFragment()

                val fragmentManager = childFragmentManager
                optionDialogFragment.show(
                    fragmentManager,
                    OptionDialogFragment::class.java.simpleName
                )
            }
        }
    }

    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener =
        object : OptionDialogFragment.OnOptionDialogListener {
            override fun onOptionChoosen(text: String?) {
                Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
            }
        }
}