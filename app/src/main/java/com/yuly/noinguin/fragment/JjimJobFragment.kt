package com.yuly.noinguin.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuly.noinguin.activities.MainActivity
import com.yuly.noinguin.databinding.FragmentJjimJobBinding


class JjimJobFragment : Fragment() {

    lateinit var binding:FragmentJjimJobBinding







    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJjimJobBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }//onViewCreated






}//fragment