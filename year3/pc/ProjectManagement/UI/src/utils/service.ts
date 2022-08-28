import { projectBaseUrl } from "./generalConstants";

export const sprintsBaseUrl: string = `${projectBaseUrl}${"sprints/"}`;
export const userStoriesBaseUrl: string = `${projectBaseUrl}${"user-stories/"}`;
export const tasksBaseUrl: string = `${projectBaseUrl}${"tasks/"}`;

export namespace SprintsService {
  export const GET_CURRENT_SPRINT: string = `${sprintsBaseUrl}${"get-current-sprint"}`;
}

export namespace UsersService {
  export const GET_ALL_FOR_PROJECT: string = `${projectBaseUrl}${"get-all-by-project/"}`;
}

export namespace UserStoriesService {
  export const GET_ALL_BY_SPRINT_ID: string = `${userStoriesBaseUrl}${"get-all-by-sprint/"}`;
  export const DELETE_BY_ID: string = `${userStoriesBaseUrl}${"delete/"}`;
  export const CREATE: string = `${userStoriesBaseUrl}${"create/"}`;
  export const UPDATE: string = `${userStoriesBaseUrl}${"update/"}`;
}

export namespace TasksService {
  export const GET_ALL_BY_USER_STORY_ID: string = `${tasksBaseUrl}${"get-all-by-user-story/"}`;
  export const DELETE_BY_ID: string = `${tasksBaseUrl}${"delete/"}`;
  export const UPDATE: string = `${tasksBaseUrl}${"update/"}`;
  export const CREATE: string = `${tasksBaseUrl}${"create/"}`;
}
