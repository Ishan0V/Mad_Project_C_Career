package com.ivpy.career.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ivpy.career.R
import com.ivpy.career.databinding.FragmentHomeBinding
import com.ivpy.career.firestore.FirestoreClass
import com.ivpy.career.model.Mentor
import com.ivpy.career.ui.adapter.ItemAdapter

class HomeFragment : BaseFragment() {

    //private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
*/
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
  /*      homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        textView.text="Home page."
        return root
    }

    fun successMentorListFromFireStoreALL(mentorList :ArrayList<Mentor>){
        hideProgress()
        if (mentorList.isNotEmpty()){
            view?.findViewById<RecyclerView>(R.id.rv_home_item)?.visibility= View.VISIBLE
            view?.findViewById<TextView>(R.id.text_home)?.visibility= View.GONE
            view?.findViewById<RecyclerView>(R.id.rv_home_item)?.layoutManager= GridLayoutManager(activity,2)
            val adapterMentor = ItemAdapter(requireActivity(),mentorList)
            view?.findViewById<RecyclerView>(R.id.rv_home_item)?.adapter=adapterMentor
        }
    }

    fun getMentorListFromFireStoreAll(){
        showProgress()
        FirestoreClass().getMentorListALL(this@HomeFragment)
    }

    override fun onResume() {
        super.onResume()
        getMentorListFromFireStoreAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}