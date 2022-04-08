package com.mini.spotify.back.controller;

import com.mini.spotify.back.domain.Song;
import com.mini.spotify.back.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {
    private final SongService service;

    @Autowired
    public SongController(SongService service) {
        this.service = service;
    }

    @GetMapping("/library")
    public List<Song> all() {
        return service.all();
    }

    @GetMapping("/song/{id}")
    public Song one(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/author/{id}")
    public List<Song> author(@PathVariable String id) {
        return service.allByAuthorId(id);
    }

    @PostMapping("/song")
    public Song add(@RequestBody Song song) {
        return service.addSong(song);
    }

    @DeleteMapping("/song/{id}")
    public void deleteSong(@PathVariable String id) {
        service.deleteById(id);
    }

    @DeleteMapping("/author/{id}")
    public void deleteAllByAuthor(@PathVariable String id) {
        service.deleteAllByAuthorId(id);
    }

}
