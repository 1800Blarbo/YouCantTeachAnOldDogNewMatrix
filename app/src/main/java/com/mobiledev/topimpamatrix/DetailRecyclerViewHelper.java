package com.mobiledev.topimpamatrix;

import org.ejml.alg.dense.misc.CTransposeAlgs;
import org.ejml.alg.dense.misc.RrefGaussJordanRowPivot;
import org.ejml.data.CDenseMatrix64F;
import org.ejml.data.Complex64F;
import org.ejml.data.ComplexPolar64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CCommonOps;
import org.ejml.ops.CMatrixFeatures;
import org.ejml.ops.MatrixFeatures;
import org.ejml.simple.SimpleEVD;
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
        switch (MatrixHelper.classify(matrix)) {
            case "real number":
                return getRealNumberDetails(matrix.getReal(0, 0));
            case "complex number":
                return getComplexNumberDetails(new Complex64F(matrix.getReal(0, 0), matrix.getImaginary(0, 0)));
            case "real vector":
                return getRealVectorDetails(MatrixHelper.makeVector(MatrixHelper.makeReal(matrix)));
            case "complex vector":
                return getComplexVectorDetails(MatrixHelper.makeVector(matrix));
            case "real matrix" :
                return getRealMatrixDetails(MatrixHelper.makeReal(matrix));
        }
        return getComplexMatrixDetails(matrix);
    }

    public static Detail[] getComplexMatrixDetails(CDenseMatrix64F matrix) {
        Detail[] details = new Detail[10];

        // matrix details
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

        // numerical details
        details[2] = new Detail("Determinant", FormatHelper.complexToString(CCommonOps.det(matrix)) + "");

        CDenseMatrix64F inverse = matrix.copy();
        CCommonOps.invert(inverse);
        details[3] = new Detail("Inverse", FormatHelper.matrixToString(inverse));

        //details[] = new Detail("Real part", FormatHelper.matrixToString())

        details[4] = new Detail("Trace", FormatHelper.complexToString(MatrixHelper.trace(matrix)));

        // boolean details
        details[5] = new Detail("Hermitian", FormatHelper.booleanToString(CMatrixFeatures.isHermitian(matrix, 1e-8)));
        details[6] = new Detail("Identity", FormatHelper.booleanToString(CMatrixFeatures.isIdentity(matrix, 1e-8)));
        details[7] = new Detail("Positive definite", FormatHelper.booleanToString(CMatrixFeatures.isPositiveDefinite(matrix)));
        details[8] = new Detail("Unitary", FormatHelper.booleanToString(CMatrixFeatures.isUnitary(matrix, 1e-8)));
        details[9] = new Detail("Square", FormatHelper.booleanToString(MatrixHelper.isSquare(matrix)));

        return details;
    }

    public static Detail[] getRealMatrixDetails(DenseMatrix64F matrix) {
        SimpleMatrix simple = SimpleMatrix.wrap(matrix);

        // eigenstuff
        SimpleEVD eigenstuff = simple.eig();
        int numEigenvalues = eigenstuff.getNumberOfEigenvalues();
        String eigenvalues = "";
        String eigenvectors = "";
        for (int i = 0; i < numEigenvalues; i++) {
            if (i < numEigenvalues - 1) {
                eigenvalues += "λ_" +  i + " = " + FormatHelper.complexToString(eigenstuff.getEigenvalue(i)) + ", ";
                eigenvectors += "v_" + i + " = " + FormatHelper.matrixToString(eigenstuff.getEigenVector(i)) + ", ";
            } else {
                eigenvalues += "λ_" +  i + " = " + FormatHelper.complexToString(eigenstuff.getEigenvalue(i));
                eigenvectors += "v_" + i + " = " + FormatHelper.matrixToString(eigenstuff.getEigenVector(i));
            }
        }

        // Row reduction
        RrefGaussJordanRowPivot rowReducer = new RrefGaussJordanRowPivot();
        DenseMatrix64F rref = matrix.copy();
        rowReducer.reduce(rref, matrix.numCols);

        Detail[] details = new Detail[19];

        // matrix details
        details[0] = new Detail("Row reduced echlon form", FormatHelper.matrixToString(rref));
        details[1] = new Detail("Eigenvalues", eigenvalues);
        details[2] = new Detail("Eigenvectors", eigenvectors);
        details[3] = new Detail("Inverse", FormatHelper.matrixToString(simple.invert().getMatrix()));
        details[4] = new Detail("Transpose", FormatHelper.matrixToString(simple.transpose().getMatrix()));

        // numerical details
        details[5] = new Detail("Determinant", FormatHelper.round(simple.determinant(), 3) + "");
        details[6] = new Detail("Trace", FormatHelper.round(simple.trace(), 3) + "");
        details[7] = new Detail("balls? ", "yup");
        details[8] = new Detail("Rank", MatrixFeatures.nullity(matrix) + "");
        details[9] = new Detail("Nullity", MatrixFeatures.rank(matrix) + "");

        // boolean details
        details[10] = new Detail("Identity", FormatHelper.booleanToString(MatrixFeatures.isIdentity(matrix, 1e-8)));
        details[11] = new Detail("Orthogonal", FormatHelper.booleanToString(MatrixFeatures.isOrthogonal(matrix, 1e-8)));
        details[12] = new Detail("Positive definite", FormatHelper.booleanToString(MatrixFeatures.isPositiveDefinite(matrix)));
        details[13] = new Detail("Semi-positive definite", FormatHelper.booleanToString(MatrixFeatures.isPositiveSemidefinite(matrix)));
        details[14] = new Detail("Skew symmetric", FormatHelper.booleanToString(MatrixFeatures.isSkewSymmetric(matrix, 1e-8)));
        details[15] = new Detail("Square", FormatHelper.booleanToString(MatrixFeatures.isSquare(matrix)));
        details[16] = new Detail("Symmetric", FormatHelper.booleanToString(MatrixFeatures.isSymmetric(matrix)));
        details[17] = new Detail("Upper triangular matrix", FormatHelper.booleanToString(MatrixFeatures.isUpperTriangle(matrix, 0, 1e-8)));
        details[18] = new Detail("Are rows linearly independent", FormatHelper.booleanToString(MatrixFeatures.isRowsLinearIndependent(matrix)));
        // details[9] = new Detail("Are rows and columns linearly independent", String.valueOf(MatrixFeatures.isFullRank(matrix)))

        return details;
    }

    public static Detail[] getComplexVectorDetails(ComplexVector vector) {
        return new Detail[0];
    }

    public static Detail[] getRealVectorDetails(Vector vector) {
        Detail[] details = new Detail[7];
        details[0] = new Detail("Magnitude", "|v| = " + FormatHelper.round(vector.getMagnitude(), 2));
        details[1] = new Detail("Angle", "θ = " + FormatHelper.round(vector.getTheta(), 2));
        details[2] = new Detail("Dimension", vector.getDimension() + "");
        details[3] = new Detail("Normalized", FormatHelper.vectorToString(vector.normalize()));
        details[4] = new Detail("Polar form", FormatHelper.polarVectorToString(vector));
        details[5] = new Detail("Slope in xy-plane", FormatHelper.doubleToString(vector.getComponents()[1] / vector.getComponents()[0], 2));
        details[6] = new Detail("Unit", FormatHelper.booleanToString(vector.getMagnitude() == 1));
        return  details;
    }

    public static Detail[] getComplexNumberDetails(Complex64F number) {
        ComplexPolar64F polarForm = new ComplexPolar64F(number);
        Detail[] details = new Detail[2];
        details[0] = new Detail("Modulus", "|z| = " + FormatHelper.round(polarForm.getR(), 2));
        details[1] = new Detail("Argument", "θ = " + FormatHelper.round(polarForm.getTheta(), 2));
        details[2] = new Detail("Conjugate", FormatHelper.complexToString(new Complex64F(number.real, -number.imaginary)));
        // include visuals!!!
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
