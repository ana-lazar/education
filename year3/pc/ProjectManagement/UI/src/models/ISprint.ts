import { Epic } from "./IEpic";

export interface Sprint {
    id: number;
    title: string;
    startDate: Date;
    endDate: Date;
    epicDTO: Epic;
}