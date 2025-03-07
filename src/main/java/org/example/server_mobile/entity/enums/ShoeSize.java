package org.example.server_mobile.entity.enums;

public enum ShoeSize {
    SIZE_35(35),
    SIZE_36(36),
    SIZE_37(37),
    SIZE_38(38),
    SIZE_39(39),
    SIZE_40(40),
    SIZE_41(41),
    SIZE_42(42),
    SIZE_43(43),
    SIZE_44(44),
    SIZE_45(45),
    SIZE_46(46);

    private final int size;

    ShoeSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return String.valueOf(size);
    }
}
