import React, { useCallback, useState } from "react";
import PropTypes from "prop-types";
import {TaskProps } from "./TaskProps";
import { createTask, updateTask } from "./taskApi";

type SaveTaskFn = (task: TaskProps) => Promise<any>;

export interface TaskState {
  saving: boolean;
  savingError?: Error | null;
  saveTask?: SaveTaskFn;
}

const initialState: TaskState = {
  saving: false,

};

export const TaskContext = React.createContext<TaskState>(
  initialState
);

interface TaskProviderProps {
  children: PropTypes.ReactNodeLike;
}

export const TaskProvider: React.FC<TaskProviderProps> = ({
  children,
}) => {
  const [state, setState] = useState<TaskState>(initialState);
  const { saving, savingError } = state;
  const saveTask = useCallback<SaveTaskFn>(saveTaskCallback, []);
  const value = {
    saving,
    savingError,
    saveTask,
  };

  return (
    <TaskContext.Provider value={value}>
      {children}
    </TaskContext.Provider>
  );

  async function saveTaskCallback(task: TaskProps) {
    try {
      setState({ ...state, savingError: null, saving: true });
      const TaskDto = {
        id: task.id!,
        title: task.title!,
        description: task.description!,
        assignedToDTO: task.assignedToDTO!,
        createdByDTO: task.createdByDTO!,
        created: task.created!,
        userStoryDTO: task.userStoryDTO!,
      };
      const savedTask = await (task.id
        ? updateTask(TaskDto)
        : createTask(TaskDto));
      setState({ ...state, savingError: null, saving: false });
      return savedTask;
    } catch (error: any) {
      setState({ ...state, savingError: error, saving: false });
    }
  }
};
