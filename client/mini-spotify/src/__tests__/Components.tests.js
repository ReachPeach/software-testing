import SongList from '../SongList'
import AuthorItem from '../AuthorItem'
import {render, screen} from "../custom-render";
import {useParams, MemoryRouter} from "react-router-dom";
import {waitForElementToBeRemoved} from "@testing-library/react";
import {wait} from "@testing-library/user-event/dist/utils";

describe('Related pages', function () {
    it("can tell mocked from unmocked functions", () => {
        expect(jest.isMockFunction(useParams)).toBe(true);
        expect(jest.isMockFunction(MemoryRouter)).toBe(false);
    });

    it('Author page content', async () => {
        useParams.mockReturnValue({id: 1});

        render(<AuthorItem/>);
        waitForElementToBeRemoved(screen.getByText(/Author with id=1 doesn't exist/i)).then(() =>
            expect(screen.getByText(/Author 1/)).toBeInTheDocument()
        );
    });

    it('Anonymous page content', () => {
        useParams.mockReturnValue({id: 40});

        render(<AuthorItem/>);
        waitForElementToBeRemoved(screen.getByText(/Author with id=40 doesn't exist/i)).then(() =>
            expect(screen.getByText(/Anonymous/)).toBeInTheDocument()
        );
    });

    it('Not existed author`s page content', () => {
        useParams.mockReturnValue({id: -1});

        render(<AuthorItem/>);
        expect(screen.getByText(/Author with id=-1 doesn't exist/)).toBeInTheDocument();
    });

    it('Library content', () => {
        render(<SongList/>)
        wait(100).then(() => {
            for (let i = 1; i <= 15; i++) {
                expect(screen.getByText("Song " + i)).toBeInTheDocument();
            }
        });
    });
});