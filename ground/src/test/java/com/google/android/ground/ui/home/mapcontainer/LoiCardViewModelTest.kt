/*
 * Copyright 2023 Google LLC
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
package com.google.android.ground.ui.home.mapcontainer

import com.google.android.ground.BaseHiltTest
import com.google.android.ground.ui.home.mapcontainer.cards.LoiCardViewModel
import com.jraska.livedata.TestObserver
import com.sharedtest.FakeData
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
class LoiCardViewModelTest : BaseHiltTest() {

  @Test
  fun testLoiNameWithPoint_whenCaptionIsNull() {
    val viewModel = LoiCardViewModel(TEST_LOI.copy(caption = null))

    TestObserver.test(viewModel.loiName).assertValue("POINT")
  }

  @Test
  fun testLoiNameWithPolygon_whenCaptionIsNull() {
    val viewModel = LoiCardViewModel(TEST_AREA.copy(caption = null))

    TestObserver.test(viewModel.loiName).assertValue("POLYGON")
  }

  @Test
  fun testLoiName_whenCaptionIsAvailable() {
    val viewModel = LoiCardViewModel(TEST_LOI.copy(caption = "some value"))

    TestObserver.test(viewModel.loiName).assertValue("some value")
  }

  @Test
  fun testLoiJobName_whenNameIsNull() {
    val job = TEST_LOI.job.copy(name = null)
    val viewModel = LoiCardViewModel(TEST_LOI.copy(job = job))

    TestObserver.test(viewModel.loiJobName).assertValue(null)
  }

  @Test
  fun testLoiJobName_whenNameIsAvailable() {
    val job = TEST_LOI.job.copy(name = "job name")
    val viewModel = LoiCardViewModel(TEST_LOI.copy(job = job))

    TestObserver.test(viewModel.loiJobName).assertValue("job name")
  }

  companion object {
    private val TEST_LOI = FakeData.LOCATION_OF_INTEREST.copy(caption = null)
    private val TEST_AREA = FakeData.AREA_OF_INTEREST.copy(caption = null)
  }
}
