package jfinaltest;

import java.util.regex.Pattern;

/**
 * Created by crabime on 7/2/17.
 */
public class Holder {
    // "order\\s+by\\s+[^,\\s]+(\\s+asc|\\s+desc)?(\\s*,\\s*[^,\\s]+(\\s+asc|\\s+desc)?)*";
    private static final Pattern ORDER_BY_PATTERN = Pattern.compile(
            "order\\s+by\\s+[^,\\s]+(\\s+asc|\\s+desc)?(\\s*,\\s*[^,\\s]+(\\s+asc|\\s+desc)?)*",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    public static String replaceOrderBy(String sql) {
        return Holder.ORDER_BY_PATTERN.matcher(sql).replaceAll("");
    }

    public static void main(String[] args) {
        String sql = "select * from member order by name";
        String result = Holder.replaceOrderBy(sql);
        System.out.println(result);
    }
}
