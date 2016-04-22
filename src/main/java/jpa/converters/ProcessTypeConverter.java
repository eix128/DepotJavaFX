package jpa.converters;

import jpa.converters.enums.ProcessType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by kadir.basol on 18.3.2016.
 */
@Converter
public class ProcessTypeConverter implements AttributeConverter<ProcessType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProcessType processType) {
        return ProcessType.toInteger(processType);
    }

    @Override
    public ProcessType convertToEntityAttribute(Integer integer) {
        return ProcessType.fromInteger(integer);
    }
}
