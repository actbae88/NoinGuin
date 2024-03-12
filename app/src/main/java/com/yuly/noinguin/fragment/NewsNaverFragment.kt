package com.yuly.noinguin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuly.noinguin.R
import com.yuly.noinguin.databinding.FragmentNewsNaverBinding


class NewsNaverFragment : Fragment() {

    private val binding by lazy { FragmentNewsNaverBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.text = "뉴스스스슷스 네이버          프레프레프레"
    }

}