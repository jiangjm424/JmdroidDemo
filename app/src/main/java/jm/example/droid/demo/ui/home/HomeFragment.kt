/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jm.example.droid.demo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import jm.droid.lib.samatadapter.ISmartAdapter
import jm.droid.lib.uibase.AbsBindingFragment
import jm.example.droid.demo.card.ModeCard1
import jm.example.droid.demo.card.TestCard
import jm.example.droid.demo.data.ModeTest1
import jm.example.droid.demo.databinding.FragmentHomeBinding

class HomeFragment : AbsBindingFragment<FragmentHomeBinding>() {

    private val dashboardViewModel by viewModels<HomeViewModel>()
    private val adapter by lazy {
        ISmartAdapter.Builder().register(String::class.java, TestCard()).register(ModeTest1::class.java, ModeCard1()).build()
    }

    override fun setupData(binding: FragmentHomeBinding, owner: LifecycleOwner) {
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            binding.textHome.text = it
        }
        adapter.add(listOf("1","2","3","4"))
        adapter.add(listOf(ModeTest1(1,"m1"), ModeTest1(2,"m2")))
    }

    override fun setupView(binding: FragmentHomeBinding, savedInstanceState: Bundle?) {
        val root: View = binding.root
        binding.recyclerView.adapter = adapter.adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
    }

    override fun onPageFirstComing() {

        Log.i("jiang", "$this first coming")
    }
}
