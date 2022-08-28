import React, { FormEvent, useContext, useEffect, useState } from "react";
import { AuthContext } from "../auth/AuthProvider";
import { Task } from "../models/ITask";
import { ITaskDetailsListItem } from "../models/ITaskDetailsListItem";
import { User } from "../models/IUser";
import { UserStory } from "../models/IUserStory";
import { getListItemFromTask } from "./Tasks";
import { getTeamMembers } from "./taskApi";
import { TaskContext } from "./TaskProvider";
import { getByRequestUrl, getDefaultUserStory, selectedUserStory } from "../utils/utilsMethods";
import { Sprint } from "../models/ISprint";
import { UserStoriesService } from "../utils/service";


export interface SaveTaskModalProps {
  switchMode: () => void;
  items: ITaskDetailsListItem[];
  sprint:Sprint;
  setItems: (items: ITaskDetailsListItem[]) => void;
}

interface SaveTaskModalState {
    title?: string;
    description?: string;
    assignedToDTO?: User;
    createdByDTO?: User;
    created?: Date;
    userStoryDTO: UserStory;
    teamMembers?: User[];
}

const SaveTaskModal: React.FC<SaveTaskModalProps> = ({
  switchMode,
  items,
  sprint,
  setItems,
}) => {
  const { saving, savingError, saveTask } = useContext(TaskContext);
  const { user } = useContext(AuthContext);
  const initialState = {
    createdByDTO: user,
    assignedToDTO: user,
    userStoryDTO: getDefaultUserStory()
  };
  const [state, setState] = useState<SaveTaskModalState>(initialState);

  useEffect(() => {
    setTeamMembers(state.userStoryDTO.sprintDTO.epicDTO.projectDTO.id);
  }, [state.userStoryDTO.sprintDTO.epicDTO.projectDTO.id]);

  useEffect(() => {
    getSelectedUserStory();
  }, []);

  const setTeamMembers = async (projectId: number) => {
    const teamMembers: User[] = await getTeamMembers(projectId);
    setState({ ...state, teamMembers });
  };

  const getSelectedUserStory = async () => {
    const allUserStories: UserStory[] = await getByRequestUrl(`${UserStoriesService.GET_ALL_BY_SPRINT_ID}${sprint.id}`);

    for(let i=0; i < allUserStories.length; i++) {
      if (allUserStories[i].id === selectedUserStory.id) {
        let newState = { ...state};
        newState.userStoryDTO = allUserStories[i];
        setState(newState);
        return;
      }
    }
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    
    e.preventDefault();
    state.created = new Date();
    saveTask?.(state).then((task: Task) => {
      if (task) {
        
        const newItems: ITaskDetailsListItem[] = items;
        newItems.splice(items.length, 0, getListItemFromTask(task));
        setItems(newItems);
        switchMode();
      }
    });
  };

  return (
    <div className="modal is-active">
      <div
        className="modal-background"
        onClick={() => {
          switchMode();
        }}
      />
      <div className="modal-card">
        <section className="modal-card-body has-background-light p-6">
          <div className="content">
            <p className="modal-card-title">Add Task</p>
            <form onSubmit={handleSubmit} className="form">
              <div className="field mt-4">
                <div className="label has-text-weight-light">Title</div>
                <input
                  type="text"
                  className="input"
                  onChange={(e) =>
                    setState({ ...state, title: e.currentTarget.value || "" })
                  }
                  required
                />
              </div>
              <div className="field mt-4">
                <div className="label has-text-weight-light">Description</div>
                <textarea
                  className="textarea"
                  onChange={(e) =>
                    setState({
                      ...state,
                      description: e.currentTarget.value || "",
                    })
                  }
                  required
                />
              </div>
              <div className="field is-expanded is-fullwidth mt-4">
                <label className="label has-text-weight-light">
                  Assigned to
                </label>
                <div className="control is-expanded is-fullwidth">
                  <span className="select is-fullwidth">
                    <select
                      className="has-text-weight-normal"
                      onChange={(e) => {
                        const selectedIndex =
                          e.currentTarget.options.selectedIndex;
                        setState({
                          ...state,
                          assignedToDTO: state.teamMembers?.[selectedIndex],
                        });
                      }}
                      value={`${state.assignedToDTO?.firstName} ${state.assignedToDTO?.lastName}`}
                    >
                      {state.teamMembers?.map((user) => (
                        <option key={user.id}>
                          {`${user.firstName} ${user.lastName}`}
                        </option>
                      ))}
                    </select>
                  </span>
                </div>
              </div>
              <div className="py-2">
                {savingError && (
                  <div className="info has-text-error is-size-7 mt-2">
                    {savingError.message} <br />
                    Please retry
                  </div>
                )}
              </div>
              {!saving && (
                <button className="button is-dark is-fullwidth">Save</button>
              )}
              {saving && (
                <div className="is-dark is-fullwidth mt-6">
                  <div className="columns is-fullwidth is-centered">
                    <div className="column is-one-third" />
                    <div className="column is-one-fifth mt-2">
                      <div className="control is-loading"></div>
                    </div>
                    <div className="column is-one-third" />
                  </div>
                </div>
              )}
              <button
                className="button is-fullwidth mt-5"
                onClick={() => {
                  switchMode();
                }}
              >
                Cancel
              </button>
            </form>
          </div>
        </section>
      </div>
    </div>
  );
};

export default SaveTaskModal;
