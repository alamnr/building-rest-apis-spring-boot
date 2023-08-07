package dev.denvega.streamsschdule.repository;

import dev.denvega.streamsschdule.exception.LiveStreamException;
import dev.denvega.streamsschdule.model.LiveStream;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class LiveStreamRepository {

    private List<LiveStream> streams = new ArrayList<>();

    public LiveStreamRepository(){
        streams.add(new LiveStream(UUID.randomUUID().toString(),"Building REST Api with Spring boot",
                "description", "https://www.vega.com", LocalDateTime.of(2023,3,23,12,45,0),
                LocalDateTime.of(2023,3,29,2,35,0)));
    }

    public  List<LiveStream> findAll(){
        return streams;
    }

    public LiveStream findById(String id) throws LiveStreamException{
        return streams.stream().filter(stream -> stream.id().equals(id)).findFirst().orElseThrow(LiveStreamException::new);
    }

    public LiveStream create(LiveStream stream){
        streams.add(stream);
        return stream;
    }

    public void update(LiveStream stream, String id){
        LiveStream existing = streams.stream().filter(s -> s.id().equals(id)).findFirst().orElseThrow(()-> new IllegalArgumentException("Stream not found."));
        int i = streams.indexOf(existing);
        streams.set(i,stream);
    }

    public void delete(String id){
        streams.removeIf(stream -> stream.id().equals(id));
    }

}
