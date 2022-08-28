import { Sprint } from "../models/ISprint";
import { User } from "../models/IUser"
import { UserStory } from "../models/IUserStory";

export interface TaskProps {
  id?: number;
  title?: string;
  description?: string;
  assignedToDTO?: User;
  createdByDTO?: User;
  created?: Date;
  userStoryDTO?: UserStory;
}