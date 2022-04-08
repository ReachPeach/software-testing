import {SongList} from '../SongList'
import {AuthorItem} from '../AuthorItem'
import {render, screen} from "../custom-render";
import {useParams, MemoryRouter} from "react-router-dom";
import {library} from "../makeLibrary";

describe('Related pages', function () {
    it("can tell mocked from unmocked functions", () => {
        expect(jest.isMockFunction(useParams)).toBe(true);
        expect(jest.isMockFunction(MemoryRouter)).toBe(false);
    });

    it('Author page content', () => {
        useParams.mockReturnValue({id: 1});

        render(<AuthorItem/>);
        expect(screen.getByText(/Author 1/)).toBeInTheDocument();
    });

    it('Anonymous page content', () => {
        useParams.mockReturnValue({id: 40});

        render(<AuthorItem/>);
        expect(screen.getByText(/Anonymous/)).toBeInTheDocument();
    });

    it('Not existed author`s page content', () => {
        useParams.mockReturnValue({id: -1});

        render(<AuthorItem/>);
        expect(screen.getByText(/Author with id=-1 doesn't exist/)).toBeInTheDocument();
    });

    it('Library content', () => {
       render(<SongList/>)
        library.slice(0, 15).forEach((song) => {
            expect(screen.getByText(song.name)).toBeInTheDocument();
        });
    });
});