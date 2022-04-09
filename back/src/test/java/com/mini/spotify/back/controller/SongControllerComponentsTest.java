package com.mini.spotify.back.controller;

import com.mini.spotify.back.domain.Author;
import com.mini.spotify.back.domain.Song;
import com.mini.spotify.back.service.SongService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SongController.class)
public class SongControllerComponentsTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService service;

    @Test
    public void testLibrary() throws Exception {
        Author author = new Author("author", "Tester");
        Song song1 = new Song("song1", "Name1", Collections.singletonList(author));
        Song song2 = new Song("song2", "Name2", Collections.singletonList(author));

        given(service.all()).willReturn(Arrays.asList(song1, song2));
        MvcResult mvcResult = mockMvc.perform(get("/library"))
                .andExpect(status().isOk())
                .andReturn();

        TypeReference<List<Song>> typeReference = new TypeReference<List<Song>>() {
        };

        String contentAsString = mvcResult.getResponse().getContentAsString();

        List<Song> actualResult = mapper.readValue(contentAsString, typeReference);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult.size(), 2);
    }

    @Test
    public void testSong() throws Exception {
        Author author = new Author("author", "Tester");
        Song song = new Song("song", "Song", Collections.singletonList(author));
        String id = song.getId();


        given(service.getById(id)).willReturn(song);
        MvcResult mvcResult = mockMvc.perform(get("/song/" + id))
                .andExpect(status().isOk())
                .andReturn();

        TypeReference<Song> typeReference = new TypeReference<Song>() {
        };

        String contentAsString = mvcResult.getResponse().getContentAsString();

        Song actualResult = mapper.readValue(contentAsString, typeReference);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult.getId(), song.getId());
        Assertions.assertEquals(actualResult.getName(), song.getName());
        Assertions.assertEquals(actualResult.getAuthors().size(), song.getAuthors().size());
        for (int index = 0; index < actualResult.getAuthors().size(); index++) {
            Assertions.assertEquals(actualResult.getAuthors().get(index).getName(), song.getAuthors().get(0).getName());
            Assertions.assertEquals(actualResult.getAuthors().get(index).getId(), song.getAuthors().get(0).getId());
        }
    }

    @Test
    public void testAuthor() throws Exception {
        Author author = new Author("author", "Tester");
        Author anotherAuthor = new Author("author`", "User");

        Song song1 = new Song("song1", "First Song", Collections.singletonList(author));
        Song song2 = new Song("song2", "Second Song", Arrays.asList(author, anotherAuthor));
        String id = author.getId();

        given(service.allByAuthorId(id)).willReturn(Arrays.asList(song1, song2));
        MvcResult mvcResult = mockMvc.perform(get("/author/" + id))
                .andExpect(status().isOk())
                .andReturn();

        TypeReference<List<Song>> typeReference = new TypeReference<List<Song>>() {
        };

        String contentAsString = mvcResult.getResponse().getContentAsString();

        List<Song> actualResult = mapper.readValue(contentAsString, typeReference);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(actualResult.size(), 2);
        List<Song> addedSongs = Arrays.asList(song1, song2);
        for (int index = 0; index < actualResult.size(); index++) {
            Assertions.assertEquals(actualResult.get(index).getName(), addedSongs.get(index).getName());
            Assertions.assertEquals(actualResult.get(index).getId(), addedSongs.get(index).getId());
            Assertions.assertTrue(actualResult.get(index).getAuthors().stream().
                    anyMatch(author_ -> Objects.equals(author_.getId(), author.getId()) &&
                            Objects.equals(author_.getName(), author.getName())));
        }
    }
}