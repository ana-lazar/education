import React from  'react';
import RacesTable from "./RacesTable";
import { getRaces, addRace, deleteRace, updateRace } from './utils/rest-calls';
import './RaceApp.css';
import RaceForm from './RaceForm';

class RaceApp extends React.Component{
    constructor(props){
        super(props);
        this.state={
            races: [{"id": "1", "capacity": "125"}],
            addFunc: this.addFunc.bind(this),
            editFunc: this.editFunc.bind(this),
            deleteFunc: this.deleteFunc.bind(this)
        }
        console.log('RaceApp:constructor')
    }

    addFunc(race){
        console.log('RaceApp:addFunc ' + race);
        addRace(race)
            .then(res => getRaces())
            .then(races => this.setState({races}))
            .catch(error => {
                console.log('Error ', error);
                alert(error);
            });
    }

    editFunc(race){
        console.log('RaceApp:editFunc ' + race);
        updateRace(race)
            .then(res => getRaces())
            .then(races => this.setState({races}))
            .catch(error => {
                console.log('Error ', error);
                alert(error);
            });
    }

    deleteFunc(user){
        console.log('RaceApp:deleteFunc ' + user);
        deleteRace(user)
            .then(res => getRaces())
            .then(races => this.setState({races}))
            .catch(error => {
                console.log('Error ', error);
                alert(error);
            });
    }

    componentDidMount(){
        console.log('RaceApp:componentDidMount')
        getRaces().then(races => this.setState({races}));
    }

    render(){
        return(
            <div className="RaceApp">
                <p id="title">Motorcycle Races</p>
                <RaceForm addFunc={this.state.addFunc} editFunc={this.state.editFunc} />
                <RacesTable races={this.state.races} deleteFunc={this.state.deleteFunc} />
            </div>
        );
    }
}

export default RaceApp;