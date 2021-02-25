import {noteConstants} from "../constants/noteConstants";


const noteReducer = (state = {}, action) => {
    switch (action.type) {
        case noteConstants.FETCH_NOTE_LIST_SUCCESS:
            return {
                ...state,
                notes: action.payload.data.content
            }
        default:
            return state
    }
}
export default noteReducer