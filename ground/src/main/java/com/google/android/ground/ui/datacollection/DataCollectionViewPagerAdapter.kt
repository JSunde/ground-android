/*
 * Copyright 2022 Google LLC
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
package com.google.android.ground.ui.datacollection

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.ground.model.task.Task
import com.google.common.collect.ImmutableList
import java.lang.IllegalStateException

/**
 * A simple pager adapter that presents the [Task]s associated with a given Submission, in sequence.
 */
class DataCollectionViewPagerAdapter(fragment: Fragment, private val tasks: ImmutableList<Task>) :
  FragmentStateAdapter(fragment) {
  override fun getItemCount(): Int = tasks.size

  override fun createFragment(position: Int): Fragment {
    val task = tasks[position]
    return when (task.type) {
      Task.Type.TEXT -> QuestionDataCollectionFragment(task)
      else -> throw IllegalStateException("No TaskFragment found for task type: ${task.type}")
    }
  }
}
