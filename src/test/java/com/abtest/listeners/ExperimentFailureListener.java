package com.abtest.listeners;

import com.abtest.experiments.VariantContext;
import com.abtest.experiments.VariantContextHolder;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExperimentFailureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        VariantContext ctx = VariantContextHolder.get();
        if (ctx != null) {
            System.err.println("failed test: " + result.getName()
                    + ", experiment=" + ctx.experimentKey
                    + ", variant=" + ctx.variantKey);
        }
    }
}
