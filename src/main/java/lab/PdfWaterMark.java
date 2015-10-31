package lab;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@Component
public class PdfWaterMark {

    @Resource
    CustomerResourceLoader customerResourceLoader;

    @Resource
    Watermark watermark;

    public void test(String inputFileName, String outputFileName) throws IOException, DocumentException {
        Assert.notNull(customerResourceLoader);
        // InputStream inputStream = customerResourceLoader.getInputStream(inputFileName);
        byte[] source = FileCopyUtils.copyToByteArray(new FileInputStream(inputFileName));
        ByteArrayOutputStream out = watermark.stampPdfByteArray(source);
        FileCopyUtils.copy(out.toByteArray(), new FileOutputStream(outputFileName));
    }

}
