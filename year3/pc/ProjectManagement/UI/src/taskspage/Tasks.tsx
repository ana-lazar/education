import { CommandBar, DetailsList, IColumn, IContextualMenuItem, Stack, StackItem, ThemeProvider } from "@fluentui/react";
import { DetailsListLayoutMode, IObjectWithKey, Selection, SelectionMode } from '@fluentui/react/lib/DetailsList';
import React, { useCallback, useEffect, useState} from "react";
import { useNavigate} from "react-router-dom";
import { useAuth } from "../auth/AuthProvider";
import { Task } from "../models/ITask";
import { ITaskDetailsListItem } from "../models/ITaskDetailsListItem";
import { SprintsService, TasksService } from "../utils/service";
import {currentUser, formatDate, getByRequestUrl, getDefaultSprint, getViewportAsPixels, selectedTask, selectedUserStory, setSelectedTask} from "../utils/utilsMethods";
import { commandBarStyles, defaultMenuItemStyle, detailsListColumnStyle, enabledMenuItemStyle, itemStyle, itemStyleForLastColumn, setGapBetweenHeaders, setGapBetweenHeadersAndDetailsList, setGapBetweenHeadersAndUserInfo, setStyleForUserName, setStyleForUserRole, transparentTheme } from "./Tasks.styles";
import {ITaskProps} from "../models/ITaskProps";
import { ADD, DELETE, EDIT } from "../utils/generalConstants";
import SaveTaskModal from "./SaveTaskModal";
import EditTaskModal from "./EditTaskModal";
import { Sprint } from "../models/ISprint";

const TITLE_COLUMN: string = "Title";
const DESCRIPTION_COLUMN: string = "Description";
const ASSIGNED_TO_COLUMN: string = "Assigned to";
const CREATED_BY_COLUMN: string = "Created by";
const CREATED_COLUMN: string = "Created at";
const BACKLOG_TITLE: string = "Tasks";

const getColumnName = (title: string, description: string, assignedToDTO: string, createdByDTO: string, created : string, name: string): string => {
    return name === title ? title : name === description ? description : name === assignedToDTO ? assignedToDTO : name === createdByDTO ? createdByDTO : name  === created ? created : name;
};

const getFieldName = (columnName: string): string => {
    return columnName === TITLE_COLUMN ? "title" :  columnName === DESCRIPTION_COLUMN ? "description" : columnName === ASSIGNED_TO_COLUMN ? "assignedTo" : columnName === CREATED_BY_COLUMN ? "createdBy" : columnName === CREATED_COLUMN ? "created" : "";
};

const getColumn = (pageWidth: number, name: string): IColumn => {
    return {
        key: name,
        name: getColumnName(TITLE_COLUMN, DESCRIPTION_COLUMN, ASSIGNED_TO_COLUMN, CREATED_BY_COLUMN, CREATED_COLUMN, name),
        fieldName: getFieldName(name),
        minWidth: getViewportAsPixels(pageWidth, 15),
        maxWidth: getViewportAsPixels(pageWidth, 20),
        isResizable: true,
        isMultiline: true,
        styles: detailsListColumnStyle
    };
};

const getColumns = (pageWidth: number, names: string[]): IColumn[] => {
    return names.map((name: string) => getColumn(pageWidth, name));
};


export const getListItemFromTask = (task : Task): ITaskDetailsListItem => {
    return {
        id: task.id,
        title: task.title,
        description: task.description,
        assignedTo: `${task.assignedToDTO.firstName}${" "}${task.assignedToDTO.lastName}`,
        createdBy: `${task.createdByDTO.firstName}${" "}${task.createdByDTO.lastName}`,
        created: formatDate(task.created)
    };
};

const renderItemColumn = (item: any, index?: number, column?: IColumn): React.ReactFragment => {
    const fieldContent = item[column!.fieldName as keyof ITaskDetailsListItem] as string;
    
    return (
        <React.Fragment>
      {column!.fieldName !== "created"
        ? <span className={itemStyle}>{fieldContent}</span>
        : <span className={itemStyleForLastColumn}>{fieldContent}</span>
      }
    </React.Fragment>
    );
};

const getTaskForCurrentUserStory = (allTasks: Task[]): ITaskDetailsListItem[] => {
    return allTasks.map((item) => getListItemFromTask(item) );
};

const getMenuItem = (name: string): IContextualMenuItem => {
    return {
        key: name,
        text: name,
        iconProps: { iconName: name }
    }
  };
  
  const getMenuItems = (names: string[]): IContextualMenuItem[] => {
    return names.map((name: string) => getMenuItem(name));
  };

