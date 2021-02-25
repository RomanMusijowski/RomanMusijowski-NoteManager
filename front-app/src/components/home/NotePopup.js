import React, {Fragment, useState} from 'react'
import MyButton from "../../services/util/MyButton";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Dialog from "@material-ui/core/Dialog";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Moment from "react-moment";

const useStyles = makeStyles({
    root: {
        minWidth: 375,
    },
    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
    },
    pos: {
        marginBottom: 12,
    },
});

const NotePopup = ({note}) => {

    const classes = useStyles();
    const [open, setOpen] = useState(false);

    const handleOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };

    return (
        <Fragment>
            <MyButton
                tip="Edit Details"
                onClick={handleOpen}
                btnClassName={classes.button}
            >
                <p><strong>{note.title}</strong></p>
            </MyButton>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="confirm-dialog"
            >
                <DialogTitle id="confirm-dialog">Show note</DialogTitle>
                <DialogContent>
                    <table className="table table-striped table-hover">
                        <tbody>
                            <tr>
                                <td>title</td>
                                <td><strong>{note.title}</strong></td>
                            </tr>
                            <tr>
                                <td>content</td>
                                <td><strong>{note.content}</strong></td>
                            </tr>
                            <tr>
                                <td>created</td>
                                <td><strong>
                                    <Moment format="DD-MMMM-yyyy HH:mm:ss">
                                        {note.created}
                                    </Moment>

                                </strong></td>
                            </tr>
                            <tr>
                                <td>modified</td>
                                <td><strong>
                                    <Moment format="DD-MMMM-yyyy HH:mm:ss">
                                        {note.modified}
                                    </Moment>
                                </strong></td>
                            </tr>
                        </tbody>
                    </table>
                </DialogContent>
            </Dialog>
        </Fragment>
    )
}

export default NotePopup

