package com.mobiledev.topimpamatrix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.ejml.data.CDenseMatrix64F;

/**
 * Created by larspmayrand on 4/14/16.
 */
public class MainActivity extends AppCompatActivity {

    public  final static String SERIALIZABLE_KEY = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Start DetailActivity for randomly generated complex matrix. */
        double[][] array = new double[][]
                {{Math.random() * 30 - 15, Math.random() * 30 - 15, Math.random() * 30 - 15, 0},
                        {Math.random() * 30 - 15, Math.random() * 30 - 15, 0, Math.random() * 30 - 15}};
        CDenseMatrix64F matrix = new CDenseMatrix64F(array);
        //CDenseMatrix64F matrix = MatrixHelper.makeComplex(RandomMatrices.createOrthogonal(2, 2, new Random()));
        Intent intent = new Intent(this,DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

}
