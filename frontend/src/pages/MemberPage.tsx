import { PageHeader } from "@/components/page-header";
import { Card, CardHeader } from "@/components/ui/card";
import * as Dialog from '@radix-ui/react-dialog';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import React, {useEffect} from "react";
import { Member, ModalProps, AddMemberProps, UpdateMemberProps } from "@/types/member";
import { CheckOutItem} from "@/types/checkoutitem";
import { useMemberStore } from '@/hooks/useMemberStore';


const UpdateMember: React.FC<UpdateMemberProps> = ({isOpen, onClose, memberId}) => {
    const [member, setMember] = React.useState<Member>({
        memberId: 0,
        memberName: '',
        address: '',
        emailAddress: ''
    });

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setMember({ ...member, [name]: value });
    };

    useEffect(() => {
        fetch(`http://localhost:8080/library/member/${memberId}`)
            .then(response => response.json())
            .then(data => {
                setMember(data);
            });
    }, [memberId, isOpen]);

    const resetForm = () => {
        setMember({ memberId: 0, memberName: '', address: '', emailAddress: ''})
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        fetch('http://localhost:8080/library/member/' + member.memberId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(member)
        })
            .then(response => {
                resetForm()
                if(response.ok) {
                    toast.success('Member updated successfully')
                    onClose();
                } else {
                    toast.error('Failed to update member')
                }
            })
            .catch(() => {
                resetForm()
                onClose();
                toast.error('Failed to update member')
            });
    }

    return (
        <Dialog.Root open={isOpen} onOpenChange={onClose}>
            <Dialog.Portal>
                <Dialog.Overlay className="fixed inset-0 bg-black/30" />
                <Dialog.Content className="fixed p-6 bg-white rounded top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-1/2">
                    <Dialog.Title>
                        <div className="text-center font-bold">Update Member</div>
                    </Dialog.Title>
                    <div>
                        <form onSubmit={handleSubmit}>
                            <div className="mb-4">
                                <label htmlFor="memberId" className="block text-gray-700 text-sm font-bold mb-2">Member
                                    ID</label>
                                <input disabled type="text" id="memberId" name="memberId" value={member.memberId}
                                       onChange={handleInputChange}
                                       className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
                            </div>
                            <div className="mb-4">
                                <label htmlFor="emailAddress"
                                       className="block text-gray-700 text-sm font-bold mb-2">Email</label>
                                <input disabled type="email" id="emailAddress" name="emailAddress"
                                       value={member.emailAddress} onChange={handleInputChange}
                                       className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
                            </div>
                            <div className="mb-4">
                                <label htmlFor="address"
                                       className="block text-gray-700 text-sm font-bold mb-2">Address</label>
                                <input type="text" id="address" name="address" value={member.address}
                                       onChange={handleInputChange}
                                       className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
                            </div>
                            <div className="mb-4">
                                <label htmlFor="memberName"
                                       className="block text-gray-700 text-sm font-bold mb-2">Name</label>
                                <input type="text" id="memberName" name="memberName" value={member.memberName}
                                       onChange={handleInputChange}
                                       className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
                            </div>

                            <div className="flex justify-end">
                                <button type="submit"
                                        className="p-2 text-white bg-blue-500 hover:bg-blue-700 rounded">Submit
                                </button>
                            </div>
                        </form>
                    </div>
                    <Dialog.Close/>
                </Dialog.Content>
            </Dialog.Portal>
        </Dialog.Root>
    );
}

const MemberTable: React.FC = () => {
    const members = useMemberStore((state) => state.members);
    const fetchMembers = useMemberStore((state) => state.fetchMembers);

    const [isOpen, setIsOpen] = React.useState(false);
    const [memberId, setMemberId] = React.useState(0);
    const [returned, setReturned] = React.useState(false);

    const [isOpenUpdate, setIsOpenUpdate] = React.useState(false);

    const updateMember = (id: number) => {
        setMemberId(id);
        setIsOpenUpdate(true);
    }

    const closeModalUpdate = () => { setIsOpenUpdate(false) }

    useEffect(() => {
        fetchMembers().then(() => console.log(members));
    }, [fetchMembers]);

    useEffect(() => {
        fetchMembers().then(() => console.log(members));
    }, [isOpenUpdate]);

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
                if (data.ok) {
                    toast.success('Member deleted successfully');
                } else {
                    toast.error('Failed to delete member');
                }
                fetchMembers().then(r => console.log(r));
            })
            .catch(error => {
                toast.error('Failed to delete member');
            });
    }


    return (
        <div>
            <MemberCheckout isOpen={isOpen} onClose={closeModal} memberId={memberId} returned={returned} />
            <UpdateMember isOpen={isOpenUpdate} onClose={closeModalUpdate} memberId={memberId} />
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
                                onClick={() => updateMember(member.memberId as number)}
                                className="p-2 text-white bg-yellow-500 hover:bg-yellow-700 rounded">
                               Update Member
                            </button>
                            <button
                                onClick={() => deleteMember(member.memberId as number)}
                                className="p-2 text-white bg-red-500 hover:bg-red-700 rounded"
                            >
                                Delete Member
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
            fetch(`http://localhost:8080/library/member/${memberId}/checkoutitems?returned=${returned}`)
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
                                <th className="pr-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                                <th className="pr-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Member</th>
                                <th className="pr-32 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Book</th>
                                <th className="pr-4 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Checkout Date</th>
                                <th className="pr-16 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Due Date</th>
                                <th className="pr-8 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Return Status</th>
                                <th className="pr-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Return Date</th>
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
    const [emailValid, setEmailValid] = React.useState<Boolean>(true);

    const validateEmail = (email: string) => {
        // Simple regex for basic email validation
        return /\S+@\S+\.\S+/.test(email);
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;

        setMember({ ...member, [name]: value });

        if (name === 'emailAddress') {
            setEmailValid(validateEmail(value));
        }
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!emailValid) {
            return;
        }
        fetch('http://localhost:8080/library/member', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(member)
        })
            .then(response => {
                resetForm()
                if(response.ok) {
                    toast.success('Member added successfully')
                    onClose();
                } else {
                    toast.error('Failed to add member')
                }
            })
            .catch(() => {
                onClose();
                resetForm()
                toast.error('Failed to add member')
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
                                <label htmlFor="memberName"
                                       className="block text-gray-700 text-sm font-bold mb-2">Name</label>
                                <input type="text" id="memberName" name="memberName" value={member.memberName}
                                       onChange={handleInputChange}
                                       className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
                            </div>
                            <div className="mb-4">
                                <label htmlFor="address"
                                       className="block text-gray-700 text-sm font-bold mb-2">Address</label>
                                <input type="text" id="address" name="address" value={member.address}
                                       onChange={handleInputChange}
                                       className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
                            </div>
                            <div className="mb-4">
                                <label htmlFor="emailAddress" className="block text-gray-700 text-sm font-bold mb-2">
                                    Email
                                </label>
                                <input
                                    type="email"
                                    id="emailAddress"
                                    name="emailAddress"
                                    value={member.emailAddress}
                                    onChange={handleInputChange}
                                    className={`shadow appearance-none border ${emailValid ? 'border-gray-300' : 'border-red-500'} rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline`}
                                />
                                {!emailValid &&
                                    <p className="text-red-500 text-xs italic">Please enter a valid email address.</p>}
                            </div>
                            <div className="flex justify-end">
                                <button type="submit"
                                        className="p-2 text-white bg-blue-500 hover:bg-blue-700 rounded">Submit
                                </button>
                            </div>
                        </form>
                    </div>
                    <Dialog.Close/>
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
                <ToastContainer/>
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
