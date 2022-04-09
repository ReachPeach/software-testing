package com.mini.spotify.back.controller;


import com.mini.spotify.back.domain.Author;
import com.mini.spotify.back.domain.Song;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.MapPropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = SongControllerTest.Initializer.class)
public class SongControllerTest {
    public static final int PORT = 27017;
    public static final String HTTP_LOCALHOST_8080 = "http://localhost:8080";

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0").withExposedPorts(PORT);
            mongoDBContainer.start();

            Map<String, Object> properties = new HashMap<>();
            properties.put("spring.data.mongodb.host", mongoDBContainer.getContainerIpAddress());
            properties.put("spring.data.mongodb.port", mongoDBContainer.getMappedPort(PORT));
            applicationContext.getEnvironment().getPropertySources()
                    .addLast(new MapPropertySource("TestConfigProperties", properties));
        }
    }

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Test
    public void testAddSong() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        Author author = new Author("authorId", "Named");
        Song song = new Song("songId", "Named too", Collections.singletonList(author));

        Song actualSong = restTemplate.postForEntity(HTTP_LOCALHOST_8080 + "/song", song, Song.class).getBody();

        Assertions.assertNotNull(actualSong);
        Assertions.assertEquals(actualSong.getId(), song.getId());
        Assertions.assertEquals(actualSong.getName(), song.getName());
    }

    @Test
    public void testAllByAuthor() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        Author author1 = new Author("targetId", "John Doe");
        Author author2 = new Author("someId", "Lua Dipa");

        Song soloFSong = new Song("song1", "Solo by John", Collections.singletonList(author1));
        Song soloSSong = new Song("song2", "Solo by Lua", Collections.singletonList(author2));
        Song featSong = new Song("song3", "John feat. Lau", Arrays.asList(author1, author2));

        restTemplate.postForEntity(HTTP_LOCALHOST_8080 + "/song", soloFSong, Song.class);
        restTemplate.postForEntity(HTTP_LOCALHOST_8080 + "/song", soloSSong, Song.class);
        restTemplate.postForEntity(HTTP_LOCALHOST_8080 + "/song", featSong, Song.class);


        ParameterizedTypeReference<List<Song>> typeReference = new ParameterizedTypeReference<List<Song>>() {
        };
        List<Song> songs =
                restTemplate.exchange(HTTP_LOCALHOST_8080 + "/author/" + author1.getId(), HttpMethod.GET, null, typeReference).getBody();

        Assertions.assertNotNull(songs);
        Assertions.assertEquals(songs.size(), 2);
    }

    @Test
    public void testDeletingByAuthor() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        Author author1 = new Author("targetId", "John Doe");
        Author author2 = new Author("someId", "Lua Dipa");

        Song soloFSong = new Song("song1", "Solo by John", Collections.singletonList(author1));
        Song soloSSong = new Song("song2", "Solo by Lua", Collections.singletonList(author2));
        Song featSong = new Song("song3", "John feat. Lau", Arrays.asList(author1, author2));

        restTemplate.postForEntity(HTTP_LOCALHOST_8080 + "/song", soloFSong, Song.class);
        restTemplate.postForEntity(HTTP_LOCALHOST_8080 + "/song", soloSSong, Song.class);
        restTemplate.postForEntity(HTTP_LOCALHOST_8080 + "/song", featSong, Song.class);

        restTemplate.delete(HTTP_LOCALHOST_8080 + "/author/" + author1.getId());
        ParameterizedTypeReference<List<Song>> typeReference = new ParameterizedTypeReference<List<Song>>() {
        };

        List<Song> songs =
                restTemplate.exchange(HTTP_LOCALHOST_8080 + "/library", HttpMethod.GET, null, typeReference).getBody();

        Assertions.assertNotNull(songs);
        Assertions.assertEquals(songs.size(), 1);

    }
}
