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

package com.scarabantes.slotmachine.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.scarabantes.slotmachine.R;
import com.scarabantes.slotmachine.app.adapters.AbstractWheelAdapter;
import com.scarabantes.slotmachine.app.adapters.RecyclerViewAdapter;
import com.scarabantes.slotmachine.app.fragments.AlertDialogFragment;
import com.scarabantes.slotmachine.app.models.History;
import com.scarabantes.slotmachine.app.widget.OnWheelScrollListener;
import com.scarabantes.slotmachine.app.widget.WheelView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.rv)
    RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;
    private ArrayList<History> list = new ArrayList<>();

    private int wheelsFinishedScrolling = 0;
    private Map<Integer, String> products = new HashMap<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        initWheel(R.id.slot_1);
        initWheel(R.id.slot_2);
        initWheel(R.id.slot_3);

        Button mix = (Button)findViewById(R.id.btn_go);
        mix.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mixWheel(R.id.slot_1);
                mixWheel(R.id.slot_2);
                mixWheel(R.id.slot_3);
            }
        });

        initProducts();
        initRecyclerView();
        setRecyclerAdapter();
    }

    private void initProducts() {
        products.put(R.drawable.fruittype_avocado, getString(R.string.text_avocado));
        products.put(R.drawable.fruittype_burrito, getString(R.string.text_burrito));
        products.put(R.drawable.fruittype_skeleton, getString(R.string.text_skeleton));
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setRecyclerAdapter() {
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    // Wheel scrolled flag
    private boolean wheelScrolled = false;

    // Wheel scrolled listener
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }
        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            wheelsFinishedScrolling ++;

            if(wheelsFinishedScrolling == 3) {
                wheelsFinishedScrolling = 0;
                addToHistory();

                if(test()) {
                    int valueDrawable = getWheel(R.id.slot_1).getViewAdapter()
                            .getImageFromResource(getWheel(R.id.slot_1).getCurrentItem());
                    showDialog(getString(R.string.text_winner_title, products.get(valueDrawable)),
                            getWheel(R.id.slot_1).getViewAdapter()
                                    .getImageFromResource(getWheel(R.id.slot_1).getCurrentItem()),
                            R.string.text_winner_message);
                } else {
                    showDialog(getString(R.string.text_try_again_title) , 0, R.string.text_try_again_message);
                }
            }
        }
    };

    /**
     * Initializes wheel
     * @param id the wheel com.scarabantes.slotmachine.app.widget Id
     */
    private void initWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.setViewAdapter(new SlotMachineAdapter(this));
        wheel.setCurrentItem((int) (Math.random() * 10));
        wheel.setVisibleItems(3);
        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setEnabled(false);

    }

    /**
     * Returns wheel by Id
     * @param id the wheel Id
     * @return the wheel with passed Id
     */
    private WheelView getWheel(int id) {
        return (WheelView) findViewById(id);
    }

    /**
     * Tests wheels
     * @return true
     */
    private boolean test() {
        int value = getWheel(R.id.slot_1).getCurrentItem();
        return testWheelValue(R.id.slot_2, value) && testWheelValue(R.id.slot_3, value);
    }

    private void addToHistory() {
        WheelView wheelViewOne = getWheel(R.id.slot_1);
        WheelView wheelViewTwo = getWheel(R.id.slot_2);
        WheelView wheelViewThree = getWheel(R.id.slot_3);

        int resWheelViewOne = wheelViewOne.getViewAdapter().getImageFromResource(wheelViewOne.getCurrentItem());

        int resWheelViewTwo = wheelViewTwo.getViewAdapter().getImageFromResource(wheelViewTwo.getCurrentItem());

        int resWheelViewThree = wheelViewThree.getViewAdapter().getImageFromResource(wheelViewThree.getCurrentItem());

        History history = new History(resWheelViewOne, resWheelViewTwo, resWheelViewThree);
        list.add(history);
        adapter.setData(list);
    }

    private void showDialog(String title, int resIdImage, int resIdMessage) {
        FragmentManager fm = getSupportFragmentManager();
        AlertDialogFragment dialog = AlertDialogFragment.newInstance(title, resIdImage, resIdMessage);
        dialog.show(fm, "fragment_edit_name");
    }

    /**
     * Tests wheel value
     * @param id the wheel Id
     * @param value the value to test
     * @return true if wheel value is equal to passed value
     */
    private boolean testWheelValue(int id, int value) {
        return getWheel(id).getCurrentItem() == value;
    }

    /**
     * Mixes wheel
     * @param id the wheel id
     */
    private void mixWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.scroll(-350 + (int)(Math.random() * 50), 2000);
    }

    /**
     * Slot machine adapter
     */
    private class SlotMachineAdapter extends AbstractWheelAdapter {
        // Image size
        final int IMAGE_WIDTH = 120;
        final int IMAGE_HEIGHT = 72;

        // Slot machine symbols
        private final int items[] = new int[] {
                R.drawable.fruittype_avocado,
                R.drawable.fruittype_burrito,
                R.drawable.fruittype_skeleton
        };

        // Cached images
        private List<SoftReference<Bitmap>> images;

        // Layout inflater
        private Context context;

        /**
         * Constructor
         */
        public SlotMachineAdapter(Context context) {
            this.context = context;
            images = new ArrayList<SoftReference<Bitmap>>(items.length);
            for (int id : items) {
                images.add(new SoftReference<Bitmap>(loadImage(id)));
            }
        }

        /**
         * Loads image from resources
         */
        private Bitmap loadImage(int id) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true);
            bitmap.recycle();
            return scaled;
        }

        @Override
        public int getItemsCount() {
            return items.length;
        }

        // Layout params for image view
        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            ImageView img;
            if (cachedView != null) {
                img = (ImageView) cachedView;
            } else {
                img = new ImageView(context);
            }
            img.setLayoutParams(params);
            SoftReference<Bitmap> bitmapRef = images.get(index);
            Bitmap bitmap = bitmapRef.get();
            if (bitmap == null) {
                bitmap = loadImage(items[index]);
                images.set(index, new SoftReference<Bitmap>(bitmap));
            }
            img.setImageBitmap(bitmap);

            return img;
        }

        @Override
        public int getImageFromResource(int value){
            return items[value];

        }
    }
}