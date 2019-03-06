package com.util.enumtest;

/**
 * @Auth wn
 * @Date 2018/12/29
 */
public interface Competitor<T extends Competitor<T>> {
    OutCome compete(T cometitor);
}
