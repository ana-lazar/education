import { Sprint } from "../../models/ISprint";
import { User } from "../../models/IUser";

export interface UserStoryProps {
  id?: number;
  title?: string;
  description?: string;
  assignedTo?: User;
  createdBy?: User;
  sprintDTO?: Sprint;
  status?: string;
  created?: Date;
}
