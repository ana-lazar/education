import React, { useCallback, useState } from "react";
import PropTypes from "prop-types";
import { UserStoryProps } from "./UserStoryProps";
import { createUserStory, updateUserStory } from "./userStoryApi";

type SaveUserStoryFn = (userStory: UserStoryProps) => Promise<any>;

export interface UserStoryState {
  saving: boolean;
  savingError?: Error | null;
  saveUserStory?: SaveUserStoryFn;
}

const initialState: UserStoryState = {
  saving: false,
};

export const UserStoryContext = React.createContext<UserStoryState>(
  initialState
);

interface UserStoryProviderProps {
  children: PropTypes.ReactNodeLike;
}

export const UserStoryProvider: React.FC<UserStoryProviderProps> = ({
  children,
}) => {
  const [state, setState] = useState<UserStoryState>(initialState);
  const { saving, savingError } = state;
  const saveUserStory = useCallback<SaveUserStoryFn>(saveUserStoryCallback, []);
  const value = {
    saving,
    savingError,
    saveUserStory,
  };

  return (
    <UserStoryContext.Provider value={value}>
      {children}
    </UserStoryContext.Provider>
  );

  async function saveUserStoryCallback(userStory: UserStoryProps) {
    try {
      setState({ ...state, savingError: null, saving: true });
      const userStoryDto = {
        id: userStory.id!,
        title: userStory.title!,
        description: userStory.description!,
        createdBy: userStory.createdBy!,
        assignedTo: userStory.assignedTo!,
        sprintDTO: userStory.sprintDTO!,
        status: userStory.status!,
        created: userStory.created!,
      };
      const savedUserStory = await (userStory.id
        ? updateUserStory(userStoryDto)
        : createUserStory(userStoryDto));
      setState({ ...state, savingError: null, saving: false });
      return savedUserStory;
    } catch (error: any) {
      setState({ ...state, savingError: error, saving: false });
    }
  }
};
