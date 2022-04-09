const express = require('express')
const bodyParser = require('body-parser')
const session = require('express-session')
const cookieParser = require('cookie-parser')

const url = "localhost";
const port = 4000;

const makeLibrary = (n, m) => {
    // returns n number of to-do items
    // default is 15

    const song_num = n || 15;
    const author_num = m || 5
    const library = [];
    const authorsSongs = [];
    for (let author = 0; author <= author_num; author++) {
        authorsSongs.push([]);
    }

    for (let i = 0; i < song_num; i++) {
        const authors = []
        for (let j = 0; j < author_num; j++) {
            if (Math.random() < 1 / author_num) {
                authors.push({
                    id: j,
                    name: `Author ${j}`
                })
                authorsSongs[j].push(i)
            }
        }
        if (authors.length === 0) {
            authors.push({
                id: author_num,
                name: `Anonymous`
            })
            authorsSongs[author_num].push(i)
        }

        library.push({
            id: i,
            name: `Song ${i}`,
            authors: authors
        });
    }
    return [library, authorsSongs];
};

const [library, authorsSongs] = makeLibrary(200, 40);

run();

function run() {
    const api = express();
    api.use(bodyParser.json());
    api.use(express.urlencoded());
    api.options('http://' + url + ':' + port.toString());
    api.use(function (req, res, next) {
        res.setHeader('Access-Control-Allow-Origin', 'http://localhost:3000');
        res.setHeader('Access-Control-Allow-Credentials', 'true');
        res.setHeader('Cache-Control', 'no-store');

        next();
    });

    api.get('/author', (req, res) => {
        const id = parseInt(req.query.id)
        if (id < 0 || id > authorsSongs.length) {
            res.send([])
        } else {
            res.send(library.filter(song => authorsSongs[id].includes(song.id)))
        }
    });

    api.get('/', (req, res) => {
        res.send(library)
    })

    api.listen(port);
}
