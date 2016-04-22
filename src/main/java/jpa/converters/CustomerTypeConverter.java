package jpa.converters;

import com.google.inject.AbstractModule;
import jpa.converters.enums.CustomerType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by kadir.basol on 5.4.2016.
 */
@Converter(autoApply=true)
public class CustomerTypeConverter implements AttributeConverter<CustomerType,Integer> {

    @Override
    public Integer convertToDatabaseColumn(CustomerType customerType) {
        return customerType.getType().intValue();
    }

    @Override
    public CustomerType convertToEntityAttribute(Integer aByte) {
        return CustomerType.fromByte(aByte.byteValue());
    }
}
