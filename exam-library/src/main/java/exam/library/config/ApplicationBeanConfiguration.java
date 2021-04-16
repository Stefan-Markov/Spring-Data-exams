package exam.library.config;

import com.google.gson.*;
import exam.library.util.ValidatorUtil;
import exam.library.util.ValidatorUtilImpl;
import exam.library.util.XmlParser;
import exam.library.util.XmlParserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
//                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
//                    @Override
//                    public LocalDateTime deserialize(JsonElement jsonElement,
//                                                     Type type, JsonDeserializationContext jsonDeserializationContext)
//                            throws JsonParseException {
//                        return LocalDateTime.parse(jsonElement.getAsString(),
//                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//                    }
//                })
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement jsonElement,
                                                     Type type, JsonDeserializationContext jsonDeserializationContext)
                            throws JsonParseException {
                        return LocalDate.parse(jsonElement.getAsString(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                })
                .create();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }
}
