package cn.crabime;

import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by crabime on 11/5/17.
 */
public class JaywayTest extends TestCase {

    private String str;

    @Before
    protected void setUp() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("JsonSample.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null){
            sb.append(line);
        }
        inputStream.close();
        br.close();
        str = sb.toString();
    }

    @Test
    public void testOutputJson(){
        List<String> author = JsonPath.read(str, "$.store.book[*].author");
        assertEquals(4, author.size());
    }

    @Test
    public void testReadOneTime(){
        //只读取一次json文件
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(str);
        String author1 = JsonPath.read(document, "$.store.book[0].author");
        assertEquals("Nigel Rees", author1);
    }

    /**
     * 使用..符号
     */
    @Test
    public void testAllSymbol(){
        List<Map<String, Object>> secondBook = JsonPath.parse(str).read("$..book[2]", List.class);
        assertEquals(1, secondBook.size());
        assertEquals("Herman Melville", secondBook.get(0).get("author"));

        List<Map<String, Object>> firstToTheSecond = JsonPath.parse(str).read("$..book[:2]", List.class);
        assertEquals(2, firstToTheSecond.size());
        assertEquals("Nigel Rees", firstToTheSecond.get(0).get("author"));

        //获取前两个book json对象
        List<Map<String, Object>> firstTwoBook = JsonPath.parse(str).read("$..book[0,1]", List.class);
        assertEquals(2, firstTwoBook.size());
        assertEquals("Sword of Honour", firstTwoBook.get(1).get("title"));

        List booksLength = JsonPath.parse(str).read("$..book.length()", List.class);
        assertEquals(1, booksLength.size());
        assertEquals(4, booksLength.get(0));
    }

    @Test
    public void testReadAuthorWithISBN(){
        ReadContext ctx = JsonPath.parse(str);
        List<String> bookAuthorWithISBN = ctx.read("$.store.book[?(@.isbn)].author");
        assertEquals(2, bookAuthorWithISBN.size());
    }

    @Test
    public void testFilterExpensiveBook(){
        List<Map<String, Object>> expensiveBooks = JsonPath
                .using(Configuration.defaultConfiguration())
                .parse(str)
                .read("$.store.book[?(@.price>10)]", List.class);
        System.out.println(expensiveBooks.get(0));
    }

    @Test
    public void testMultipleFilter(){
        List<Map<String, Object>> books = JsonPath.parse(str).read("$.store.book[?(@.price < 10 && @.category == 'reference')]");
        assertEquals(1, books.size());
        assertEquals("Sayings of the Century", books.get(0).get("title"));

        List<Map<String, Object>> extremeBooks = JsonPath.parse(str).read("$.store.book[?(@.price < 9 || @.price > 20)]");
        assertEquals(3, extremeBooks.size());
        //目前!报错
//        List<Map<String, Object>> noFictions = JsonPath.parse(str).read("$.store.book[?(!(@.category == 'fiction' || @.price > 20))]");
//        assertEquals(3, noFictions.size());
    }

    /**
     * jayway自动识别mapping结果
     */
    @Test
    public void testObjectMappingSPI(){
        String json = "{\"date_as_long\" : 1411455611975}";
        Date date = JsonPath.parse(json).read("$['date_as_long']", Date.class);
        System.out.println(date.toString());
    }

    /**
     * 将json字符串直接映射为一个对象
     */
    @Test
    public void testJsonToObjectMapping(){
        Book book = JsonPath.parse(str).read("$.store.book[0]", Book.class);
        System.out.println(book.toString());
    }

    /**
     * 读取全部泛型信息
     * 使用Gson的JsonProvider、MappingProvider
     * JsonProvider主要是用来读取json数据的
     * MappingProvider主要用来映射对象和bean的
     */
    @Test
    public void testObtainFullGenericType(){
        TypeRef<List<String>> typeRef = new TypeRef<List<String>>() {};
        Configuration.setDefaults(new Configuration.Defaults(){
            private final JsonProvider jsonProvider = new GsonJsonProvider();
            private final MappingProvider mappingProvider = new GsonMappingProvider();

            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }

            public MappingProvider mappingProvider() {
                return mappingProvider;
            }
        });
        Configuration configuration = Configuration.defaultConfiguration();
        List<String> titles = JsonPath.using(configuration).parse(str).read("$.store.book[*].title", typeRef);
        for (String s : titles) {
            System.out.println(s);
        }
    }

    /**
     * 使用jsonpath filter
     */
    @Test
    public void testJayWayFilter(){
        Filter cheapFictionFilter = Filter.filter(Criteria.where("category").is("fiction").and("price").lte(13D));

        List<Map<String, Object>> books = JsonPath.parse(str).read("$.store.book[?]", cheapFictionFilter);
        assertEquals(2, books.size());

        //增加过滤器,每一个问号对应一个filter
        Filter authorFilter = Filter.filter(Criteria.where("author").is("Evelyn Waugh"));
        List<Map<String, Object>> booksWithSpecAuthor = JsonPath.parse(str).read("$.store.book[?, ?]", cheapFictionFilter, authorFilter);
        assertEquals(1, booksWithSpecAuthor.size());
    }

    @Test
    public void testRollYourOwn(){
        Predicate booksWithISBN = new Predicate() {
            public boolean apply(PredicateContext ctx) {
                return ctx.item(Map.class).containsKey("isbn");
            }
        };
        List<Map<String, Object>> books = JsonPath.parse(str).read("$.store.book[?].isbn", List.class, booksWithISBN);
        assertEquals(2, books.size());
    }
}
