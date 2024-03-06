import { PageHeader, PageHeaderHeading } from "@/components/page-header";
import { Card, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import * as Dialog from '@radix-ui/react-dialog';

import React, {useEffect} from "react";
import { Member, ModalProps, AddMemberProps } from "@/types/member";
import { CheckOutItem} from "@/types/checkoutitem";
import { useMemberStore } from '@/hooks/useMemberStore';

const MemberTable: React.FC = () => {
    const members = useMemberStore((state) => state.members);
    const fetchMembers = useMemberStore((state) => state.fetchMembers);

    const [isOpen, setIsOpen] = React.useState(false);
    const [memberId, setMemberId] = React.useState(0);
    const [returned, setReturned] = React.useState(false);

    useEffect(() => {
        fetchMembers().then(() => console.log(members));
    }, [fetchMembers]);

    const openModal = (id: number, returned: boolean) => {
        setMemberId(id);
        setReturned(returned);
        setIsOpen(true);
    }

    const closeModal = () => { setIsOpen(false) }

    const deleteMember = (id: number) => {
        fetch(`http://localhost:8080/library/member/${id}`, {
            method: 'DELETE',
        })
            .then(data => {
                console.log('Success:', data);
                fetchMembers().then(r => console.log(r));
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    return (
        <div>
            <MemberCheckout isOpen={isOpen} onClose={closeModal} memberId={memberId} returned={returned} />
            <table className="table-auto w-full">
                <thead>
                <tr>
                    <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Member ID
                    </th>
                    <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Name
                    </th>
                    <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Address
                    </th>
                    <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Email
                    </th>
                    <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Actions
                    </th>
                </tr>
                </thead>
                <tbody>
                {members.map((member) => (
                    <tr key={member.memberId}>
                        <td className="px-5 py-5 border-b border-gray-200 text-sm">
                            {member.memberId}
                        </td>
                        <td className="px-5 py-5 border-b border-gray-200 text-sm">
                            {member.memberName}
                        </td>
                        <td className="px-5 py-5 border-b border-gray-200 text-sm">
                            {member.address}
                        </td>
                        <td className="px-5 py-5 border-b border-gray-200 text-sm">
                            {member.emailAddress}
                        </td>
                        <td className="px-5 py-5 border-b border-gray-200 text-sm flex justify-between">
                            <button onClick={() => openModal(member.memberId as number, false)}
                                    className="p-2 text-white bg-blue-500 hover:bg-blue-700 rounded">
                                BorrowList
                            </button>
                            <button onClick={() => openModal(member.memberId as number, true)}
                                    className="p-2 text-white bg-green-500 hover:bg-green-700 rounded">
                                ReturnList
                            </button>
                            <button
                                onClick={() => deleteMember(member.memberId as number)}
                                className="p-2 text-white bg-red-500 hover:bg-red-700 rounded"
                            >
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

const MemberCheckout: React.FC<ModalProps> = ({isOpen, onClose, memberId, returned}) => {

    const[checkedOutItems, setCheckedOutItems] = React.useState<CheckOutItem[]>([]);

    useEffect(() => {
        setCheckedOutItems([]);
        if(memberId === 0) return;
        try {
            fetch(`http://localhost:8080/member/${memberId}/checkoutitems?returned=${returned}`)
                .then(response => response.json())
                .then(data => setCheckedOutItems(data));
        } catch (error) {
            console.error("Failed to fetch checkout items", error);
        }
    }, [isOpen, memberId, returned]);


    return (
        <Dialog.Root open={isOpen} onOpenChange={onClose}>
            <Dialog.Portal>
                <Dialog.Overlay className="fixed inset-0 bg-black/30" />
                <Dialog.Content className="fixed p-6 bg-white rounded top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2">
                    <Dialog.Title>
                        <div className="mb-6">{returned ? 'Return List' : 'Borrow List'}</div>
                    </Dialog.Title>
                    <div>
                        <table className="min-w-full table-auto">
                            <thead>
                            <tr>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Member</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Book</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Checkout Date</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Due Date</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Return Status</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Return Date</th>
                            </tr>
                            </thead>
                            <tbody>
                            {checkedOutItems.map(item => (
                                <tr key={item.checkedOutBookItemId}>
                                    <td>{item.checkedOutBookItemId}</td>
                                    <td>{item.member}</td>
                                    <td>{item.book}</td>
                                    <td>{item.checkoutDate}</td>
                                    <td>{item.dueDate}</td>
                                    <td>{item.returnStatus ? 'Returned' : 'Not Returned'}</td>
                                    <td>{item.returnDate || 'N/A'}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </Dialog.Content>
            </Dialog.Portal>
        </Dialog.Root>
    );
}

const AddMember: React.FC<AddMemberProps> = ({isOpen, onClose}) => {
    const [member, setMember] = React.useState<Member>({
        memberName: '',
        address: '',
        emailAddress: ''
    });

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setMember({ ...member, [name]: value });
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        fetch('http://localhost:8080/library/member', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(member)
        })
            .then(data => {
                resetForm()
                onClose();
            })
            .catch(error => {
                onClose();
                resetForm()
            });
    }

    const resetForm = () => {
        setMember({ memberName: '', address: '', emailAddress: ''})
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
                        <div className="text-center font-bold">Add Member</div>
                    </Dialog.Title>
                    <div>
                        <form onSubmit={handleSubmit}>
                            <div className="mb-4">
                                <label htmlFor="memberName" className="block text-gray-700 text-sm font-bold mb-2">Name</label>
                                <input type="text" id="memberName" name="memberName" value={member.memberName} onChange={handleInputChange} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                            </div>
                            <div className="mb-4">
                                <label htmlFor="address" className="block text-gray-700 text-sm font-bold mb-2">Address</label>
                                <input type="text" id="address" name="address" value={member.address} onChange={handleInputChange} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                            </div>
                            <div className="mb-4">
                                <label htmlFor="emailAddress" className="block text-gray-700 text-sm font-bold mb-2">Email</
                                    label>
                                <input type="text" id="emailAddress" name="emailAddress" value={member.emailAddress} onChange={handleInputChange} className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                            </div>
                            <div className="flex justify-end">
                                <button type="submit" className="p-2 text-white bg-blue-500 hover:bg-blue-700 rounded">Submit</button>
                            </div>
                        </form>
                    </div>
                    <Dialog.Close />
                </Dialog.Content>
            </Dialog.Portal>
        </Dialog.Root>
    );
}

export default function MemberPage(): React.ReactElement<Member> {
    const [isOpen, setIsOpen] = React.useState(false);

    const fetchMembers = useMemberStore((state) => state.fetchMembers);
    const addMember = () => {
        setIsOpen(true);
    }

    useEffect(() => {
        fetchMembers().then(() => console.log('Members fetched'));
    }, [isOpen]);

    return (
        <>
            <PageHeader>
            </PageHeader>
            <Card>
                <CardHeader>
                    <div className="flex justify-end items-center">
                        <button onClick={addMember} className="p-2 text-white bg-blue-500 hover:bg-blue-700 rounded">Add
                            Member
                        </button>
                    </div>
                </CardHeader>
                <AddMember isOpen={isOpen} onClose={() => setIsOpen(false)}/>
                <MemberTable/>
            </Card>
        </>
    )
};
