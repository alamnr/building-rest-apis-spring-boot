package dev.denvega.streamsschdule.controller;

import dev.denvega.streamsschdule.model.LiveStream;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static  org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class LiveStreamControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void findAll(){
        ResponseEntity<List<LiveStream>> entity = findAllStreams();
        assertEquals(HttpStatus.OK,entity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());
        assertEquals(1,entity.getBody().size());
        System.out.println(entity);
    }

    @Test
    @Order(2)
    void findById(){
        LiveStream existing = findAllStreams().getBody().get(0);
        String id = existing.id();
        String desc = "description";
        String title = "Building REST Api with Spring boot";
        LiveStream stream = restTemplate.getForObject("/streams/"+id, LiveStream.class);
        assertEquals(id,stream.id());
        assertEquals(title,stream.title());
        assertEquals(desc,stream.description());
    }

    @Test
    @Order(3)
    void create(){
        String id = UUID.randomUUID().toString();
        LiveStream stream = new LiveStream(
                id,
                "Test Title",
                "Test desc",
                "Test url",
                LocalDateTime.of(2023,8,22,23,0,0),
                LocalDateTime.of(2023,8,21,21,0,0)
        );

        ResponseEntity<LiveStream> entity = restTemplate.postForEntity("/streams",stream,LiveStream.class);
        assertEquals(HttpStatus.CREATED,entity.getStatusCode());
        assertEquals(2, findAllStreams().getBody().size());

        LiveStream responseStream = entity.getBody();
        System.out.println(responseStream==stream);
        assertEquals(stream.id(),responseStream.id());
        assertEquals(stream.title(),responseStream.title());
        assertEquals(stream.description(),responseStream.description());
        assertEquals(stream.url(),responseStream.url());
        assertEquals(stream.startDate(),responseStream.startDate());
        assertEquals(stream.endDate(),responseStream.endDate());
    }

    @Test
    @Order(4)
    void update(){
        LiveStream existing = findAllStreams().getBody().get(0);
        LiveStream streamUpdated = new LiveStream(
                existing.id(),
                "Updated title",
                "Updated description",
                "Updated url",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        ResponseEntity<LiveStream> entity = restTemplate.exchange("/streams/"+existing.id(),HttpMethod.PUT,new HttpEntity<>(streamUpdated),LiveStream.class);
        assertEquals(HttpStatus.NO_CONTENT,entity.getStatusCode());
    }

    @Test
    @Order(5)
    void delete() {
        LiveStream existing = findAllStreams().getBody().get(0);
        ResponseEntity<LiveStream> entity = restTemplate.exchange("/streams/" + existing.id(), HttpMethod.DELETE, null, LiveStream.class);
        assertEquals(HttpStatus.NO_CONTENT,entity.getStatusCode());
        assertEquals(1,findAllStreams().getBody().size());
    }

    private ResponseEntity<List<LiveStream>> findAllStreams(){
            return restTemplate.exchange("/streams",
                    HttpMethod.GET,
                    new HttpEntity<>(null),
                    new ParameterizedTypeReference<List<LiveStream>>() {});
    }
}
