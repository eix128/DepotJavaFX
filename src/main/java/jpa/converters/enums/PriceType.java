package jpa.converters.enums;

/**
 * Created by kadir.basol on 21.3.2016.
 */

/**
 * Fiyat türü
 */
public enum PriceType {
    DOLLAR((byte)1),EURO((byte)2),TL((byte)3),UNKNOWN((byte)-1);
    
    private Byte value;

    PriceType(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }

    public static final PriceType fromInteger(int value) {
        switch (value) {
            case 1:
                return PriceType.DOLLAR;
            case 2:
                return PriceType.EURO;
            case 3:
                return PriceType.TL;
            default:
                return PriceType.UNKNOWN;
        }
    }
}
