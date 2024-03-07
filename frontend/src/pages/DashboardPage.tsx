import { PageHeader } from "@/components/page-header";
import { Card, CardHeader, CardTitle } from "@/components/ui/card";
import React, { useState} from "react";
import { Book } from "@/types/book";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const BorrowBook: React.FC = () => {

    const [memberId, setMemberId] = useState<string>('');
    const [bookId, setBookId] = useState<string>('');

    const handleBookId = (e: React.ChangeEvent<HTMLInputElement>) => {
        setBookId(e.target.value);
    }
    const handleMemberId = (e: React.ChangeEvent<HTMLInputElement>) => {
        setMemberId(e.target.value);
    }
    const borrowBook = () => {
        fetch('http://localhost:8080/library/borrowed?member_Id=' + memberId + '&isbn=' + bookId).then(response => {
            if (response.ok) {
                toast.success('Book borrowed successfully')
            } else {
                toast.error('Failed to borrow book')
            }
        })
        setBookId('');
        setMemberId('');
    }

    return (
        <Card className="w-1/2 mr-2 mt-2">
            <CardHeader>
                <CardTitle className="flex justify-center m-3">Borrow Book</CardTitle>
            </CardHeader>
            <div>
                <form onSubmit={(e) => { e.preventDefault(); borrowBook()}} className="m-3">
                    <div className="mb-4">
                        <label htmlFor="memberName" className="block text-gray-700 text-sm font-bold mb-2">MemberId</label>
                        <input type="text" id="memberName" name="memberName" value={memberId}
                               onChange={handleMemberId}
                               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
                    </div>
                    <div className="mb-4">
                        <label htmlFor="address" className="block text-gray-700 text-sm font-bold mb-2">Book ISBN</label>
                        <input type="text" id="address" name="address" value={bookId}
                               onChange={handleBookId}
                               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
                    </div>
                    <div className="flex justify-center m-3">
                        <button type="submit" className="p-2 text-white bg-blue-500 hover:bg-blue-700 rounded">
                            Borrow Book
                        </button>
                    </div>
                </form>
            </div>
        </Card>
    );

}

const ReturnBook: React.FC = () => {

    const [memberId, setMemberId] = useState<string>('');
    const [bookId, setBookId] = useState<string>('');

    const handleBookId = (e: React.ChangeEvent<HTMLInputElement>) => {
        setBookId(e.target.value);
    }
    const handleMemberId = (e: React.ChangeEvent<HTMLInputElement>) => {
        setMemberId(e.target.value);
    }
    const returnBook = () => {
        fetch('http://localhost:8080/library/returned?member_Id=' + memberId + '&isbn=' + bookId).then(response => {
            if (response.ok) {
                toast.success('Book returned successfully')
            } else {
                toast.error('Failed to return book')
            }
        })

        setBookId('');
        setMemberId('');
    }

    return (
        <Card className="w-1/2 ml-2">
            <CardHeader>
                <CardTitle className="flex justify-center m-3">Return Book</CardTitle>
            </CardHeader>
            <div>
                <form onSubmit={(e) => { e.preventDefault(); returnBook()}} className="m-3">
                    <div className="mb-4">
                        <label htmlFor="memberName" className="block text-gray-700 text-sm font-bold mb-2">MemberId</label>
                        <input type="text" id="memberName" name="memberName" value={memberId}
                               onChange={handleMemberId}
                               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
                    </div>
                    <div className="mb-4">
                        <label htmlFor="address" className="block text-gray-700 text-sm font-bold mb-2">Book ISBN</label>
                        <input type="text" id="address" name="address" value={bookId}
                               onChange={handleBookId}
                               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
                    </div>
                    <div className="flex justify-center m-3">
                        <button type="submit" className="p-2 text-white bg-blue-500 hover:bg-blue-700 rounded">
                            Return Book
                        </button>
                    </div>
                </form>
            </div>
        </Card>
    );

}
export default function DashboardPage(): React.ReactElement<Book> {
    return (
        <>
            <PageHeader>
                <ToastContainer/>
                {/*<PageHeaderHeading>Book</PageHeaderHeading>*/}
                <BorrowBook/>
                <ReturnBook/>
            </PageHeader>
        </>
    )
}
