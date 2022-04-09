package com.mini.spotify.back.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Song {
    String id;
    String name;
    List<Author> authors;
}
