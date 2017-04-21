package com.avazu.testplugin.TestViewPager.recyclerview;

/**
 * @author carl
 */
public interface SnappyScrollCalculator {
    int computeScrollToItemIndex(int velocityX, int velocityY);
}
