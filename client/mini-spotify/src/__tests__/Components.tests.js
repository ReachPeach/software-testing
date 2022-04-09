import SongList from '../SongList'
import AuthorItem from '../AuthorItem'
import {render, screen, waitForElementToBeRemoved} from "../custom-render";
import {useParams, MemoryRouter} from "react-router-dom";
import {act, waitFor} from "@testing-library/react";

describe('Author pages', () => {

    it("can tell mocked from unmocked functions", () => {
        expect(jest.isMockFunction(useParams)).toBe(true);
        expect(jest.isMockFunction(MemoryRouter)).toBe(false);
    });

    it('Author page content', async () => {
        useParams.mockReturnValue({id: 1});

        global.fetch = jest.fn((args) => {
            if (args.includes("/author")) {
                return Promise.resolve({
                    json: () => Promise.resolve([{
                        id: 1, name: "Song 2", authors: [{
                            id: 1, name: "Author 1"
                        }]
                    }])
                })
            }
        })
        render(<AuthorItem/>);
        await waitForElementToBeRemoved(() =>
            screen.getByText(/Author with id=1 doesn't exist/i)
        );
        expect(screen.getByText(/Author 1/)).toBeInTheDocument()
    });

    it('Anonymous page content', async () => {
        useParams.mockReturnValue({id: 40});
        global.fetch = jest.fn((args) => {
            if (args.includes("/author")) {
                return Promise.resolve({
                    json: () => Promise.resolve([{
                        id: 1, name: "Song 11", authors: [{
                            id: 40, name: "Anonymous"
                        }]
                    }])
                })
            }
        })
        render(<AuthorItem/>);
        await waitForElementToBeRemoved(() =>
            screen.getByText(/Author with id=40 doesn't exist/i)
        );
        expect(screen.getByText(/Anonymous/)).toBeInTheDocument()
    });

    it('Not existed author`s page content', async () => {
        useParams.mockReturnValue({id: -1});
        global.fetch = jest.fn((args) => {
            if (args.includes("/author")) {
                return Promise.resolve({
                    json: () => Promise.resolve([])
                })
            }
        })


        render(<AuthorItem/>);
        expect(screen.getByText(/Author with id=-1 doesn't exist/i)).toBeInTheDocument();
    });
});

describe("Main page", () => {
    it('Library content', async () => {
        global.fetch = jest.fn((args) => {
            if (args.includes("/")) {
                return Promise.resolve({
                    json: () => Promise.resolve([
                        {
                            id: 1, name: "Song 1", authors: [
                                {id: 1, name: "Author 1"},
                                {id: 2, name: "Author 2"}]
                        },
                        {
                            id: 2, name: "Song 2", authors: [
                                {id: 3, name: "Author 3"}
                            ]
                        },
                        {
                            id: 3, name: "Song 3", authors: [
                                {id: 40, name: "Anonymous"}
                            ]
                        }
                    ])
                })
            }
        })

        render(<SongList/>);
        await waitFor(() => {
            for (let i = 1; i <= 3; i++) {
                expect(screen.getByText("Song " + i)).toBeInTheDocument()
                expect(screen.getByText("Author " + i)).toBeInTheDocument()
            }
            expect(screen.getByText("Anonymous")).toBeInTheDocument()
        })
    });
})