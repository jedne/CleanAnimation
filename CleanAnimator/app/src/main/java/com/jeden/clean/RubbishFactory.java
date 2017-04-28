package com.jeden.clean;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jeden on 2017/4/21.
 */

public class RubbishFactory {
    public Rubbish generateRubbish(int index, int width, int height) {
        Rubbish rb = new Rubbish();
        Random random = new Random();
        int randomType = random.nextInt(4);
        rb.x = -60;
        if(randomType == 0) {
            rb.x = 1200;
        }

        rb.y = random.nextInt(height);

        rb.tension = (random.nextInt(5) / 10.0f) + 1;

        rb.rotateAngle = random.nextInt(360);

        rb.alpha = random.nextInt(125) + 50;

        int temp = index % 4 + 1;
        rb.startTime = random.nextInt(5) * 50 + temp * 500;

        rb.isRecycled = false;

        rb.locationAngle = 0;

        Log.v("jeden", "RubbishFactory rb x:" + rb.x + " y:" + rb.y + " tension:" + rb.tension + " rotateAngle:" + rb.rotateAngle
        + " alpha:" + rb.alpha + " startTime:" + rb.startTime);
        return rb;
    }

    public List<Rubbish> generateRubbishs(int size, int width, int height) {

        if(size <= 0) {
            return null;
        }

        List<Rubbish> lists = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            lists.add(generateRubbish(i, width, height));
        }

        return lists;
    }
}
