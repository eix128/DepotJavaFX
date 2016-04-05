package jpa.converters;

import jpa.converters.enums.PriceType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by kadir.basol on 21.3.2016.
 */
@Converter
public class ProcessAmountTypeConverter implements AttributeConverter<PriceType,Byte> {

    @Override
    public Byte convertToDatabaseColumn(PriceType amountType) {
        return amountType.getValue();
    }

    @Override
    public PriceType convertToEntityAttribute(Byte integer) {
        return PriceType.fromInteger(integer);
    }
}
