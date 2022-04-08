const makeLibrary = (n, m) => {
    // returns n number of to-do items
    // default is 15

    const song_num = n || 15;
    const author_num = m || 5
    const library = [];
    for (let i = 0; i < song_num; i++) {
        const authors = []
        for (let j = 0; j < author_num; j++) {
            if (Math.random() < 1 / author_num) {
                authors.push({
                    id: j,
                    name: `Author ${j}`
                })
            }
        }
        if (authors.length === 0) {
            authors.push({
                id: author_num,
                name: `Anonymous`
            })
        }

        library.push({
            id: i,
            name: `Song ${i}`,
            authors: authors
        });
    }
    return library;
};

export const library = makeLibrary(200, 40);