const Tasks = (props: ITaskProps): JSX.Element => {
    const { isAuthenticated } = useAuth();
    const navigate = useNavigate();
    const [currentSprint, setCurrentSprint] = useState<Sprint>(
        getDefaultSprint()
      );
    const [deleteItemId, setDeleteItemId] = useState<number>(0);
    const [items, setItems] = useState<ITaskDetailsListItem[]>([]);
    const [tasks, setTasks] = useState<Task[]>([]);
    const [selectedItems, setSelectedItems] = useState<IObjectWithKey[] | undefined>(undefined);
    const [selection] = useState<Selection>(() => new Selection({
        onSelectionChanged: () => {
            const selectedItems: IObjectWithKey[] = selection.getSelection();
            const selected: ITaskDetailsListItem = selectedItems[0] as ITaskDetailsListItem
            setSelectedTask(selected)
            setSelectedItems(selectedItems);
        }
    }));
    const columns: IColumn[] = getColumns(props.pageWidth, [TITLE_COLUMN, DESCRIPTION_COLUMN, ASSIGNED_TO_COLUMN, CREATED_BY_COLUMN, CREATED_COLUMN]);
    const menuItems: IContextualMenuItem[] = getMenuItems([ADD, EDIT, DELETE]);

    useEffect(() => {
        if (!isAuthenticated) {
            navigate("/login");
        }
    }, [isAuthenticated]);

    useEffect(() => {
        getAllTasksForCurrentUserStory();
    }, [tasks]);

    const [isSaving, setIsSaving] = useState(false);
    const switchSavingMode = useCallback(
        () => setIsSaving((isSaving) => !isSaving),
        []
    );

    const [isEditing, setIsEditing] = useState(false);
    const switchEditingMode = useCallback(
        () => setIsEditing((isEditing) => !isEditing),
        []
    );
    useEffect(() => {
        if(deleteItemId === 0) {
          return;
        }
    
        deleteTask();
      }, [deleteItemId]);

    const getAllTasksForCurrentUserStory = async () => {
        const allTasks: Task[] = await getByRequestUrl(`${TasksService.GET_ALL_BY_USER_STORY_ID}${selectedUserStory.id}`);
        setItems(getTaskForCurrentUserStory(allTasks));
        setTasks(allTasks)
    };

    useEffect(() => {
        getCurrentSprint();
    }, []);

    const getCurrentSprint = async () => {
        const sprint: Sprint = await getByRequestUrl(
          SprintsService.GET_CURRENT_SPRINT
        );
        
        setCurrentSprint(sprint);
    };
      
    const getSelectedTask = (): Task => {
        const index = tasks.findIndex((it) => it.id === selectedTask.id);
        return tasks[index];
    };
    
    const deleteTask = async () => {
        const task: ITaskDetailsListItem = getSelectedItem() as ITaskDetailsListItem;
        const requestUrl: string = `${TasksService.DELETE_BY_ID}${task.id}`;
        const message: string = await getByRequestUrl(requestUrl);
    
        if(message === "Success") {
            getAllTasksForCurrentUserStory();
        }
        else {
          alert("An error has occurred on delete operation");
        }
    };

    const getSelectedItem = (): IObjectWithKey => {
        return selectedItems![0];
    };

    const getTitle = (): string => {
        return `${"Selected user story: "}${selectedUserStory.title}`;
    };

    const isEditOrDeleteDisabled = (checkEdit: boolean): boolean => {
        if (!selectedItems)
            return true;
    
        if (checkEdit) {
            if (selectedItems.length !== 1)
                return true;
        }
        else
            if (selectedItems.length < 1)
                return true;
        return false;
    };

    const onAddClicked = (): void => {
        switchSavingMode();
    };

    const onEditClicked = (): void => {
        if (tasks.find((us) => us.id === selectedTask.id) !== undefined)
            switchEditingMode();
    };

    const onDeleteClicked = (): void => {
        const deleteTask: ITaskDetailsListItem = getSelectedItem() as ITaskDetailsListItem;
        setDeleteItemId(deleteTask.id);
    };

    const updateMenuItems = (): IContextualMenuItem[] => {
        return menuItems.map((item: IContextualMenuItem) => {
            switch (item.key) {
                case ADD:
                    item.onClick = () => onAddClicked();
                    item.style = enabledMenuItemStyle;
                    break;
                case EDIT:
                    item.disabled = isEditOrDeleteDisabled(true);
                    item.onClick = () => onEditClicked();
                    item.style = selectedItems?.length === 1 ? enabledMenuItemStyle : defaultMenuItemStyle;
                    break;
                case DELETE:
                    item.disabled = isEditOrDeleteDisabled(false);
                    item.onClick = () => onDeleteClicked();
                    item.style = selectedItems?.length === 1 ? enabledMenuItemStyle : defaultMenuItemStyle;
                    break;
                default: return item;
            }
            return item;
        });
    };

    return (
        <div>
            {isSaving && (
            <SaveTaskModal
                switchMode={switchSavingMode}
                items={items}
                sprint={currentSprint}
                setItems={setItems}
            />
            )}
            
            {isEditing && (
            <EditTaskModal
                sprint={currentSprint}
                switchMode={switchEditingMode}
                task={getSelectedTask()}
                items={items}
                setItems={setItems}
                tasks={tasks}
                setTasks={setTasks}
            />
            )}
        <Stack className="hero is-fullheight has-background-dark" tokens={setGapBetweenHeadersAndDetailsList}>
            <Stack horizontal tokens={setGapBetweenHeadersAndUserInfo}>
                <StackItem tokens={setGapBetweenHeaders}>
                    <p className="title has-text-white is-size-5 has-text-left marginFH1"> {getTitle()} </p>
                    <p className="subtitle has-text-white is-size-3 marginFH2"> {BACKLOG_TITLE} </p>
                </StackItem>
                <StackItem>
                    <p style={setStyleForUserName}>{`${currentUser.firstName} ${currentUser.lastName}`}</p>
                    <p style={setStyleForUserRole}>{currentUser.roleTitle}</p>
                </StackItem>
            </Stack>
            <StackItem>
                <ThemeProvider theme={transparentTheme}>
                    <CommandBar items={updateMenuItems()} styles={commandBarStyles} />
                    <DetailsList className="hero is-fullheight has-background-dark"
                        items={items}
                        setKey="set"
                        columns={columns}
                        selectionMode={SelectionMode.single}
                        layoutMode={DetailsListLayoutMode.justified}
                        selection={selection}
                        selectionPreservedOnEmptyClick={true}
                        onRenderItemColumn={renderItemColumn}>
                    </DetailsList>
                </ThemeProvider>
            </StackItem>
        </Stack>
        </div>  
    );      
};

export default Tasks;
