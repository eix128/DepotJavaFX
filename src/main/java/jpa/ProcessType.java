package jpa;

/**
 * Created by kadir.basol on 25.2.2016.
 */
public enum ProcessType {
    ENTRANCE(0),WAYOUT(1);

    private int value;

    ProcessType(int type) {
        this.value = type;
    }

    public static ProcessType fromInteger(int x) {
        switch(x) {
            case 0:
                return ProcessType.ENTRANCE;
            case 1:
                return ProcessType.WAYOUT;
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
