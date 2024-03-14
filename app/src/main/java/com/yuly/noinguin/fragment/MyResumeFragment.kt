package com.yuly.noinguin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.yuly.noinguin.R
import com.yuly.noinguin.databinding.FragmentMyResumeBinding
import com.yuly.noinguin.network.G

class MyResumeFragment : Fragment() {

   val binding by lazy { FragmentMyResumeBinding.inflate(layoutInflater) }
    lateinit var id:String
    lateinit var imgFile:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = G.userAccountItem?.id.toString()
        binding.etName.setText(id)
        val imgFile = "http://baechu10.dothome.co.kr/06NoinGuin/${G.userAccountItem?.imgFile}"
        Glide.with(requireContext()).load(imgFile).into(binding.iv)





    }





}