package com.yuly.noinguin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuly.noinguin.R
import com.yuly.noinguin.databinding.FragmentMyInformationBinding


class MyInformationFragment : Fragment() {

   val binding by lazy { FragmentMyInformationBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tv.text = "내정보.. 프레그먼트"
    }





}