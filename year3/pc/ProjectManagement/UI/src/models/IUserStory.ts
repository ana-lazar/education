import { Sprint } from "./ISprint";
import { User } from "./IUser";

export interface UserStory {
    id: number;
    title: string;
    description: string;
    assignedTo: User;
    createdBy: User;
    sprintDTO: Sprint;
    status: string;
    created: Date;
}