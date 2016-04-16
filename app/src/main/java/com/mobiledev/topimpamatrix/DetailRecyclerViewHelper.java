package com.mobiledev.topimpamatrix;

import org.ejml.alg.dense.misc.CTransposeAlgs;
import org.ejml.data.CDenseMatrix64F;
import org.ejml.data.Complex64F;
import org.ejml.data.ComplexPolar64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CCommonOps;
import org.ejml.ops.CMatrixFeatures;
import org.ejml.ops.MatrixFeatures;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by larspmayrand on 4/11/16.
 */
public class DetailRecyclerViewHelper {

    /**
     * Classifies inputted complex matrix as complex/real, matrix/vector/number.
     * Then calls the appropriate getDetails method
     */
    public static Detail[] getDetails(CDenseMatrix64F matrix) {
        if (MatrixHelper.isReal(matrix)) {
            DenseMatrix64F realMatrix = MatrixHelper.makeReal(matrix);
            if (matrix.numCols == 1 || matrix.numRows == 1) {
                double[] realVector = MatrixHelper.makeVector(realMatrix);
                if (realVector.length == 1) {
                    return getRealNumberDetails(realVector[0]);
                }
                return getRealVectorDetails(realVector);
            }
            return getRealMatrixDetails(realMatrix);
        }
        if (matrix.numCols == 1 || matrix.numRows == 1) {
            Complex64F[] imaginaryVector = MatrixHelper.makeVector(matrix);
            if (imaginaryVector.length == 1) {
                return getImaginaryNumberDetails(imaginaryVector[0]);
            }
            return getImaginaryVectorDetails(imaginaryVector);
        }
        return getImaginaryMatrixDetails(matrix);
    }

    public static Detail[] getImaginaryMatrixDetails(CDenseMatrix64F matrix) {
        Detail[] details = new Detail[8];

        CDenseMatrix64F transposed = matrix.copy();
        CDenseMatrix64F conjugate = matrix.copy();
        if (matrix.numCols == matrix.numRows) { // if matrix is square
            CTransposeAlgs.square(transposed);
            CTransposeAlgs.squareConjugate(conjugate);
        } else {
            CTransposeAlgs.standard(matrix, transposed);
            CTransposeAlgs.standardConjugate(matrix, conjugate);
        }
        details[0] = new Detail("Transpose", FormatHelper.matrixToString(transposed));
        details[1] = new Detail("Conjugate", FormatHelper.matrixToString(conjugate));

        details[] = new Detail("Determinant", FormatHelper.complexToString(CCommonOps.det(matrix)) + "");

        CDenseMatrix64F inverse = matrix.copy();
        CCommonOps.invert(inverse);
        details[2] = new Detail("Inverse", FormatHelper.matrixToString(inverse));

        //details[] = new Detail("Real part", FormatHelper.matrixToString())




        details[8] = new Detail("Trace", FormatHelper.complexToString(MatrixHelper.trace(matrix)));go
        details[5] = new Detail("Hermitian", FormatHelper.booleanToString(CMatrixFeatures.isHermitian(matrix, 1e-8)));
        details[6] = new Detail("Identity", FormatHelper.booleanToString(CMatrixFeatures.isIdentity(matrix, 1e-8)));
        details[7] = new Detail("Positive definite", FormatHelper.booleanToString(CMatrixFeatures.isPositiveDefinite(matrix)));

        details[9] = new Detail("Unitary", FormatHelper.booleanToString(CMatrixFeatures.isUnitary(matrix, 1e-8)));
        details[10] = new Detail("Square", FormatHelper.booleanToString(MatrixHelper.isSquare(matrix)));
        return details;
    }

