package com.rubix.transformer.util;

import com.rubix.transformer.pojo.Transformation;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Created by sudhir.m on 18/11/16.
 */
@Component
public class TransformationComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Transformation transformation1 = (Transformation) o1;
        Transformation transformation2 = (Transformation) o2;

        // FIXME: 18/11/16
        return transformation1.getSource().getName().compareTo(transformation2.getSource().getName());
    }
}
