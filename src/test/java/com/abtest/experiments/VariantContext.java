package com.abtest.experiments;

public class VariantContext {

    public final String experimentKey;
    public final String variantKey;
    public final boolean verified;

    public VariantContext(String experimentKey, String variantKey, boolean verified) {
        this.experimentKey = experimentKey;
        this.variantKey = variantKey;
        this.verified = verified;
    }
}
