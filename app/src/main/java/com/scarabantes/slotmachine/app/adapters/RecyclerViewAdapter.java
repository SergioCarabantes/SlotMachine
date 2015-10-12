/*
 * Copyright (C) 2015 Sergio Carabantes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scarabantes.slotmachine.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scarabantes.slotmachine.R;
import com.scarabantes.slotmachine.app.models.History;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<History> items = new ArrayList<>();
    private View v;

    public RecyclerViewAdapter() {}

    public void setData(List<History> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final History item = items.get(position);
        holder.wheelOne.setImageResource(item.getResWheelViewOne());
        holder.wheelTwo.setImageResource(item.getResWheelViewTwo());
        holder.wheelThree.setImageResource(item.getResWheelViewthree());
    }

    @Override public int getItemCount() {
        return items.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView wheelOne;
        public ImageView wheelTwo;
        public ImageView wheelThree;

        public ViewHolder(View itemView) {
            super(itemView);
            wheelOne = (ImageView) itemView.findViewById(R.id.wheel_one);
            wheelTwo = (ImageView) itemView.findViewById(R.id.wheel_two);
            wheelThree = (ImageView) itemView.findViewById(R.id.wheel_three);
        }
    }
}
