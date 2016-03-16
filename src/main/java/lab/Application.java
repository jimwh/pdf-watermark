package lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;

@Configuration
@EnableAutoConfiguration
@Import(CustomerResourceLoader.class)
@ComponentScan("lab")
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {

        log.info("application start...");
        if (args.length != 2) {
            log.info("Usage: <input file> <output file>");
            System.exit(1);
        }


        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        Assert.notNull(ctx.getBean(CustomerResourceLoader.class));

        /*
        PdfWaterMark pdfWaterMark = ctx.getBean(PdfWaterMark.class);
        Assert.notNull(pdfWaterMark);
        pdfWaterMark.test(args[0], args[1]);
        */
        PdfTester tester = ctx.getBean(PdfTester.class);
        Assert.notNull(tester);
        tester.test(args[0], args[1]);

        SpringApplication.exit(ctx);
        log.info("application done...");
    }


}
