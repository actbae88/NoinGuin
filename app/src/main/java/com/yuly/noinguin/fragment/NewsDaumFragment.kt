package com.yuly.noinguin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuly.noinguin.R
import com.yuly.noinguin.databinding.FragmentNewsDaumBinding


class NewsDaumFragment : Fragment() {

    private val binding by lazy { FragmentNewsDaumBinding.inflate(layoutInflater) }

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

        binding.tv.text = "dddddd다음   뉴스  프레프레프레"
    }

}