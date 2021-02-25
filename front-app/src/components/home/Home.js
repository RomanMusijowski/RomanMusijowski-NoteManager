import React from 'react';
import NoteTable from "./NoteTable";
import Container from "@material-ui/core/Container";


const Home = () => {

    return (
        <Container maxWidth="lg">
            <NoteTable/>
        </Container>
    )
}

export default Home