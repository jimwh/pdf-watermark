package lab;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.*;

@Component
public class PdfWaterMark {

    @Resource
    CustomerResourceLoader customerResourceLoader;

    @Resource
    Watermark watermark;

    public void test(String inputFileName, String outputFileName) throws IOException, DocumentException {
        Assert.notNull(customerResourceLoader);

        // InputStream inputStream = customerResourceLoader.getInputStream(inputFileName);

        InputStream inputStream = new FileInputStream(inputFileName);

        byte[] source = FileCopyUtils.copyToByteArray(inputStream);

        ByteArrayOutputStream out = watermark.stampPdfByteArray(source);

        OutputStream outputStream = new FileOutputStream(outputFileName);
        FileCopyUtils.copy(out.toByteArray(), outputStream);
    }

}
