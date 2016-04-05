package jpa.converters.enums;

/**
 * Created by kadir.basol on 29.3.2016.
 */

/**
 * Birim ağırlık hesaplama türü
 */
public enum UnitType {
    BOX((byte)1),KG((byte) 2),TON((byte)3),OTHER((byte)-1);

    private Byte value;

    UnitType(byte b) {
        this.value = b;
    }

    public Byte getValue() {
        return value;
    }


    public static final UnitType fromInteger(int value) {
        switch (value) {
            case 1:
                return UnitType.BOX;
            case 2:
                return UnitType.KG;
            case 3:
                return UnitType.TON;
            default:
                return UnitType.OTHER;
        }
    }


    @Override
    public String toString() {
        switch (this) {
            case BOX:
                return "Kutu";
            case KG:
                return "Kg";
            case TON:
                return "Ton";
            case OTHER:
                return "Diğer";
        }
        return super.toString();
    }
}
