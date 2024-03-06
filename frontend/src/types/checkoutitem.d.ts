export interface CheckOutItem {
    checkedOutBookItemId: number;
    book: string;
    member: string;
    checkoutDate: string;
    dueDate: string;
    returnStatus: boolean;
    returnDate: string;
}