package com.cbs.sscbs.Fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.cbs.sscbs.R
import com.cbs.sscbs.web
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



        bfia_card.setOnClickListener {
            MaterialDialog.Builder(context)
                    .title("BFIA")
                    .items(item)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, which, text ->



                        when(which)
                        {
                            0-> {
                                val bundle = Bundle()
//                                val url = "https://www.facebook.com"
//                            bundle.putString("urlString", url)
//                                val intent = Intent(context, web::class.java)
//                            intent.putExtras(bundle)
//                                    startActivity(intent)

                                val intent: Intent = Intent(context, web::class.java)
                                intent.putExtra("url" , "https://www.facebook.com")
                                startActivity(intent)

                            }
//
                        }



//                        val bundle = Bundle()
//                        val url = "http://www.google.com"
//                        bundle.putString("urlString", url)
//                        val intent = Intent(context, web::class.java)
//                        intent.putExtras(bundle)
//                        startActivity(intent)

                        val intent: Intent = Intent(context, web::class.java)
                        intent.putExtra("url" , "https://www.google.com")
                       startActivity(intent)


                        true
                    })
                    .show()
        }

        bsc_card.setOnClickListener {
            MaterialDialog.Builder(context)
                    .title("BFIA")
                    .items(item)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, which, text ->

                        val intent: Intent = Intent(context, web::class.java)
                       // intent.putExtra(CONSTANTS.imageurl, url)
                        startActivity(intent)

                        true
                    })
                    .show()
        }

        bms_card.setOnClickListener {
            MaterialDialog.Builder(context)
                    .title("BFIA")
                    .items(item)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, which, text ->

                        val intent: Intent = Intent(context, web::class.java)
                        // intent.putExtra(CONSTANTS.imageurl, url)
                        startActivity(intent)

                        true
                    })
                    .show()

        }


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
