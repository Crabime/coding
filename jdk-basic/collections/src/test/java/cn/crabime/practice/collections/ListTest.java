package cn.crabime.practice.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListTest {

    @Test
    public void testList() {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("cde");
        List<String> result = list.stream().map(r -> "1" + r).collect(Collectors.toList());
        System.out.println(result);

        String str = "abc";
        System.out.println(str.substring(str.indexOf("ab") + "ab".length()));

        int i = Integer.parseInt("01");
        System.out.println(i);
        int j = Integer.parseInt("");
        System.out.println(j);
    }

}
