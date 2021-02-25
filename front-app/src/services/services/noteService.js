import axios from "axios";
import {fetchListOfNotesFailure, fetchListOfNotesSuccess} from "../actions/noteActions";


export const fetchListOfTasks = () => async (dispatch) => {
    axios.get('http://localhost:8080/api/note')
        .then(res => {
            dispatch(fetchListOfNotesSuccess(res))
        })
        .catch((error) => {
            dispatch(fetchListOfNotesFailure(error))
        })
}