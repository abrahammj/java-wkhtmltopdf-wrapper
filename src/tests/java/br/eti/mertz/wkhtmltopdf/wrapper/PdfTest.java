package br.eti.mertz.wkhtmltopdf.wrapper;

import br.eti.mertz.wkhtmltopdf.wrapper.Pdf;
import br.eti.mertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import br.eti.mertz.wkhtmltopdf.wrapper.page.PageType;
import br.eti.mertz.wkhtmltopdf.wrapper.params.PageParam;
import br.eti.mertz.wkhtmltopdf.wrapper.params.Param;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.CoreMatchers.*;

public class PdfTest {

    @Test
    public void testCommand() throws Exception {
        Pdf pdf = new Pdf();
        pdf.addToc();
        pdf.addParam(new Param("--enable-javascript"), new Param("--html-header", "file:///example.html"));
        pdf.addPage("http://www.google.com", PageType.url);
        Assert.assertThat("command params should contain the --enable-javascript and --html-header", pdf.getCommand(), containsString("--enable-javascript --html-header file:///example.html"));
    }

    @Test
    public void findExecutable() throws Exception {
        WrapperConfig wc = new WrapperConfig();
        Assert.assertThat("executable should be /usr/bin/wkhtmltopdf", wc.findExecutable(), containsString("/usr/bin/wkhtmltopdf"));
    }

    @Test
    public void testPdfFromStringTo() throws Exception {

        // GIVEN a html template containing special characters that java stores in utf-16 internally
        Pdf pdf = new Pdf();
        pdf.addPage("<html><head><meta charset=\"utf-8\"></head><h1>Müller</h1></html>", PageType.htmlAsString);

        pdf.saveAs("output.pdf");

        // WHEN
        byte[] pdfBytes = pdf.getPDF();

        PDFParser parser = new PDFParser(new ByteArrayInputStream(pdfBytes));

        // that is a valid PDF (otherwise an IOException occurs)
        parser.parse();
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String pdfText = pdfTextStripper.getText(new PDDocument(parser.getDocument()));

        Assert.assertThat("document should contain the creditorName", pdfText, containsString("Müller"));
    }
    
    @Test
    public void testCommand_with_global_and_page_params() throws Exception {
        Pdf pdf = new Pdf();
        pdf.addToc();
        pdf.addParam(new Param("--enable-javascript"), new Param("--html-header", "file:///example.html"));
        pdf.addPage("http://www.google.com", PageType.url);
        pdf.addParam(new PageParam("--cookie", "sessionid 1234"));
        pdf.addParam(new Param("--header-spacing", "5"));
        Assert.assertThat("command params should end with pageparam ", pdf.getCommand(), endsWith("http://www.google.com --cookie sessionid 1234 -"));
    }
    
}
