package propertyeditors;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;

import java.util.List;

/**
 * spring中CustomCollectionEditor使用
 */
public class CustomCollectionEditorTest {

    public static void main(String[] args) {
        CustomCollectionEditor editor = new CustomCollectionEditor(List.class);

        editor.setValue(new String[] {"张三", "李四", "王五"});

        Object value = editor.getValue();
        List<?> valList = (List<?>) value;

        System.out.println(valList);
    }
}
