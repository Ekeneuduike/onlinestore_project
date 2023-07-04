package com.ekene.config;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class TestComponent implements ObservationHandler<Observation.Context> {

    private final static Logger log = LoggerFactory.getLogger(TestComponent.class);

    @Override
    public void onStart(Observation.Context context) {
        log.info("before running the observation for context [{}], userType{}", context.getName(),getUserTypeFromContext(context));
        ObservationHandler.super.onStart(context);
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info("before running the observation for context [{}], usertype {}",context.getName(),getUserTypeFromContext(context));
        ObservationHandler.super.onStop(context);
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return false;
    }

    private String getUserTypeFromContext(Observation.Context context) {
        return StreamSupport.stream(context.getLowCardinalityKeyValues().spliterator(), false)
                .filter(keyValue -> "usertype".equals(keyValue.getKey()))
                .map(KeyValue::getKey)
                .findFirst()
                .orElse("unknown");

    }
}
