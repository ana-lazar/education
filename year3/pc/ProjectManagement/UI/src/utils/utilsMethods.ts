import { Epic } from "../models/IEpic";
import { Project } from "../models/IProject";
import { Sprint } from "../models/ISprint";
import { User } from "../models/IUser";
import { UserStory } from "../models/IUserStory";
import {UserStoryDetailsListItem} from "../models/IUserStoryDetailsListItem";
import {Task} from "../models/ITask";
import axios from "axios";
import { config } from "../core";
import { ITaskDetailsListItem } from "../models/ITaskDetailsListItem";

export const getByRequestUrl = (requestUrl: string) => {
  return axios
  .get(requestUrl, config)
  .then((res) => {
    return Promise.resolve(res.data);
  })
  .catch((err) => {
    console.log(err);
    return Promise.reject(err);
  });
};

export const getViewportAsPixels = (pageSizePx: number, viewportSize: number): number => {
    return (viewportSize * pageSizePx) / 100;
};

export const getMonthFromString = (month: string): string => {
  return (new Date(Date.parse(month +" 1, 2021")).getMonth() + 1).toString();
};

export const formatDate = (date: Date): string => {
  const dateAsString: string = date.toString();
  let day: string = "";
  let month: string = "";
  let year: string = "";

  if (dateAsString.includes("(Eastern European Standard Time)")) {
    const newFormat: string = dateAsString.split("(")[0];
    const splitedDate: string [] = newFormat.split(" ");
    day = splitedDate[2];
    month = getMonthFromString(splitedDate[1]);
    year = splitedDate[3];
  }
  else {
    const splitedDate: string[] = dateAsString.split("-");
    day = splitedDate[2];
    month = splitedDate[1];
    year = splitedDate[0];
  }

  return `${day}${"."}${month}${"."}${year}`;
};

export const getDefaultProject = (): Project => {
  return {
    id: 0,
    title: ""
  };
};
  
export const getDefaultEpic = (): Epic => {
  return {
    id: 0,
    title: "",
    created: new Date(),
    projectDTO: getDefaultProject()
  };
};

export const getDefaultSprint = (): Sprint => {
  return {
    id: 0,
    title: "",
    startDate: new Date(),
    endDate: new Date(),
    epicDTO: getDefaultEpic()
  };
};

export const getDefaultUserStory = (): UserStory => {
  return {
    id: 0,
    title: "",
    description: "",
    assignedTo: getDefaultUser(),
    createdBy: getDefaultUser(),
    sprintDTO: getDefaultSprint(),
    status: "",
    created: new Date()
  };
};

export const getDefaultTask = (): Task => {
  return {
    id: 0,
    title: "",
    description: "",
    assignedToDTO: getDefaultUser(),
    createdByDTO: getDefaultUser(),
    userStoryDTO: getDefaultUserStory(),
    created: new Date()
  };
};

export const getDefaultUser = (): User => {
  return {
    id: 0,
    firstName: "",
    lastName: "",
    roleId: 0,
    email: "",
    password: "",
    roleTitle: ""
  };
};

export const getDefaultUserStoryDetailsListItem = (): UserStoryDetailsListItem => {
  return {
    id: 0,
    title: "",
    description:"",
    assignedTo: "",
    createdBy: "",
    status: "",
    created: ""
  };
};

export const getDefaultTaskDetailsListItem = (): ITaskDetailsListItem => {
  return {
    id: 0,
    title: "",
    description: "",
    assignedTo: "",
    createdBy: "",
    created: "",
  };
};

export let selectedUserStory: UserStoryDetailsListItem = getDefaultUserStoryDetailsListItem();

export const setSelectedUserStory = (value: UserStoryDetailsListItem) => {
  selectedUserStory = value;
};

export let selectedTask: ITaskDetailsListItem = getDefaultTaskDetailsListItem();

export const setSelectedTask = (value: ITaskDetailsListItem) => {
  selectedTask = value;
};

export let currentUser: User = getDefaultUser();

export const setCurrentUser = (value: User) => {
  currentUser = value;
};