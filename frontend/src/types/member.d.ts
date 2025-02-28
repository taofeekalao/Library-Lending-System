export interface Member {
    memberId?: number;
    memberName: string;
    address: string;
    emailAddress: string;
}

export interface MemberTableProps {
    members: Member[];
}


export interface ModalProps {
    isOpen: boolean;
    onClose: (bool: boolean) => void;
    memberId: number;
    returned: boolean;

}

export interface UpdateMemberProps {
    isOpen: boolean;
    onClose: () => void;
    memberId: number;
}

export interface AddMemberProps {
    isOpen: boolean;
    onClose: () => void;
}

