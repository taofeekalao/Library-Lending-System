import { PageHeader, PageHeaderHeading } from "@/components/page-header";
import { Card, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import React, {useEffect, useState} from "react";
import { Book } from "@/types/book";


import { useBookStore } from '@/hooks/useBookStore';


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
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default function BookPage(): React.ReactElement<Book> {
    return (
        <>
            <PageHeader>
            </PageHeader>
            <Card>
                <BookTable/>
            </Card>
        </>
    )
}
