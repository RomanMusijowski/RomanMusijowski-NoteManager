import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import MaterialTable from "material-table";
import {fetchListOfTasks} from "../../services/services/noteService";
import UpdateIcon from '@material-ui/icons/Update';
import Moment from "react-moment";
import NotePopup from "./NotePopup";


const NoteTable = () => {

    const dispatch = useDispatch()
    const notes = useSelector((state) => state.note.notes);

    useEffect(async () => {
        if (notes === undefined || notes.size === 0) {
            dispatch(fetchListOfTasks())
        }
    })


    const [state, setState] = useState({
        columns: [
            {
                title: <th>Title</th>,
                field: 'title',
                render: rowData =>
                    <td>
                        <div>
                            <NotePopup note={rowData}/>
                        </div>
                    </td>
            },
            {
                title: 'Created',
                field: 'created',
                render: rowData =>
                    <td>
                        <div>
                            <Moment format="DD-MMMM-yyyy HH:mm:ss">
                                {rowData.created}
                            </Moment>
                        </div>
                    </td>
            },
            {
                title: 'Modified',
                field: 'modified',
                render: rowData =>
                    <td>
                        <div>
                            <Moment format="DD-MMMM-yyyy HH:mm:ss">
                                {rowData.modified}
                            </Moment>
                        </div>
                    </td>
            },
        ],
        data: notes
    })

    const updateList = () => {
        dispatch(fetchListOfTasks())
    }

    return(
        <div>
            {notes !== undefined ? (
                <MaterialTable
                    columns={state.columns}
                    data={notes}
                    title="Notes list"
                    actions={[
                        {
                            icon: () =>
                                <UpdateIcon onClick={updateList}/>,
                            isFreeAction: true,
                        },
                    ]}
                />
            ) : (
                <p>Please wait a little bit longer.</p>
            )}
        </div>
    )
}

export default NoteTable