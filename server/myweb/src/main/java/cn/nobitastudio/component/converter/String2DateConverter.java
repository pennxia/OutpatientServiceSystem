package cn.nobitastudio.component.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class String2DateConverter implements Converter<String,Date> {

    @Override
    public Date convert(String source) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
        try {
            Date date = simpleDateFormat.parse(source);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
