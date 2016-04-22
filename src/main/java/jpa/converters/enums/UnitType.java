package jpa.converters.enums;

/**
 * Created by kadir.basol on 29.3.2016.
 */

import globals.GlobalDatas;
import main.gui.ComboList;

import java.util.ResourceBundle;

/**
 * Birim ağırlık hesaplama türü
 */
public enum UnitType {
    BOX((byte)1),KG((byte) 2),TON((byte)3),OTHER((byte)-1);

    private Byte value;
    private ResourceBundle resourceBundle;

    UnitType(byte b) {
        this.value = b;
        resourceBundle = GlobalDatas.getInstance().getInjector().getInstance(ResourceBundle.class);

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
                return resourceBundle.getString("unitType.Box");
            case KG:
                return resourceBundle.getString("unitType.Kg");
            case TON:
                return resourceBundle.getString("unitType.Ton");
            case OTHER:
                return resourceBundle.getString("unitType.Other");
        }
        return super.toString();
    }
}
