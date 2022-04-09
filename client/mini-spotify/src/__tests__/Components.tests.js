import SongList from '../SongList'
import AuthorItem from '../AuthorItem'
import {render, screen} from "../custom-render";
import {useParams, MemoryRouter} from "react-router-dom";
import {waitForElementToBeRemoved} from "@testing-library/react";

describe('Related pages', function () {
    it("can tell mocked from unmocked functions", () => {
        expect(jest.isMockFunction(useParams)).toBe(true);
        expect(jest.isMockFunction(MemoryRouter)).toBe(false);
    });

    it('Author page content', async () => {
        jest.spyOn(URLSearchParams.prototype, "get").mockReturnValue("1");
        useParams.mockReturnValue({id: 1});

        render(<AuthorItem/>);
        await waitForElementToBeRemoved(() =>
            screen.getByText(/Author with id=1 doesn't exist/i)
        );
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

        for (let i = 1; i<=15; i++){
            expect(screen.getByText("Song " + i)).toBeInTheDocument();
        }
    });
});