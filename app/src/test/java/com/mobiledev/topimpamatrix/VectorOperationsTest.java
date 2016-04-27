package com.mobiledev.topimpamatrix;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by larspmayrand on 4/26/16.
 */
public class VectorOperationsTest {

    private Vector zeroVector;
    private Vector vectorA;
    private Vector vectorB;
    private Vector unitXVector;
    private Vector unitYVector;

    @Before
    public void setUp() {
        zeroVector = new Vector(new double[]{0, 0});
        vectorA = new Vector(new double[]{1, 1});
        vectorB = new Vector(new double[]{2, 0});
        unitXVector = new Vector(new double[]{1, 0});
        unitYVector = new Vector(new double[]{0, 1});
    }

    @Test
    public void testGramSchmidtProcess() {
        Vector[] normalized = VectorOperations.gramSchmidt(vectorA, vectorB);
        Assert.assertTrue(Arrays.equals(normalized, new Vector[]{new Vector(new double[]{2, 1}), new Vector(new double[]{1, 2})})); // FIX!
    }

}
