package com.ivpy.career.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ivpy.career.AddProductActivity
import com.ivpy.career.R
import com.ivpy.career.databinding.FragmentDashboardBinding
import com.ivpy.career.firestore.FirestoreClass
import com.ivpy.career.model.Mentor
import com.ivpy.career.ui.adapter.ItemAdapter

class DashboardFragment : BaseFragment() {

    //private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)*/

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    // END

    // TODO Step 6: Override the onOptionsItemSelected function and handle the actions of items.
    // START
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_add_product) {
            // TODO Step 8: Launch the add product activity.
            // START
            startActivity(Intent(activity, AddProductActivity::class.java))
            // END
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun successMentorListFromFireStore(mentorList :ArrayList<Mentor>){
        hideProgress()
        if (mentorList.isNotEmpty()){
            view?.findViewById<RecyclerView>(R.id.rv_mentor_items)?.visibility= View.VISIBLE
            view?.findViewById<TextView>(R.id.tv_no_data)?.visibility= View.GONE
            view?.findViewById<RecyclerView>(R.id.rv_mentor_items)?.layoutManager= GridLayoutManager(activity,2)
            val adapterMentor = ItemAdapter(requireActivity(),mentorList)
            view?.findViewById<RecyclerView>(R.id.rv_mentor_items)?.adapter=adapterMentor
        }
    }

    fun getMentorListFromFireStore(){
        showProgress()
        FirestoreClass().getMentorList(this@DashboardFragment)
    }

    override fun onResume() {
        super.onResume()
        getMentorListFromFireStore()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}