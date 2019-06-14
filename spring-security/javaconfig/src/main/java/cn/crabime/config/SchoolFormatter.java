package cn.crabime.config;

import cn.crabime.beans.School;
import org.springframework.format.Formatter;

import java.util.Locale;

public class SchoolFormatter implements Formatter<School> {

    @Override
    public School parse(String text, Locale locale) {
        if (null != text) {
            String[] schoolAttr = text.split(",");
            School school = new School();
            school.setId(Integer.parseInt(schoolAttr[0]));
            school.setName(schoolAttr[1]);
            return school;
        }
        return null;
    }

    @Override
    public String print(School object, Locale locale) {
        if (object == null) {
            return "";
        }
        return String.format(locale, "学校ID=[%d],校名NAME=[%s]", object.getId(), object.getName());
    }
}
