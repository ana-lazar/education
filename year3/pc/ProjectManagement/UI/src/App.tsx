import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./auth/AuthProvider";
import Login from "./auth/Login";
import Tasks from "./taskspage/Tasks";
import { IAppProps } from "./models/IAppProps";
import "./styles/bulma.css";
import "./styles/custom.css";
import Dashboard from "./dashboard/Dashboard";
import { UserStoryProvider } from "./dashboard/userStory/UserStoryProvider";
import { TaskProvider } from "./taskspage/TaskProvider";


const App = (props: IAppProps) => {
  return (
    <AuthProvider>
      <UserStoryProvider>
        <TaskProvider>
          <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/dashboard" element={<Dashboard pageHeight={props.pageHeight} pageWidth={props.pageWidth}  />} />
                <Route path="/tasks" element={<Tasks pageHeight={props.pageHeight} pageWidth={props.pageWidth} />} />
                <Route path="/" element={<Login/>}></Route>
            </Routes>
          </BrowserRouter>
        </TaskProvider>
      </UserStoryProvider>
    </AuthProvider>
  );
};

export default App;