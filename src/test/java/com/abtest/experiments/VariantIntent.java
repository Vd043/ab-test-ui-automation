package com.abtest.experiments;

public class VariantIntent {

    public final String experimentKey;
    public final String variantKey;

    public VariantIntent(String experimentKey, String variantKey) {
        this.experimentKey = experimentKey;
        this.variantKey = variantKey;
    }
}
