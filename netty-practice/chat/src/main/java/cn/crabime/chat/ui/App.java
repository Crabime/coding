package cn.crabime.chat.ui;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class App extends JFrame {

    private ImageIcon loadIcon(String image) {
        ImageIcon imageIcon = null;
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource("classpath:images/" + image);
        try {
            imageIcon = new ImageIcon(resource.getFile().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageIcon;
    }

    public App() {
        ImageIcon icon = loadIcon("icon.jpg");
        this.add(new TextField("你好，swing"));
        this.setTitle("swing聊天界面");
        this.setSize(new Dimension(700, 600));
        this.setIconImage(icon.getImage());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new App();
    }
}
