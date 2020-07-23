package cn.crabime.groovy.script;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GroovyShellEvaluate {

    private String getContent(String classpathFile) {
        StringBuilder content = new StringBuilder();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource resource = resourcePatternResolver.getResource(classpathFile);
        try {
            File file = resource.getFile();
            InputStream is = new FileInputStream(file);
            byte[] c = new byte[1024];
            int len = 0;
            while ((len = is.read(c)) != -1) {
                String buffer = new String(c, 0, len);
                content.append(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private File getFile(String classpathFile) {
        File file = null;
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource resource = resourcePatternResolver.getResource(classpathFile);
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void main(String[] args) throws IOException {
        Binding binding = new Binding();
        Table table = new Table();
        binding.setProperty("tool", "car");
        binding.setProperty("t", table);
        GroovyShellEvaluate shellEvaluate = new GroovyShellEvaluate();
        File file = shellEvaluate.getFile("classpath:randomNumberGenerator.groovy");
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(file);
    }

}
