import axios from "axios";
import { config } from "../../core";
import { User } from "../../models/IUser";
import { UserStory } from "../../models/IUserStory";
import { UsersService, UserStoriesService } from "../../utils/service";

export const createUserStory: (userStory: UserStory) => Promise<UserStory> = (
  userStory
) => {
  return axios
    .post(UserStoriesService.CREATE, userStory)
    .then((res) => {
      return Promise.resolve(res.data);
    })
    .catch((err) => {
      return Promise.reject(err);
    });
};

export const updateUserStory: (userStory: UserStory) => Promise<UserStory> = (
  userStory
) => {
  return axios
    .put(`${UserStoriesService.UPDATE}${userStory.id}`, userStory, config)
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
