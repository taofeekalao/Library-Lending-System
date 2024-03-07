export interface Book {
    bookId?: number;
    isbn: string;
    title: string;
    author: string;
    quantityInLibrary: number;
    quantityCheckedOut: number;
}

export interface BookTableProps {
    books: Book[];
}

export interface AddBookProps {
    isOpen: boolean;
    onClose: () => void;
}