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

package com.scarabantes.slotmachine.app.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scarabantes.slotmachine.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AlertDialogFragment extends DialogFragment {

    @InjectView(R.id.title)
    TextView title;

    @InjectView(R.id.image)
    ImageView image;

    @InjectView(R.id.message)
    TextView message;


    public AlertDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static AlertDialogFragment newInstance(String title, int resIdImage, int resIdMessage) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("image", resIdImage);
        args.putInt("message", resIdMessage);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        ButterKnife.inject(this, view);

        String titleAlert = getArguments().getString("title");
        int resImageId = getArguments().getInt("image");
        int resMessageId = getArguments().getInt("message");

        title.setText(titleAlert);

        if(resImageId != 0) { // 0 = No image
            image.setImageResource(resImageId);
        }
        message.setText(resMessageId);
        return view;
    }

}
