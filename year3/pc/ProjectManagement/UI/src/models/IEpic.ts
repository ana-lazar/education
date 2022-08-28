import { Project } from "./IProject";

export interface Epic {
    id: number;
    title: string;
    created: Date;
    projectDTO: Project;
}