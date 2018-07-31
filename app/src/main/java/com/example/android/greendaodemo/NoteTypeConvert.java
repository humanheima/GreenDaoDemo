package com.example.android.greendaodemo;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by DuMingwei on 2018/7/31.
 * Description:
 */
public class NoteTypeConvert implements PropertyConverter<NoteType, String> {

    @Override
    public NoteType convertToEntityProperty(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.name();
    }
}
