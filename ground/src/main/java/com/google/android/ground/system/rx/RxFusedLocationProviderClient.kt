/*
 * Copyright 2020 Google LLC
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
package com.google.android.ground.system.rx

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.ground.system.channel.LocationSharedFlowCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

/** Thin wrapper around [FusedLocationProviderClient] exposing key LOIs as reactive streams. */
class RxFusedLocationProviderClient @Inject constructor(@ApplicationContext context: Context) {
  private val fusedLocationProviderClient: FusedLocationProviderClient

  init {
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
  }

  @SuppressLint("MissingPermission")
  suspend fun requestLocationUpdates(
    locationRequest: LocationRequest,
    locationCallback: LocationSharedFlowCallback
  ) {
    fusedLocationProviderClient
      .requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
      .await()
  }

  suspend fun removeLocationUpdates(locationCallback: LocationSharedFlowCallback) {
    fusedLocationProviderClient.removeLocationUpdates(locationCallback).await()
  }
}
