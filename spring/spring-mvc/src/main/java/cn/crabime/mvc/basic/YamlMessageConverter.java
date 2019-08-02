package cn.crabime.mvc.basic;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class YamlMessageConverter<T> extends AbstractGenericHttpMessageConverter<T> {

    public YamlMessageConverter() {
        super(MediaType.valueOf("text/yaml"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Yaml yaml = new Yaml();
        return yaml.loadAs(inputMessage.getBody(), clazz);
    }

    @Override
    protected void writeInternal(T t, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Yaml yaml = new Yaml();
        OutputStreamWriter osw = new OutputStreamWriter(outputMessage.getBody());
        yaml.dump(t, osw);
        osw.close();
    }

    @Override
    public T read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Yaml yaml = new Yaml();
        return yaml.loadAs(inputMessage.getBody(), (Class<T>) type);
    }
}
