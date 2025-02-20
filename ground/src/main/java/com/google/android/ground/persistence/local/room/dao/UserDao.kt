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

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.google.android.ground.persistence.local.room.entity.UserEntity
import io.reactivex.Maybe

@Dao
interface UserDao : BaseDao<UserEntity> {

  @Transaction
  @Query("SELECT * FROM user WHERE id = :id")
  @Deprecated("Use findByIdSuspend")
  fun findById(id: String): Maybe<UserEntity>

  @Transaction
  @Query("SELECT * FROM user WHERE id = :id")
  // TODO(#1581): Rename to findById once all existing usages are migrated to coroutine.
  suspend fun findByIdSuspend(id: String): UserEntity?
}
