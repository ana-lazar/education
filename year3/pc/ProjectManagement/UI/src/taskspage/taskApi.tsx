import axios from "axios";
import { config } from "../core";
import { User } from "../models/IUser";
import { Task } from "../models/ITask";
import { UsersService, TasksService } from "../utils/service";

export const createTask: (task: Task) => Promise<Task> = (task) => {
  
    return axios
      .post(TasksService.CREATE, task)
      .then((res) => {
        return Promise.resolve(res.data);
      })
      .catch((err) => {
        return Promise.reject(err);
      });
  };
  
  export const updateTask: (task: Task) => Promise<Task> = (task) => {
    return axios
      .put(`${TasksService.UPDATE}${task.id}`, task, config)
      .then((res) => {
        return Promise.resolve(res.data);
      })
      .catch((err) => {
        return Promise.reject(err);
      });
  };
  
  export const getTeamMembers: (projectId: number) => Promise<User[]> = (
    projectId
  ) => {
    return axios
      .get(`${UsersService.GET_ALL_FOR_PROJECT}${projectId}`, config)
      .then((res) => {
        return Promise.resolve(res.data);
      })
      .catch((err) => {
        return Promise.reject(err);
      });
  };