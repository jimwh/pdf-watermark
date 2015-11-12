package lab;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.Date;

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
        LocalDate now = LocalDate.now();
        // DateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour)
        DateTime dt = new DateTime(2016, 01, 02, 0, 0);
        log.info("distance={}", getDaysToExpiration(now, dt.toDate()));
        //
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String ep = "$2a$10$75pBjapg4Nl8Pzd.3JRnUe7PDJmk9qBGwNEJDAlA3V.dEJxcDKn5O";
        log.info("matches={}", encoder.matches("koala", ep));
        /*
        for(int i=27; i<31; i++) {
            encoder = new BCryptPasswordEncoder(i);
            String result = encoder.encode("koala");
            log.info("strength={}, result={}", i, result);
            if( ep.equals(result) ) {
                //                log.info("strength={}, result={}", i, result);
                log.info("done strength={}, result={}", i, result);
                return;
            }
        }
        */

        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        Assert.notNull(ctx.getBean(CustomerResourceLoader.class));

        PdfWaterMark pdfWaterMark = ctx.getBean(PdfWaterMark.class);
        Assert.notNull(pdfWaterMark);
        pdfWaterMark.test(args[0], args[1]);

        SpringApplication.exit(ctx);
        log.info("application done...");
    }

    private static int getDaysToExpiration(LocalDate runDate, Date date) {
        return Days.daysBetween(runDate.toDateTimeAtCurrentTime(), new DateTime(date)).getDays();
    }

}
