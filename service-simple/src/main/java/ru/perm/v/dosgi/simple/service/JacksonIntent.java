package ru.perm.v.dosgi.simple.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.dosgi.common.api.IntentsProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Нужен для конвертации People в json
 * Для указания нужного представления в сервисе указать
 * <pre>
 * \@Component(
 * ....
 *  property = {
 *        "service.exported.intents=jackson",
 *        // By default CXF will favor the default json provider
 *        "cxf.bus.prop.skip.default.json.provider.registration=true"
 *  }
 * )
 * </pre>
 */
@Slf4j
public class JacksonIntent implements IntentsProvider {

    @Override
    public List<?> getIntents() {
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        log.info("getIntents {} {}", this.getClass().getName(), provider);
        return Arrays.asList(provider);
    }
}
