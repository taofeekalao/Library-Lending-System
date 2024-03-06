export interface Book {
    bookId: number;
    isbn: string;
    title: string;
    author: string;
    quantityInLibrary: number;
    quantityCheckedOut: number;
    quantityIn: number;
    quantityOut: number;
}


export interface BookTableProps {
    books: Book[];
}