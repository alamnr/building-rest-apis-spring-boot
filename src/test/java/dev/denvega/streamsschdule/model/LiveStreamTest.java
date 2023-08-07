package dev.denvega.streamsschdule.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class LiveStreamTest {

    @Test
    void create_new_mutable_live_stream(){
        MutableLiveStream stream = new MutableLiveStream();
        stream.setId(UUID.randomUUID().toString());
        stream.setTitle("Building REST API  with Spring Boot");
        stream.setDescription("In this live coding session, you will learn how to build REST APIs in Java with Spring Boot. You will learn how to bootstrap your application using Spring Initializr and the New Project Wizard in IntelliJ IDEA.");
        stream.setUrl("https://www.danvega.com");
        stream.setStartDate(LocalDateTime.of(2023,2,16,11,0));
        stream.setEndDate(LocalDateTime.of(2023,2,18,11,0));

        stream.setTitle("FOO");
        assertNotNull(stream);
        assertEquals("Building REST API  with Spring Boot", stream.getTitle());
    }

    @Test
    void create_new_immutable_stream(){
        ImmutableLiveStream stream = new ImmutableLiveStream(UUID.randomUUID().toString(),"Building REST Api with Spring boot",
                "description", "https://www.vega.com",LocalDateTime.of(2023,3,23,12,45,0),
                LocalDateTime.of(2023,3,29,2,35,0));
        assertNotNull(stream);
        assertEquals("Building REST Api with Spring Boot", stream.getTitle());
    }

    @Test
    void create_new_record_live_stream(){
        LiveStream stream = new LiveStream(UUID.randomUUID().toString(),"Building REST Api with Spring boot",
                "description", "https://www.vega.com",LocalDateTime.of(2023,3,23,12,45,0),
                LocalDateTime.of(2023,3,29,2,35,0));

        assertNotNull(stream);
        assertEquals("Building REST Api with Spring boot", stream.title());
        assertTrue(stream.getClass().isRecord());
        assertEquals(6,stream.getClass().getRecordComponents().length);
    }
}
