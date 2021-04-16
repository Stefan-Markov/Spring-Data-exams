package exam.library.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter  extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String date) throws Exception {
        return LocalDate.parse(date);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.toString();
    }
}
