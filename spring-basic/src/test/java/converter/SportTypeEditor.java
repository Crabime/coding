package converter;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Created by crabime on 9/16/17.
 */
public class SportTypeEditor extends PropertyEditorSupport {
    private final SportType sportType;

    public SportTypeEditor(SportType sportType) {
        this.sportType = sportType;
    }

    /**
     * 将spring配置中的值付给Player对象
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)){
            String[] prop = StringUtils.commaDelimitedListToStringArray(text);
            sportType.setId(Integer.parseInt(prop[0]));
            sportType.setName(prop[1].trim());
            setValue(sportType);
        }else {
            setValue(null);
        }
    }

    /**
     * 返回的不是文本
     */
    @Override
    public String getAsText() {
        return null;
    }

}
