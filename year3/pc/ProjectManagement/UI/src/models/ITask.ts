import { User } from "./IUser";
import { UserStory } from "./IUserStory";

export interface Task {
    id: number;
    title: string;
    description: string;
    assignedToDTO: User;
    createdByDTO: User;
    created: Date;
    userStoryDTO: UserStory;
}