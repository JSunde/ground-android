/*
 * Copyright 2019 Google LLC
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
package com.google.android.ground.persistence.local.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import io.reactivex.Completable

/**
 * Base interface for DAOs that implement operations on a specific entity type.
 *
 * @param <E> the type of entity that is persisted by sub-interfaces. </E>
 */
interface BaseDao<E> {

  /** Insert entity into local db. Main-safe. */
  @Insert suspend fun insert(entity: E)

  /** Update entity in local db. Main-safe. */
  @Update suspend fun update(entity: E): Int

  @Update suspend fun updateAll(entities: List<E>)

  @Deprecated("Replace usage with deleteSuspend") @Delete fun delete(entity: E): Completable

  // TODO(#1581): Rename to delete once all existing usages are migrated to coroutine.
  @Delete suspend fun deleteSuspend(entity: E)
}

/** Try to update the specified entity, and if it doesn't yet exist, create it. Main-safe. */
suspend fun <E> BaseDao<E>.insertOrUpdate(entity: E) {
  val count = update(entity)
  if (count == 0) {
    insert(entity)
  }
}
