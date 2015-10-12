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

package com.scarabantes.slotmachine.app.models;

/**
 * Created by scarabantes on 11/9/15.
 */
public class History {

    private int resWheelViewOne;
    private int resWheelViewTwo;
    private int resWheelViewthree;

    public History(int resWheelViewOne, int resWheelViewTwo, int wheelViewthree) {
        this.resWheelViewOne = resWheelViewOne;
        this.resWheelViewTwo = resWheelViewTwo;
        this.resWheelViewthree = wheelViewthree;
    }

    public int getResWheelViewOne() {
        return resWheelViewOne;
    }

    public void setResWheelViewOne(int resWheelViewOne) {
        this.resWheelViewOne = resWheelViewOne;
    }

    public int getResWheelViewTwo() {
        return resWheelViewTwo;
    }

    public void setResWheelViewTwo(int resWheelViewTwo) {
        this.resWheelViewTwo = resWheelViewTwo;
    }

    public int getResWheelViewthree() {
        return resWheelViewthree;
    }

    public void setResWheelViewthree(int resWheelViewthree) {
        this.resWheelViewthree = resWheelViewthree;
    }
}
