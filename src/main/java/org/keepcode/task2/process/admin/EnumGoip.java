package org.keepcode.task2.process.admin;

public enum EnumGoip {
    MODEL_A,
    MODEL_B,
    MODEL_C;

    public static EnumGoip getByModel(String model) {
        for (EnumGoip enumGoip : EnumGoip.values()) {
            if (enumGoip.name().equalsIgnoreCase(model)) {
                return enumGoip;
            }
        }
        throw new IllegalArgumentException("Unknown Goip model: " + model);
    }
}
