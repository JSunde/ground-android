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

package com.google.android.ground.persistence.remote.firebase.schema

import com.google.android.ground.model.locationofinterest.LocationOfInterest
import com.google.android.ground.model.submission.Submission
import com.google.android.ground.persistence.remote.firebase.base.FluentCollectionReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query

class SubmissionCollectionReference internal constructor(ref: CollectionReference) :
  FluentCollectionReference(ref) {

  fun submission(id: String) = SubmissionDocumentReference(reference().document(id))

  suspend fun submissionsByLocationOfInterestId(
    locationOfInterest: LocationOfInterest
  ): List<Submission> =
    runQuery(byLoiId(locationOfInterest.id)) {
      SubmissionConverter.toSubmission(locationOfInterest, it)
    }

  private fun byLoiId(loiId: String): Query =
    reference().whereEqualTo(FieldPath.of(SubmissionMutationConverter.LOI_ID), loiId)
}
