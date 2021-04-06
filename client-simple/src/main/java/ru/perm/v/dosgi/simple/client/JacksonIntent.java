package ru.perm.v.dosgi.simple.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.dosgi.common.api.IntentsProvider;
import org.osgi.service.component.annotations.Component;

import java.util.Arrays;
import java.util.List;

@Component //
        (//
                property = {
                        "org.apache.cxf.dosgi.IntentName=jackson"
                }
        )
@Slf4j
public class JacksonIntent implements IntentsProvider {

    @Override
    public List<?> getIntents() {
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        log.info("getIntents {} {}", this.getClass().getName(), provider);
        return Arrays.asList(provider);
    }

}
