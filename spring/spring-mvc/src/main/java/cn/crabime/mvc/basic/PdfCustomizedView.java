package cn.crabime.mvc.basic;

import com.lowagie.text.Table;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PdfCustomizedView extends AbstractPdfView {

    // TODO: 2019/8/1 增加spring mvc pdf设置
    @Override
    protected void buildPdfDocument(Map<String, Object> model, com.lowagie.text.Document document,
                                    com.lowagie.text.pdf.PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> userData = (Map<String,String>) model.get("userData");
        Table table = new Table(2);
        table.addCell("Roll No");
        table.addCell("Name");

        for (Map.Entry<String, String> entry : userData.entrySet()) {
            table.addCell(entry.getKey());
            table.addCell(entry.getValue());
        }
        document.add(table);
    }
}
