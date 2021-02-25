import createAction from "./action";
import {noteConstants} from "../constants/noteConstants";

export const fetchListOfNotesSuccess = (data) => createAction(noteConstants.FETCH_NOTE_LIST_SUCCESS, data)
export const fetchListOfNotesFailure = (data) => createAction(noteConstants.FETCH_NOTE_LIST_FAILURE, data)