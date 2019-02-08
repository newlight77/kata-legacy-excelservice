package com.newlight77.kata.excel.datamodel;

public enum Statut {
    A_FAIRE,
    EFFECTUE
    ;

    public String value() {
        return name();
    }

    public static Statut fromValue(String v) {
        return valueOf(v);
    }
}
