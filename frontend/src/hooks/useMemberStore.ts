import {Member} from "@/types/member";
import {create} from "zustand";

interface MemberState {
    members: Member[];
    setMembers: (members: Member[]) => void;
    fetchMembers: () => Promise<void>;
}

export const useMemberStore = create<MemberState>((set) => ({
    members: [],
    setMembers: (members) => set({ members }),
    fetchMembers: async () => {
        try {
            const response = await fetch('http://localhost:8080/library/member/');
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const members = await response.json();
            set({ members });
        } catch (error) {
            console.error("Failed to fetch members", error);
        }
    },
}));