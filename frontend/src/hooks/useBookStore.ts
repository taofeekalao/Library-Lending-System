import {Book } from '@/types/book';
import {create} from "zustand";

interface BookState {
    books: Book[];
    setBooks: (books: Book[]) => void;
    fetchBooks: () => Promise<void>;
}

export const useBookStore = create<BookState>((set) => ({
    books: [],
    setBooks: (books) => set({ books }),
    fetchBooks: async () => {
        try {
            const response = await fetch('http://localhost:8080/library/bookList');
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const books = await response.json();
            set({ books });
        } catch (error) {
            console.error("Failed to fetch books", error);
        }
    },
}));