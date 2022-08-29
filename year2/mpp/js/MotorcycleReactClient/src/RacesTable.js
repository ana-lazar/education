import React from  'react';
import './RaceApp.css';

class RaceRow extends React.Component{
    handleClicc = (event) => {
        console.log('Delete button for ' + this.props.race.id);
        this.props.deleteFunc(this.props.race.id);
    }

    render() {
        return (
            <tr>
                <td>{this.props.race.id}</td>
                <td>{this.props.race.capacity}</td>
                <td><button class="btn" onClick={this.handleClicc}>Delete</button></td>
            </tr>
        );
    }
}

class RacesTable extends React.Component{
    render() {
        var rows = [];
        var deleteFunction = this.props.deleteFunc;
        this.props.races.forEach(function(race) {
            rows.push(<RaceRow race={race} key={race.id} deleteFunc={deleteFunction} />);
        });
        return (<div className="RaceTable">
                    <table className="center">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Capacity</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>{rows}</tbody>
                    </table>
                </div>
        );
    }
}

export default RacesTable;