package jfinal.tools;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by crabime on 7/2/17.
 *
 * @param <T> Bean类
 * @param <M> Model类
 */
public class BaseBean<T, M> {

    /**
     * JFinal model转Bean
     * @param m model
     * @param t bean
     * @return 塞入model值后的实体bean
     */
    public <T, M extends Model<?>> T convertModelToBean(M m, T t) {
        try {
            if (null != m && null != t) {
                PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(t);
                if (!ArrayUtils.isEmpty(propertyDescriptors)) {
                    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                        String propertyName = propertyDescriptor.getName();
                        if (!propertyName.equals("class")) {
                            Class<?> propertyType = PropertyUtils.getPropertyType(t, propertyName);
                            String propertyClassName = propertyType.getSimpleName();

                            if (StringUtils.equals(propertyClassName, "Integer")) { //如果是Integer类型
                                try {
                                    Integer intValue = null;
                                    Object valueObj = m.get(propertyName);
                                    if (null != valueObj && valueObj instanceof Long) {
                                        intValue = m.getLong(propertyName).intValue();
                                    } else {
                                        intValue = m.getInt(propertyName);
                                    }
                                    if (intValue != null) {
                                        BeanUtils.setProperty(t, propertyName, intValue);
                                    }
                                } catch (Exception e) {
                                    continue;
                                }
                            }else if (StringUtils.equals(propertyClassName, "Double")){ //如果是Double类型,没有Long、Integer那么麻烦
                                try{
                                    Double doubleValue = m.getDouble(propertyName);
                                    if (doubleValue != null){
                                        BeanUtils.setProperty(t, propertyName, doubleValue);
                                    }
                                }catch (Exception e){
                                    continue;
                                }
                            }else if (StringUtils.equals(propertyClassName, "Timestamp")){
                                try {
                                    Timestamp timestamp = m.getTimestamp(propertyName);
                                    Date dateValue = null;
                                    if (timestamp != null) {
                                        dateValue = new Date(timestamp.getTime());
                                    }
                                    if (dateValue != null) {
                                        BeanUtils.setProperty(t, propertyName, dateValue);
                                    }
                                }catch (Exception e){
                                    continue;
                                }
                            }else if (StringUtils.equals(propertyClassName, "String")){
                                try {
                                    String strValue = "";
                                    //如果是Date类型
                                    if (propertyName.endsWith("Time") || propertyName.endsWith("Date")){
                                        Date date = m.getDate(propertyName);
                                        strValue = DateFormatUtils.format(date, "yyyy-MM-dd");
                                    }
                                    if (StringUtils.isAllEmpty(strValue)){
                                        strValue = m.getStr(propertyName);
                                        BeanUtils.setProperty(t, propertyName, strValue);
                                    }else {
                                        BeanUtils.setProperty(t, propertyName, strValue);
                                    }
                                }catch (Exception e){
                                    continue;
                                }
                            }else if (StringUtils.equals(propertyClassName, "Long")){
                                try{
                                    Long longValue = m.getLong(propertyName);
                                    if (longValue != null){
                                        BeanUtils.setProperty(t, propertyName, longValue);
                                    }
                                }catch (Exception e){
                                    continue;
                                }
                            }else if (StringUtils.equals(propertyName, "Float")){
                                try{
                                    Float floatValue = m.getFloat(propertyName);
                                    if (floatValue != null){
                                        BeanUtils.setProperty(t, propertyName, floatValue);
                                    }
                                }catch (Exception e){
                                    continue;
                                }
                            }else {
                                throw new RuntimeException("不支持的Bean类型");
                            }
                        }
                    }
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T convertRecordToBean(Record m, T t){
        try {
            if (null != m && null != t) {
                PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(t);
                if (!ArrayUtils.isEmpty(propertyDescriptors)) {
                    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                        String propertyName = propertyDescriptor.getName();
                        if (!propertyName.equals("class")) {
                            Class<?> propertyType = PropertyUtils.getPropertyType(t, propertyName);
                            String propertyClassName = propertyType.getSimpleName();

                            if (StringUtils.equals(propertyClassName, "Integer")) { //如果是Integer类型
                                try {
                                    Integer intValue = null;
                                    Object valueObj = m.get(propertyName);
                                    if (null != valueObj && valueObj instanceof Long) {
                                        intValue = m.getLong(propertyName).intValue();
                                    } else {
                                        intValue = m.getInt(propertyName);
                                    }
                                    if (intValue != null) {
                                        BeanUtils.setProperty(t, propertyName, intValue);
                                    }
                                } catch (Exception e) {
                                    continue;
                                }
                            }else if (StringUtils.equals(propertyClassName, "Double")){ //如果是Double类型,没有Long、Integer那么麻烦
                                try{
                                    Double doubleValue = m.getDouble(propertyName);
                                    if (doubleValue != null){
                                        BeanUtils.setProperty(t, propertyName, doubleValue);
                                    }
                                }catch (Exception e){
                                    continue;
                                }
                            }else if (StringUtils.equals(propertyClassName, "Timestamp")){
                                try {
                                    Timestamp timestamp = m.getTimestamp(propertyName);
                                    Date dateValue = null;
                                    if (timestamp != null) {
                                        dateValue = new Date(timestamp.getTime());
                                    }
                                    if (dateValue != null) {
                                        BeanUtils.setProperty(t, propertyName, dateValue);
                                    }
                                }catch (Exception e){
                                    continue;
                                }
                            }else if (StringUtils.equals(propertyClassName, "String")){
                                try {
                                    String strValue = "";
                                    //如果是Date类型
                                    if (propertyName.endsWith("Time") || propertyName.endsWith("Date")){
                                        Date date = m.getDate(propertyName);
                                        strValue = DateFormatUtils.format(date, "yyyy-MM-dd");
                                    }
                                    if (StringUtils.isAllEmpty(strValue)){
                                        strValue = m.getStr(propertyName);
                                        BeanUtils.setProperty(t, propertyName, strValue);
                                    }else {
                                        BeanUtils.setProperty(t, propertyName, strValue);
                                    }
                                }catch (Exception e){
                                    continue;
                                }
                            }else if (StringUtils.equals(propertyClassName, "Long")){
                                try{
                                    Long longValue = m.getLong(propertyName);
                                    if (longValue != null){
                                        BeanUtils.setProperty(t, propertyName, longValue);
                                    }
                                }catch (Exception e){
                                    continue;
                                }
                            }else if (StringUtils.equals(propertyName, "Float")){
                                try{
                                    Float floatValue = m.getFloat(propertyName);
                                    if (floatValue != null){
                                        BeanUtils.setProperty(t, propertyName, floatValue);
                                    }
                                }catch (Exception e){
                                    continue;
                                }
                            }else {
                                throw new RuntimeException("不支持的Bean类型");
                            }
                        }
                    }
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将bean转换成jfinal的model
     * @param t bean
     * @param m model
     * @return 具有bean属性的model对象
     */
    public <M extends Model<?>> M convertBeanToModel(T t, M m){
        if (t != null && m != null) {
            PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(t);
            if (!ArrayUtils.isEmpty(propertyDescriptors)) {
                for (PropertyDescriptor descriptor : propertyDescriptors) {
                    String propertyName = descriptor.getName();
                    if (!StringUtils.equals(propertyName, "class")) {
                        try {
                            String propertyValue = BeanUtils.getProperty(t, propertyName);
                            Object propertyObj = PropertyUtils.getProperty(t, propertyName);
                            if (propertyObj instanceof String) {
                                m.set(propertyName, propertyValue);
                            } else if (propertyObj instanceof Integer) {
                                Integer intValue = 0;
                                intValue = Integer.parseInt(propertyValue);
                                m.set(propertyName, intValue);
                            } else if (propertyObj instanceof Date) {
                                if (!StringUtils.isEmpty(propertyValue)) {
                                    Timestamp timestamp = Timestamp.valueOf(propertyValue);
                                    m.set(propertyName, timestamp);
                                }
                            } else if (propertyObj instanceof Long) {
                                Long longValue = new Long(propertyValue);
                                m.set(propertyName, longValue);
                            } else if (propertyObj instanceof Double) {
                                Double douValue = new Double(propertyValue);
                                m.set(propertyName, douValue);
                            }
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return m;
            }
        }
        return null;
    }
}
