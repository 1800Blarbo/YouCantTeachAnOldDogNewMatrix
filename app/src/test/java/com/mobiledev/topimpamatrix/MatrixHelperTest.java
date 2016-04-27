package com.mobiledev.topimpamatrix;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by larspmayrand on 4/24/16.
 */
public class MatrixHelperTest {

    private Complex64F[] complexNumbers;
    private DenseMatrix64F identity;
    private CDenseMatrix64F complexIdentity;
    private CDenseMatrix64F complexMatrix;
    private CDenseMatrix64F realMatrix;
    private CDenseMatrix64F complexVector;
    private CDenseMatrix64F realVector;
    private CDenseMatrix64F complexNumber;
    private CDenseMatrix64F realNumber;

    @Before
    public void setup() {
        Complex64F[] complexNumbers = new Complex64F[]{new Complex64F(1, 1), new Complex64F(1, 1)};
        DenseMatrix64F identity = new DenseMatrix64F(new double[][]{{1, 0},{0, 1}});
        CDenseMatrix64F complexIdentity = new CDenseMatrix64F(new double[][]{{1, 0, 0, 0},{0, 0, 1, 0}});
        CDenseMatrix64F complexMatrix = new CDenseMatrix64F(new double[][]{{1, 1, 1, 1},{1, 1, 1, 1}});
        CDenseMatrix64F realMatrix = new CDenseMatrix64F(new double[][]{{1, 0, 1, 0},{1, 0, 1, 0}});
        CDenseMatrix64F complexVector = new CDenseMatrix64F(new double[][]{{1, 1, 1, 1},{}});
        CDenseMatrix64F realVector = new CDenseMatrix64F(new double[][]{{1, 0, 1, 0},{}});
        CDenseMatrix64F complexNumber = new CDenseMatrix64F(new double[][]{{1, 1},{}});
        CDenseMatrix64F realNumber = new CDenseMatrix64F(new double[][]{{1, 0},{}});
    }

    @Test
    public void testMakeComplex() {
        Assert.assertTrue(MatrixHelper.makeComplex(identity).equals(complexIdentity));
    }

    @Test
    public void testMakeReal() {
        Assert.assertTrue(MatrixHelper.makeReal(realMatrix).equals(new DenseMatrix64F(new double[][]{{1, 1}, {1, 1}})));
    }

    @Test
    public void testClassify() {
        Assert.assertEquals(MatrixHelper.classify(MatrixHelper.makeComplex(identity)), "complex matrix");
        Assert.assertEquals(MatrixHelper.classify(complexMatrix), "complex matrix");
        Assert.assertEquals(MatrixHelper.classify(realMatrix), "real matrix");
        Assert.assertEquals(MatrixHelper.classify(complexVector), "complex vector");
        Assert.assertEquals(MatrixHelper.classify(realVector), "real vector");
        Assert.assertEquals(MatrixHelper.classify(complexNumber), "complex number");
        Assert.assertEquals(MatrixHelper.classify(realNumber), "real number");
    }

    @Test
    public void testMakeVector() {
        Assert.assertTrue(MatrixHelper.makeVector(complexVector).equals(new ComplexVector(complexNumbers)));
    }

    @Test
    public void testIsReal() {
        Assert.assertTrue(MatrixHelper.isReal(realMatrix));
    }

    @Test
    public void testIsSquare() {
        Assert.assertTrue(MatrixHelper.isSquare(complexMatrix));
        Assert.assertTrue(MatrixHelper.isSquare(realMatrix));
    }

    @Test
    public void testTrace() {
        Assert.assertTrue(MatrixHelper.trace(complexMatrix).equals(new Complex64F(4, 4)));
    }

    @Test
    public void testIsPrime() {
        int[] primes = new int[]{2, 3, 5, 7, 1721, 1723, 1733};
        for (int prime : primes) {
            Assert.assertTrue(MatrixHelper.isPrime(prime));
        }
        Assert.assertFalse(MatrixHelper.isPrime(20));
    }

    @Test
    public void testTwinPrime() {
        int[] twins = new int[]{3, 29, 59, 347};
        for (int twin : twins) {
            Assert.assertTrue(MatrixHelper.isTwin(twin));
        }
        Assert.assertFalse(MatrixHelper.isTwin(0));
        Assert.assertFalse(MatrixHelper.isTwin(2));
        Assert.assertFalse(MatrixHelper.isTwin(23));
    }

}
