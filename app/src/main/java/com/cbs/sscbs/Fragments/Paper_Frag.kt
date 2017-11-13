package com.cbs.sscbs.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.cbs.sscbs.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.paper_fragment.*




/**
 * Created by gautam on 19/2/17.
 */

class Paper_Frag : Fragment() {


    internal var bundle: Bundle? = null
    var item : ArrayList<String> = ArrayList();


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.paper_fragment, container, false)

        activity.toolbar.setTitle("Question Papers")
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item.add("Semester 1")
        item.add("Semester 2")
        item.add("Semester 3")
        item.add("Semester 4")
        item.add("Semester 5")
        item.add("Semester 6")


//        bfia_card.setOnClickListener(View.OnClickListener {
//            val alert = AlertDialog.Builder(context)
//            alert.setTitle("Select Semester")
//            val inflater = layoutInflater
//            val alertLayout = inflater.inflate(R.layout.fragment_semester_frag, null)
//            alert.setView(alertLayout)
//            alert.setNegativeButton("Close") { dialog, id -> dialog.dismiss() }
//            alert.show()
//
//
//
//
//        })

        bfia_card.setOnClickListener {
            MaterialDialog.Builder(context)
                    .title("BFIA")
                    .items(item)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, which, text ->

                        when (which) {
                            0 -> Toast.makeText(activity, "Sem 1 selected", Toast.LENGTH_LONG).show()
                            1 -> Toast.makeText(activity, "Sem 1 selected", Toast.LENGTH_LONG).show()
                        }
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         */
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         */
                        true
                    })
                    .show()
            Toast.makeText(activity, "Card", Toast.LENGTH_SHORT).show()
        }

        bsc_card.setOnClickListener(View.OnClickListener {
            val alert = AlertDialog.Builder(context)
            alert.setTitle("Select Semester")
            val inflater = layoutInflater
            val alertLayout = inflater.inflate(R.layout.fragment_semester_frag, null)
            alert.setView(alertLayout)
            alert.setNegativeButton("Close") { dialog, id -> dialog.dismiss() }
            alert.show()
        })

        bms_card.setOnClickListener(View.OnClickListener {
            val alert = AlertDialog.Builder(context)
            alert.setTitle("Select Semester")
            val inflater = layoutInflater
            val alertLayout = inflater.inflate(R.layout.fragment_semester_frag, null)
            alert.setView(alertLayout)
            alert.setNegativeButton("Close") { dialog, id -> dialog.dismiss() }
            alert.show()
        })

    }


    companion object {

        fun newInstance(): Paper_Frag {
            val fragment = Paper_Frag()
            val bundle = Bundle()
            //bundle.putString(CONSTANT.fabintent,CONSTANT.fabintent);
            fragment.arguments = bundle
            return fragment
        }
    }

}
