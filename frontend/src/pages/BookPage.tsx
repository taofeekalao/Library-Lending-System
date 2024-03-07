import { PageHeader, PageHeaderHeading } from "@/components/page-header";
import { Card, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import React, {useEffect, useState} from "react";
import * as Dialog from "@radix-ui/react-dialog";

import { useBookStore } from '@/hooks/useBookStore';
import {AddBookProps, Book } from "@/types/book";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AddBook: React.FC<AddBookProps> = ({isOpen, onClose}) => {
    const [book, setBook] = useState<Book>({
        isbn: '',
        title: '',
        author: '',
        quantityInLibrary: 0,
        quantityCheckedOut: 0,
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setBook({...book, [name]: value});
    }

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        fetch('http://localhost:8080/library/book', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(book)
        }).then(response => {
            resetForm()
            if(response.ok) {
                toast.success('Book added successfully')
                onClose();
            } else {
                toast.error('Book to add member')
            }
        })
            .catch(error => {
                onClose();
                resetForm()
                toast.error('Book to add member')
            });
    }

    const resetForm = () => {
        setBook({
            isbn: '',
            title: '',
            author: '',
            quantityInLibrary: 0,
            quantityCheckedOut: 0
        });

    }

    return (
        <Dialog.Root open={isOpen} onOpenChange={(bool) => {
            if (!bool) {
                resetForm();
                onClose();
            }
        }}>
            <Dialog.Portal>
                <Dialog.Overlay className="fixed inset-0 bg-black/30" />
                <Dialog.Content className="fixed p-6 bg-white rounded top-2/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-1/2">
                    <Dialog.Title>
                        <div className="text-center font-bold">Add Book</div>
                    </Dialog.Title>
                    <div>
                        <form onSubmit={handleSubmit}>
                            <div className="mb-4">
                                <label htmlFor="title" className="block text-gray-700 text-sm font-bold mb-2">Title</label>
                                <input type="text" id="title" name="title" value={book.title} onChange={handleInputChange} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                            </div>
                            <div className="mb-4">
                                <label htmlFor="author" className="block text-gray-700 text-sm font-bold mb-2">Author</label>
                                <input type="text" id="author" name="author" value={book.author} onChange={handleInputChange} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                            </div>
                            <div className="mb-4">
                                <label htmlFor="isbn" className="block text-gray-700 text-sm font-bold mb-2">ISBN</label>
                                <input type="text" id="isbn" name="isbn" value={book.isbn} onChange={handleInputChange} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                            </div>
                            <div className="mb-4">
                                <label htmlFor="quantityInLibrary" className="block text-gray-700 text-sm font-bold mb-2">Quantity In Library</label>
                                <input type="number" id="quantityInLibrary" name="quantityInLibrary" value={book.quantityInLibrary} onChange={handleInputChange} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                            </div>
                            <div className="mb-4">
                                <label htmlFor="quantityCheckedOut" className="block text-gray-700 text-sm font-bold mb-2">Quantity Checked Out</label>
                                <input type="number" id="quantityCheckedOut" name="quantityCheckedOut" value={book.quantityCheckedOut} onChange={handleInputChange} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                            </div>
                            <div className="flex justify-center m-3">
                                <button type="submit" className="p-2 text-white bg-blue-500 hover:bg-blue-700 rounded">
                                    Add Book
                                </button>
                            </div>
                        </form>
                    </div>
                    <Dialog.Close />
                </Dialog.Content>
            </Dialog.Portal>
        </Dialog.Root>
    );

}

const BookTable: React.FC = () => {

    const books = useBookStore((state) => state.books);
    const fetchBooks = useBookStore((state) => state.fetchBooks);

    useEffect(() => {
        fetchBooks().then(() => console.log(books));
    }, [fetchBooks]);

    return (
        <table className="table-auto w-full">
            <thead>
            <tr>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Title</th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Author</th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ISBN</th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Quantity
                    In Library
                </th>
                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider2">Quantity
                    Checked Out
                </th>

                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider"> Available </th>
            </tr>
            </thead>
            <tbody>
            {books.map((book) => (
                <tr key={book.bookId}>
                    <td className="px-5 py-5 border-b border-gray-200 text-sm">{book.title}</td>
                    <td className="px-5 py-5 border-b border-gray-200 text-sm">{book.author}</td>
                    <td className="px-5 py-5 border-b border-gray-200 text-sm">{book.isbn}</td>
                    <td className="px-5 py-5 border-b border-gray-200 text-sm">{book.quantityInLibrary}</td>
                    <td className="px-5 py-5 border-b border-gray-200 text-sm">{book.quantityCheckedOut}</td>
                    <td className="px-5 py-5 border-b border-gray-200 text-sm">
                        {book.quantityInLibrary - book.quantityCheckedOut >= 0 ? (
                            <span
                                className="inline-flex items-center justify-center px-2 py-1 text-xs font-bold leading-none text-green-600 bg-green-200 rounded-full">True</span>
                        ) : (
                            <span
                                className="inline-flex items-center justify-center px-2 py-1 text-xs font-bold leading-none text-red-600 bg-red-200 rounded-full">False</span>
                        )}
                    </td>

                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default function BookPage(): React.ReactElement<Book> {
    const [isOpen, setIsOpen] = useState(false);
    const fetchBooks = useBookStore((state) => state.fetchBooks);

    const addBook = () => {
        setIsOpen(true);
    }

    useEffect(() => {
        fetchBooks().then(() => console.log('Books fetched'));
    }, [isOpen]);
    return (
        <>
            <PageHeader>
                <ToastContainer/>
            </PageHeader>
            <Card>
                <CardHeader>
                    <div className="flex justify-end items-center">
                        <button onClick={addBook} className="p-2 text-white bg-blue-500 hover:bg-blue-700 rounded">Add
                            Book
                        </button>
                    </div>
                </CardHeader>
                <AddBook isOpen={isOpen} onClose={() => setIsOpen(false)}/>
                <BookTable/>
            </Card>
        </>
    )
}
