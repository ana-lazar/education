import React, { FormEvent, useContext, useEffect, useState } from "react";
import { AuthContext } from "../../auth/AuthProvider";
import { Sprint } from "../../models/ISprint";
import { User } from "../../models/IUser";
import { UserStory } from "../../models/IUserStory";
import { UserStoryDetailsListItem } from "../../models/IUserStoryDetailsListItem";
import { STATUS_TO_DO } from "../../utils/generalConstants";
import { getListItemFromUserStory } from "../Dashboard";
import { getTeamMembers } from "./userStoryApi";
import { UserStoryContext } from "./UserStoryProvider";

export interface SaveUserStoryModalProps {
  switchMode: () => void;
  sprint: Sprint;
  items: UserStoryDetailsListItem[];
  setItems: (items: UserStoryDetailsListItem[]) => void;
  userStories: UserStory[];
  setUserStories: (items: UserStory[]) => void;
}

interface SaveUserStoryModalState {
  title?: string;
  description?: string;
  assignedTo?: User;
  createdBy?: User;
  sprintDTO: Sprint;
  status: string;
  created?: Date;
  teamMembers?: User[];
}

const SaveUserStoryModal: React.FC<SaveUserStoryModalProps> = ({
  switchMode,
  sprint,
  items,
  setItems,
  userStories,
  setUserStories,
}) => {
  const { saving, savingError, saveUserStory } = useContext(UserStoryContext);
  const { user } = useContext(AuthContext);
  const initialState = {
    createdBy: user,
    assignedTo: user,
    sprintDTO: sprint,
    status: STATUS_TO_DO,
  };
  const [state, setState] = useState<SaveUserStoryModalState>(initialState);

  useEffect(() => {
    setTeamMembers(state.sprintDTO.epicDTO.projectDTO.id);
  }, [state.sprintDTO.epicDTO.projectDTO.id]);

  const setTeamMembers = async (projectId: number) => {
    const teamMembers: User[] = await getTeamMembers(projectId);
    setState({ ...state, teamMembers });
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    state.created = new Date();
    saveUserStory?.(state).then((userStory: UserStory) => {
      console.log(userStory);
      if (userStory) {
        const newItems: UserStoryDetailsListItem[] = items;
        newItems.splice(items.length, 0, getListItemFromUserStory(userStory));
        userStories.splice(userStories.length, 0, userStory);
        setItems(newItems);
        setUserStories(userStories);
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
            <p className="modal-card-title">Add User Story</p>
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
                          assignedTo: state.teamMembers?.[selectedIndex],
                        });
                      }}
                      value={`${state.assignedTo?.firstName} ${state.assignedTo?.lastName}`}
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

export default SaveUserStoryModal;
