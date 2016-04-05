package jpa.converters.enums;

/**
 * Created by kadir.basol on 21.3.2016.
 */

/**
 * Giriş çıkış türü
 */
public enum ProcessType {
    INPUT(0),OUTPUT(1),UNKNOWN(-1);

    private int i;

    ProcessType(int i) {
        this.i = i;
    }


    public int getValue() {
        return i;
    }

    public static ProcessType fromInteger(int i) {
        switch (i) {
            case 0:
                return ProcessType.INPUT;
            case 1:
                return ProcessType.OUTPUT;
            default:
                return ProcessType.UNKNOWN;
        }
    }
}
