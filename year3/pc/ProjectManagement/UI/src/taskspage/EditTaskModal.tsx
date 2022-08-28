import React, { FormEvent, useContext, useEffect, useState } from "react";
import { User } from "../models/IUser";
import { UserStory } from "../models/IUserStory";
import { ITaskDetailsListItem } from "../models/ITaskDetailsListItem";
import { TaskContext } from "../taskspage/TaskProvider";
import { getTeamMembers } from "./taskApi";
import { Task } from "../models/ITask";
import { getListItemFromTask } from "./Tasks";
import { Sprint } from "../models/ISprint";
import { getDefaultUserStory } from "../utils/utilsMethods";
//import { getListItemFromUserStory } from "../dashboard/Dashboard";

export interface EditTaskModalProps {
  switchMode: () => void;
  sprint : Sprint;
  task: Task;
  items: ITaskDetailsListItem[];
  setItems: (items: ITaskDetailsListItem[]) => void;
  tasks: Task[];
  setTasks: (items: Task[]) => void;
}

interface EditTaskModalState {
  id: number;
  title: string;
  description: string;
  assignedToDTO?: User;
  createdByDTO: User;
  created: Date;
  userStoryDTO: UserStory;
  teamMembers?: User[];
}

const CreateTaskModal: React.FC<EditTaskModalProps> = ({
  switchMode,
  sprint,
  task,
  items,
  setItems,
  tasks,
  setTasks,
}) => {
  const { saving, savingError, saveTask } = useContext(TaskContext);

  const initialState = {...task}
    
  const [state, setState] = useState<EditTaskModalState>(initialState);

  useEffect(() => {
    setTeamMembers(state.userStoryDTO.sprintDTO.epicDTO.projectDTO.id);
  }, [state.userStoryDTO.sprintDTO.epicDTO.projectDTO.id]);

  const setTeamMembers = async (projectId: number) => {
    const teamMembers: User[] = await getTeamMembers(projectId);
    setState({ ...state, teamMembers });
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    state.created = new Date();
    saveTask?.(state).then((task: Task) => {
      if (task) {
        const index = items.findIndex((it) => it.id === task.id);
        if (index !== -1) {
          items[index] = getListItemFromTask(task);
          tasks[index] = task;
        }
        setItems(items);
        setTasks(tasks);
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
            <p className="modal-card-title">Edit Task</p>
            <form onSubmit={handleSubmit} className="form">
              <div className="field mt-4">
                <div className="label has-text-weight-light">Title</div>
                <input
                  value={state.title}
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
                  value={state.description}
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
              <div className="field mt-4">
                <div className="label has-text-weight-light">Created by</div>
                <input
                  value={`${state.createdByDTO.firstName} ${state.createdByDTO.lastName}`}
                  type="text"
                  className="input"
                  readOnly
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
                <button className="button is-dark is-fullwidth">Edit</button>
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

export default CreateTaskModal;