    public static Detail[] getRealMatrixDetails(DenseMatrix64F matrix) {
        if (MatrixFeatures.isVector(matrix)) {
            return getRealVectorDetails(MatrixHelper.makeVector(matrix));
        }
        SimpleMatrix simple = SimpleMatrix.wrap(matrix);
//        SimpleEVD eigenstuff = simple.eig();
//        int numEigenvalues = eigenstuff.getNumberOfEigenvalues();
//        Complex64F[] eigenvalues = new Complex64F[numEigenvalues];
//        //<T>[] eigenvectors = new T[numEigenvalues];
//        for (int i = 0; i < numEigenvalues; i++) {
//            eigenvalues[i] = eigenstuff.getEigenvalue(i);
//            //eigenvectors[]
//        }
//        String eigenvalues = "";
//        String eigenvectors = "";
//        int numEigenvalues = simple.eig().getNumberOfEigenvalues();
//        for (int i = 0; i < numEigenvalues; i++) {
//            if (i < numEigenvalues - 1) {
//                eigenvalues += "\\lambda_" + i + " = " + FormatHelper.roundComplex(simple.eig().getEigenvalue(i), 3);
//                eigenvectors += "v_" + i + " = " + simple.eig().getEigenVector(i);
//            } else {
//                eigenvalues += "\\lambda_" + i + " = " + simple.eig().getEigenvalue(i) + ", ";
//                eigenvectors += "v_" + i + " = " + simple.eig().getEigenVector(i) + ", ";
//            }
//        }

        Detail[] details = new Detail[18];
//        details[0] = new Detail("Eigenvalues", eigenvalues); // FIX EIGENSFF!
//        details[1] = new Detail("Eigenvectors", eigenvectors);
        details[0] = new Detail("Eigenvalues", "comming soon");
        details[1] = new Detail("Eigenvectors", "comming soon!");
        details[2] = new Detail("Inverse", FormatHelper.matrixToString(simple.invert().getMatrix()));
        details[3] = new Detail("Transpose", FormatHelper.matrixToString(simple.transpose().getMatrix()));
        details[4] = new Detail("Trace", FormatHelper.round(simple.trace(), 3) + "");
        details[5] = new Detail("Determinant", FormatHelper.round(simple.determinant(), 3) + "");
        details[6] = new Detail("Rank", MatrixFeatures.nullity(matrix) + "");
        details[7] = new Detail("Nullity", MatrixFeatures.rank(matrix) + "");
        details[8] = new Detail("Are rows linearly independent", FormatHelper.booleanToString(MatrixFeatures.isRowsLinearIndependent(matrix)));
       // details[9] = new Detail("Are rows and columns linearly independent", String.valueOf(MatrixFeatures.isFullRank(matrix)));
        details[9] = new Detail("balls? ", "yup");
        details[10] = new Detail("Identity", FormatHelper.booleanToString(MatrixFeatures.isIdentity(matrix, 1e-8)));
        details[11] = new Detail("Orthogonal", FormatHelper.booleanToString(MatrixFeatures.isOrthogonal(matrix, 1e-8)));
        details[12] = new Detail("Positive definite", FormatHelper.booleanToString(MatrixFeatures.isPositiveDefinite(matrix)));
        details[13] = new Detail("Semi-positive definite", FormatHelper.booleanToString(MatrixFeatures.isPositiveSemidefinite(matrix)));
        details[14] = new Detail("Skew symmetric", FormatHelper.booleanToString(MatrixFeatures.isSkewSymmetric(matrix, 1e-8)));
        details[15] = new Detail("Square", FormatHelper.booleanToString(MatrixFeatures.isSquare(matrix)));
        details[16] = new Detail("Symmetric", FormatHelper.booleanToString(MatrixFeatures.isSymmetric(matrix)));
        details[17] = new Detail("Upper triangular matrix", FormatHelper.booleanToString(MatrixFeatures.isUpperTriangle(matrix, 0, 1e-8)));
        return details;
    }

    public static Detail[] getImaginaryVectorDetails(Complex64F[] vector) {
        return new Detail[0];
    }

    public static Detail[] getRealVectorDetails(double[] vector) {
        Detail[] details = new Detail[1];
        details[0] = new Detail("Length", MatrixHelper.length(vector) + "");
        return  details;
    }

    public static Detail[] getImaginaryNumberDetails(Complex64F number) {
        ComplexPolar64F polarForm = new ComplexPolar64F(number);
        Detail[] details = new Detail[2];
        details[0] = new Detail("Modulus", polarForm.getR() + "");
        details[1] = new Detail("Argument", "\\theta = " + polarForm.getTheta());
        return details;
    }

    public static Detail[] getRealNumberDetails(double number) {
        Detail[] details = new Detail[4];
        details[0] = new Detail("Prime", FormatHelper.booleanToString(MatrixHelper.isPrime(number)));
        details[1] = new Detail("Twin prime", FormatHelper.booleanToString(MatrixHelper.isTwin(number)));
        details[2] = new Detail("Even", FormatHelper.booleanToString(number % 2 == 0));
        details[3] = new Detail("Odd", FormatHelper.booleanToString(number % 2 != 0));
        return details;
    }

}
