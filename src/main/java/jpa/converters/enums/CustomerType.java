package jpa.converters.enums;

import com.google.inject.Inject;

import java.util.ResourceBundle;

/**
 * Created by kadir.basol on 5.4.2016.
 */
public enum CustomerType {
    PERSONAL((byte) 1), SUPPLIER((byte) 2), ARTISAN((byte) 3), OTHER((byte) 4) , UNKNOWN((byte) 0);

    private Byte type;


    CustomerType(byte type) {
        this.type = type;
    }

    @Inject
    private ResourceBundle resourceBundle;


    @Override
    public String toString() {
        switch (this) {
            case PERSONAL:
                return resourceBundle.getString("companyType.Personal");
            case SUPPLIER:
                return resourceBundle.getString("companyType.Supplier");
            case ARTISAN:
                return resourceBundle.getString("companyType.Artisan");
            case OTHER:
                return resourceBundle.getString("companyType.Other");
            case UNKNOWN:
                return resourceBundle.getString("companyType.Unknown");
            default:
                return resourceBundle.getString("companyType.Unknown");
        }
    }


    public static CustomerType fromByte(Byte value) {
        if(value == null)
            return UNKNOWN;
        switch (value) {
            case 0:
                return UNKNOWN;
            case 1:
                return PERSONAL;
            case 2:
                return SUPPLIER;
            case 3:
                return ARTISAN;
            case 4:
                return OTHER;
            default:
                return OTHER;
        }
    }

    public Byte getType() {
        return type;
    }
}
