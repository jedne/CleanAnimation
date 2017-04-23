package com.jeden.clean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jeden on 2017/4/21.
 */

public class RubbishFactory {
    public Rubbish generateRubbish(int width, int height) {
        Rubbish rb = new Rubbish();
        Random random = new Random();
        int randomType = random.nextInt(2);
        rb.x = 0;
        if(randomType == 1) {
            rb.x = width;
        }

        rb.y = random.nextInt(height / 2) + height / 4;

        rb.tension = (random.nextInt(10) / 10.0f) + 1;

        rb.rotateAngle = random.nextInt(360);

        rb.alpha = random.nextInt(55) + 20;
        return rb;
    }

    public List<Rubbish> generateRubbishs(int size, int width, int height) {

        if(size <= 0) {
            return null;
        }

        List<Rubbish> lists = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            lists.add(generateRubbish(width, height));
        }

        return lists;
    }
}
