package jpa.converters;

import jpa.ProcessType;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converter;

/**
 * Created by kadir.basol on 18.3.2016.
 */
@Converter
public class ProcessTypeConverter implements AttributeConverter<ProcessType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProcessType processType) {
        return processType.getValue();
    }

    @Override
    public ProcessType convertToEntityAttribute(Integer integer) {
        return ProcessType.fromInteger(integer);
    }
}
