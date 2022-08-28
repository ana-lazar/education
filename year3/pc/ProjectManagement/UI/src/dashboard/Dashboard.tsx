import { CommandBar, DetailsList, IColumn, IContextualMenuItem, Stack, StackItem, ThemeProvider } from "@fluentui/react";
import { DetailsListLayoutMode, IObjectWithKey, Selection, SelectionMode } from "@fluentui/react/lib/DetailsList";
import React, { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthProvider";
import { IDashboardProps } from "../models/IDashboardProps";
import { Sprint } from "../models/ISprint";
import { UserStory } from "../models/IUserStory";
import { UserStoryDetailsListItem } from "../models/IUserStoryDetailsListItem";
import { ADD, DELETE, EDIT, VIEW_TASKS } from "../utils/generalConstants";
import { SprintsService, UserStoriesService } from "../utils/service";
import { getDefaultSprint, formatDate, getViewportAsPixels, getByRequestUrl, setSelectedUserStory, selectedUserStory, currentUser } from "../utils/utilsMethods";
import { commandBarStyles, defaultMenuItemStyle, detailsListColumnStyle, itemStyle, enabledMenuItemStyle, setGapBetweenHeaders, setGapBetweenHeadersAndDetailsList, transparentTheme, itemStyleForLastColumn, setGapBetweenHeadersAndUserInfo, setStyleForUserRole, setStyleForUserName } from "./Dashboard.styles";
import LoginFoot from "../images/foot.svg";
import EditUserStoryModal from "./userStory/EditUserStoryModal";
import SaveUserStoryModal from "./userStory/SaveUserStoryModal";

const TITLE_COLUMN: string = "Title";
const DESCRIPTION_COLUMN: string = "Description";
const ASSIGNED_TO_COLUMN: string = "Assigned to";
const CREATED_BY_COLUMN: string = "Created by";
const STATUS_COLUMN: string = "Status";
const CREATED_COLUMN: string = "Created at";
const BACKLOG_TITLE: string = "Backlog";

const getColumnName = (title: string, description: string, assignedTo: string, createdBy: string, status: string, created: string, name: string): string => {
  return name === title
    ? title
    : name === description
    ? description
    : name === assignedTo
    ? assignedTo
    : name === createdBy
    ? createdBy
    : name === status
    ? status
    : name === created
    ? created
    : name;
};

const getFieldName = (columnName: string): string => {
  return columnName === TITLE_COLUMN
    ? "title"
    : columnName === DESCRIPTION_COLUMN
    ? "description"
    : columnName === ASSIGNED_TO_COLUMN
    ? "assignedTo"
    : columnName === CREATED_BY_COLUMN
    ? "createdBy"
    : columnName === STATUS_COLUMN
    ? "status"
    : columnName === CREATED_COLUMN
    ? "created"
    : "";
};

const getColumn = (pageWidth: number, name: string): IColumn => {
  return {
    key: name,
    name: getColumnName(TITLE_COLUMN,DESCRIPTION_COLUMN,ASSIGNED_TO_COLUMN,CREATED_BY_COLUMN,STATUS_COLUMN,CREATED_COLUMN,name),
    fieldName: getFieldName(name),
    minWidth: getViewportAsPixels(pageWidth, 10),
    maxWidth: getViewportAsPixels(pageWidth, 20),
    isResizable: true,
    isMultiline: true,
    styles: detailsListColumnStyle,
  };
};

const getColumns = (pageWidth: number, names: string[]): IColumn[] => {
  return names.map((name: string) => getColumn(pageWidth, name));
};

export const getListItemFromUserStory = (userStory: UserStory): UserStoryDetailsListItem => {
  return {
    id: userStory.id,
    title: userStory.title,
    description: userStory.description,
    assignedTo: `${userStory.assignedTo.firstName}${" "}${userStory.assignedTo.lastName}`,
    createdBy: `${userStory.createdBy.firstName}${" "}${userStory.createdBy.lastName}`,
    status: userStory.status,
    created: formatDate(userStory.created)
  };
};

const renderItemColumn = (item: any, index?: number, column?: IColumn): React.ReactFragment => {
  const fieldContent = item[column!.fieldName as keyof UserStoryDetailsListItem] as string;

  return (
    <React.Fragment>
      {column!.fieldName !== "created"
        ? <span className={itemStyle}>{fieldContent}</span>
        : <span className={itemStyleForLastColumn}>{fieldContent}</span>
      }
    </React.Fragment>
  );
};

const getMenuItem = (name: string): IContextualMenuItem => {
  return {
    key: name,
    text: name,
    iconProps: { iconName: name },
  };
};

const getMenuItems = (names: string[]): IContextualMenuItem[] => {
  return names.map((name: string) => getMenuItem(name));
};

const Dashboard = (props: IDashboardProps): JSX.Element => {
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [currentSprint, setCurrentSprint] = useState<Sprint>(getDefaultSprint());
  const [deleteItemId, setDeleteItemId] = useState<number>(0);
  const [items, setItems] = useState<UserStoryDetailsListItem[]>([]);
  const [userStories, setUserStories] = useState<UserStory[]>([]);
  const [selectedItems, setSelectedItems] = useState<IObjectWithKey[] | undefined>(undefined);
  const [selection] = useState<Selection>(
    () =>
      new Selection({
        onSelectionChanged: () => {
          const selectedItems: IObjectWithKey[] = selection.getSelection();
          const selected: UserStoryDetailsListItem = selectedItems[0] as UserStoryDetailsListItem;
          setSelectedUserStory(selected);
          setSelectedItems(selectedItems);
        },
      })
  );
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
  const columns: IColumn[] = getColumns(props.pageWidth, [TITLE_COLUMN, DESCRIPTION_COLUMN, ASSIGNED_TO_COLUMN, CREATED_BY_COLUMN, STATUS_COLUMN, CREATED_COLUMN]);
  const menuItems: IContextualMenuItem[] = currentUser.roleTitle !== "Team Member" 
                                              ? getMenuItems([VIEW_TASKS, ADD, EDIT, DELETE])
                                              : getMenuItems([VIEW_TASKS]);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
    }
  }, [isAuthenticated]);

  useEffect(() => {
    getCurrentSprint();
  }, []);

  useEffect(() => {
    if (deleteItemId === 0) {
      return;
    }

    deleteUserStory();
  }, [deleteItemId]);

  const getCurrentSprint = async () => {
    const sprint: Sprint = await getByRequestUrl(SprintsService.GET_CURRENT_SPRINT);
    getUserStories(sprint.id);

    setCurrentSprint(sprint);
  };

  const getUserStories = async (sprintId: number) => {
    const allUserStories = await getByRequestUrl(`${UserStoriesService.GET_ALL_BY_SPRINT_ID}${sprintId}`);
    setItems(getUserStoriesForCurrentSprint(allUserStories));
    setUserStories(allUserStories);
  };

  const deleteUserStory = async () => {
    const userStory: UserStoryDetailsListItem = getSelectedItem() as UserStoryDetailsListItem;
    const requestUrl: string = `${UserStoriesService.DELETE_BY_ID}${userStory.id}`;
    const message: string = await getByRequestUrl(requestUrl);

    if (message === "Success") {
      getUserStories(currentSprint.id);
    } else {
      alert("An error has occurred on delete operation");
    }
  };

  const getUserStoriesForCurrentSprint = (allUserStories: UserStory[]): UserStoryDetailsListItem[] => {
    return allUserStories.map((item) => getListItemFromUserStory(item));
  };

  const getTitle = (): string => {
    return `${"Projects"}${" / "}${currentSprint.epicDTO.projectDTO.title}`;
  };

  const getSubtitle = (): string => {
    return `${currentSprint.title}${" / "}${formatDate(currentSprint.startDate)}${" - "}${formatDate(currentSprint.endDate)}`;
  };

  const getSelectedItem = (): IObjectWithKey => {
    return selectedItems![0];
  };

  const getSelectedUserStory = (): UserStory => {
    const index = userStories.findIndex((it) => it.id === selectedUserStory.id);
    return userStories[index];
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

  const onEditClicked = (): void => {
    if (userStories.find((us) => us.id === selectedUserStory.id) !== undefined) {
      switchEditingMode();
    }
  };

  const onAddClicked = (): void => {
    switchSavingMode();
  };

  const onDeleteClicked = (): void => {
    const deleteUserStory: UserStoryDetailsListItem = getSelectedItem() as UserStoryDetailsListItem;
    setDeleteItemId(deleteUserStory.id);
  };

  const onViewClicked = (): void => {
    if (selectedItems !== undefined) {
      navigate("/tasks");
    }
  };

  const updateMenuItems = (): IContextualMenuItem[] => {
    return menuItems.map((item: IContextualMenuItem) => {
      switch (item.key) {
        case VIEW_TASKS:
          item.disabled = !(selectedItems?.length === 1);
          item.onClick = () => onViewClicked();
          item.style =
            selectedItems?.length === 1
              ? enabledMenuItemStyle
              : defaultMenuItemStyle;
          break;
        case ADD:
          item.onClick = () => onAddClicked();
          item.style = enabledMenuItemStyle;
          break;
        case EDIT:
          item.disabled = isEditOrDeleteDisabled(true);
          item.onClick = () => onEditClicked();
          item.style =
            selectedItems?.length === 1
              ? enabledMenuItemStyle
              : defaultMenuItemStyle;
          break;
        case DELETE:
          item.disabled = isEditOrDeleteDisabled(false);
          item.onClick = () => onDeleteClicked();
          item.style =
            selectedItems?.length === 1
              ? enabledMenuItemStyle
              : defaultMenuItemStyle;
          break;
        default:
          return item;
      }
      return item;
    });
  };

  return (
    <div>
      {isSaving && (
        <SaveUserStoryModal
          switchMode={switchSavingMode}
          sprint={currentSprint}
          items={items}
          setItems={setItems}
          userStories={userStories}
          setUserStories={setUserStories}
        />
      )}
      {isEditing && (
        <EditUserStoryModal
          switchMode={switchEditingMode}
          userStory={getSelectedUserStory()}
          items={items}
          setItems={setItems}
          userStories={userStories}
          setUserStories={setUserStories}
        />
      )}
      <Stack
        className="hero is-fullheight has-background-dark"
        tokens={setGapBetweenHeadersAndDetailsList}
      >
        <Stack horizontal tokens={setGapBetweenHeadersAndUserInfo}>
          <StackItem>
            <Stack tokens={setGapBetweenHeaders}>
            <p className="title has-text-white is-size-5 has-text-left marginFH1">
              {" "}
              {getTitle()}{" "}
            </p>
            <p className="title has-text-white is-size-5 has-text-left marginFH1">
              {" "}
              {getSubtitle()}{" "}
            </p>
            <p className="subtitle has-text-white is-size-3 marginFH2">
              {" "}
              {BACKLOG_TITLE}{" "}
            </p>
          </Stack>
          </StackItem>
          <StackItem>
            <p style={setStyleForUserName}>{`${currentUser.firstName} ${currentUser.lastName}`}</p>
            <p style={setStyleForUserRole}>{currentUser.roleTitle}</p>
          </StackItem>
        </Stack>
        <StackItem>
          <ThemeProvider theme={transparentTheme}>
            <CommandBar items={updateMenuItems()} styles={commandBarStyles} />
            <DetailsList
              className="hero is-fullheight has-background-dark"
              items={items}
              setKey="set"
              columns={columns}
              selectionMode={SelectionMode.single}
              layoutMode={DetailsListLayoutMode.justified}
              selection={selection}
              selectionPreservedOnEmptyClick={true}
              onRenderItemColumn={renderItemColumn}
            ></DetailsList>
          </ThemeProvider>
        </StackItem>
        <div className="hero-foot">
          <figure className="image is-fullwidth">
            <img src={LoginFoot} alt="LoginFoot" className="" />
          </figure>
        </div>
      </Stack>
    </div>
  );
};

export default Dashboard;
