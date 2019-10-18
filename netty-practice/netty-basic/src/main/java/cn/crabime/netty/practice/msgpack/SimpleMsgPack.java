package cn.crabime.netty.practice.msgpack;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * msgpack框架简单应用
 */
public class SimpleMsgPack {

    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");

        MessagePack pack = new MessagePack();
        // 序列化list
        byte[] serializableList = pack.write(list);

        // 根据这个byte数组，反序列化获取集合对象
        List<String> deSerializableList = pack.read(serializableList, Templates.tList(Templates.TString));

        for (String str : deSerializableList) {
            System.out.println(str);
        }
        System.out.println();
    }
}
