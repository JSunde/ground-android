/*
 * Copyright 2021 Google LLC
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

package com.google.android.gnd.ui.syncstatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gnd.databinding.SyncStatusListItemBinding;
import com.google.android.gnd.model.Mutation;
import com.google.android.gnd.model.feature.FeatureMutation;
import com.google.common.collect.ImmutableList;
import java.text.DateFormat;

class SyncStatusListAdapter extends RecyclerView.Adapter<SyncStatusViewHolder> {

  private ImmutableList<Mutation> mutations;
  private final DateFormat dateFormat;
  private final DateFormat timeFormat;

  SyncStatusListAdapter(Context context) {
    this.mutations = ImmutableList.of();
    this.dateFormat = android.text.format.DateFormat.getDateFormat(context);
    this.timeFormat = android.text.format.DateFormat.getTimeFormat(context);
  }

  @NonNull
  @Override
  public SyncStatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new SyncStatusViewHolder(
        SyncStatusListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(SyncStatusViewHolder viewHolder, int position) {
    // TODO: i18n; add user friendly names.
    // TODO: Use data binding.
    // TODO(#876): Improve L&F and layout.

    Mutation mutation = mutations.get(position);
    String text =
        new StringBuilder()
            .append(mutation.getType().toString())
            .append(' ')
            .append(mutation instanceof FeatureMutation ? "Feature" : "Observation")
            .append(' ')
            .append(dateFormat.format(mutation.getClientTimestamp()))
            .append(' ')
            .append(timeFormat.format(mutation.getClientTimestamp()))
            .append('\n')
            .append("Sync ")
            .append(mutation.getSyncStatus().toString())
            .toString();
    viewHolder.binding.syncStatusText.setText(text);
    viewHolder.position = position;
  }

  @Override
  public int getItemCount() {
    return mutations.size();
  }

  void update(ImmutableList<Mutation> offlineBaseMaps) {
    this.mutations = offlineBaseMaps;
    notifyDataSetChanged();
  }
}
