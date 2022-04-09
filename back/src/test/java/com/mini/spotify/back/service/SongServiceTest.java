package com.mini.spotify.back.service;

import com.mini.spotify.back.domain.Author;
import com.mini.spotify.back.domain.Song;
import com.mini.spotify.back.repository.SongRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {
    @Mock
    private SongRepository repository;

    private SongService service;

    @BeforeEach
    void prepareEnv() {
        service = new SongService(repository);
    }

    @Test
    public void testGetByExistedId() {
        String id = "existingId";
        Author author = new Author("authorId", "Le Tester");
        Song song = new Song(id, "First testing song", Collections.singletonList(author));
        when(repository.findById(id)).thenReturn(Optional.of(song));

        Song actualSong = service.getById(id);
        Assertions.assertNotNull(actualSong);
        Assertions.assertEquals(actualSong.getId(), id);
    }

    @Test
    public void testGetByNotExistedId() {
        String id = "NotExistingId";
        when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> service.getById(id));
    }

    @Test
    public void testAllInNonEmpty() {
        String id = "existingId";
        Author author = new Author("authorId", "Le Tester");
        Song song = new Song(id, "First testing song", Collections.singletonList(author));
        when(repository.findAll()).thenReturn(Collections.singletonList(song));

        List<Song> actualAll = service.all();
        Assertions.assertNotNull(actualAll);
        Assertions.assertEquals(actualAll.size(), 1);
        Assertions.assertNotNull(actualAll.get(0));
        Assertions.assertEquals(actualAll.get(0).getId(), id);
    }

    @Test
    public void testAllInEmpty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Song> actualAll = service.all();
        Assertions.assertNotNull(actualAll);
        Assertions.assertTrue(actualAll.isEmpty());
    }

    @Test
    public void testGetByExistedAuthorId() {
        String id = "existingId";

        Author author = new Author(id, "Le Tester");
        Author anotherAuthor = new Author("anotherId", "Casual user");
        Song song1 = new Song("Song1", "First testing song", Collections.singletonList(author));
        Song song2 = new Song("Song2", "Second testing song", Arrays.asList(anotherAuthor, author));

        when(repository.findAll()).thenReturn(Arrays.asList(song1, song2));

        List<Song> songsByAuthor = service.allByAuthorId(id);
        Assertions.assertNotNull(songsByAuthor);
        Assertions.assertEquals(songsByAuthor.size(), 2);
        for (Song song : songsByAuthor) {
            Assertions.assertNotNull(song);
        }
        Assertions.assertEquals(songsByAuthor.get(0).getId(), song1.getId());
        Assertions.assertEquals(songsByAuthor.get(1).getId(), song2.getId());
    }

    @Test
    public void testGetByNotExistedAuthorId() {
        String id = "existingId";
        when(repository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertThrows(NoSuchElementException.class, () -> service.allByAuthorId(id));
    }
}
