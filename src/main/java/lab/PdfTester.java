package lab;

import com.itextpdf.text.DocumentException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class PdfTester {

    private static final Logger log = LoggerFactory.getLogger(PdfTester.class);
    @Resource
    private ConsentFormDatasheetPDFService pdfService;

    public void test(String inputFileName, String outputFileName) throws IOException, DocumentException {

        log.info("start from here: inputFileName={}, outputFileName={}", inputFileName, outputFileName);
        log.info("now:{}", DateTime.now().toString("MM/dd/yyyy"));
        Assert.notNull(pdfService);
        ByteArrayOutputStream out = pdfService.createPDF(new ConsentHeader());
        Assert.notNull(out);
        FileCopyUtils.copy(out.toByteArray(), new FileOutputStream(outputFileName));
    }

}
