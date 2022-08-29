import React from  'react';
import './RaceApp.css';

class RaceForm extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            id: '', 
            capacity:''
        };
    }

    handleIdChange = (event) => {
        this.setState({id: event.target.value});
    }

    handleCapacityChange = (event) =>{
        this.setState({capacity: event.target.value});
    }

    handleSubmit = (event) => {
        var race = {
            id:this.state.id,
            capacity:this.state.capacity
        }
        console.log("Race submitted: ", race);
        this.props.addFunc(race);
        event.preventDefault();
    }

    handleEdit = (event) => {
        var race = {
            id:this.state.id,
            capacity:this.state.capacity
        }
        console.log("Race updated: ", race);
        this.props.editFunc(race);
    }

    render() {
        return (<div>
            <form onSubmit={this.handleSubmit}>
            <label>
                Save/Edit Race
            </label><br/>
            <label>
                Id:
                <input type="text" value={this.state.id} onChange={this.handleIdChange} />
            </label><br/>
            <label>
                Capacity:
                <input type="text" value={this.state.capacity} onChange={this.handleCapacityChange} />
            </label><br/>

            <input class="btn" type="submit" value="Save" />
        </form>
            <button class="btn" onClick={this.handleEdit}>Edit</button>
        </div>);
    }
}

export default RaceForm;