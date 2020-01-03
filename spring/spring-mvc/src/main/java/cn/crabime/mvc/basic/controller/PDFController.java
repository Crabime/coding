package cn.crabime.mvc.basic.controller;

import cn.crabime.mvc.basic.PdfBean;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;

@Controller
public class PDFController {

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ResponseEntity<byte[]> handlePdf(@RequestParam("name") String name, @RequestParam("character") String character) {
        PdfBean pdfBean = new PdfBean(name, character);
        String absFilePath = generatePdf(pdfBean);
        String fileName = null;
        byte[] bytes = null;
        try {
            File file = new File(absFilePath);
            fileName = file.getName();
            bytes = new byte[(int)file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(bytes, 0, bytes.length);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(fileName, fileName);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    private String generatePdf(PdfBean pdfBean) {
        String pdfFileName = "E:\\pdf\\pt.pdf";
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
            document.open();
            Table table = new Table(2);
            table.addCell("name");
            table.addCell("character");
            table.addCell(pdfBean.getName());
            table.addCell(pdfBean.getCharacter());
            document.add(table);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return pdfFileName;
    }
}
