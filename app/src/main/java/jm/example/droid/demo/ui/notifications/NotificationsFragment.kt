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

package jm.example.droid.demo.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import jm.droid.lib.uibase.AbsBindingFragment
import jm.example.droid.demo.databinding.FragmentNotificationsBinding

class NotificationsFragment : AbsBindingFragment<FragmentNotificationsBinding>() {

    private val dashboardViewModel by viewModels<NotificationsViewModel>()
    override fun setupData(binding: FragmentNotificationsBinding, owner: LifecycleOwner) {
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            binding.textNotifications.text = it
        }
    }

    override fun setupView(binding: FragmentNotificationsBinding, savedInstanceState: Bundle?) {
        val root: View = binding.root
    }

    override fun onPageFirstComing() {

        Log.i("jiang", "$this first coming")
    }
}
