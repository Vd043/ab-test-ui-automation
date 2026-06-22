package com.abtest.experiments;

public final class VariantContextHolder {

    private static final ThreadLocal<VariantContext> CONTEXT = new ThreadLocal<>();

    private VariantContextHolder() {
    }

    public static void set(VariantContext context) {
        CONTEXT.set(context);
    }

    public static VariantContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
