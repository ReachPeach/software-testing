package com.mini.spotify.back.service;

import com.mini.spotify.back.domain.Song;
import com.mini.spotify.back.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SongService {
    private final SongRepository repository;

    @Autowired
    public SongService(SongRepository repository) {
        this.repository = repository;
    }

    public List<Song> all() {
        return repository.findAll();
    }

    public Song getById(String id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<Song> allByAuthorId(String authorId) {
        List<Song> found = repository.findAll().stream().
                filter(song -> song.getAuthors().stream().anyMatch(it -> it.getId().equals(authorId))).
                collect(Collectors.toList());
        if (found.isEmpty()) throw new NoSuchElementException();
        return found;
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void deleteAllByAuthorId(String authorId) {
        repository.deleteAllById(allByAuthorId(authorId).stream().map(Song::getId).collect(Collectors.toList()));
    }

    public Song addSong(Song song) {
        return repository.save(song);
    }
}
