/**
 * Created by tsengkasing on 12/17/2016.
 */
import React from 'react';
import {LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend} from 'recharts';

const styles = {
    Title : {
        textAlign : 'center',
    },
    Container : {
        overflowX: 'visible',
    },
};

const data = [
    {name: '1', quantity: 1398},
    {name: '2', quantity: 2400},
    {name: '3', quantity: 3908},
    {name: '4', quantity: 4256},
    {name: '5', quantity: 4800},
    {name: '6', quantity: 5890},
    {name: '7', quantity: 6793},
    {name: '8', quantity: 8453},
    {name: '9', quantity: 8890},
    {name: '10', quantity: 9210},
    {name: '11', quantity: 10041},
    {name: '12', quantity: 11211},
];

class NewUsersQuantity extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data : [],
        };
    }

    componentWillMount() {
        this.setState({
            data : data
        });
    }

    render() {
        return (
            <div style={styles.Container}>
                <h1 style={styles.Title}>新增用户量</h1>
                <LineChart width={1024} height={512} data={this.state.data}
                           margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                    <XAxis label="时间/月" dataKey="name"/>
                    <YAxis label="用户数量/个"/>
                    <CartesianGrid strokeDasharray="3 3"/>
                    <Tooltip/>
                    <Legend />
                    <Line type="monotone" dataKey="quantity" stroke="#8884d8" activeDot={{r: 8}}/>
                    {/*<Line type="monotone" dataKey="uv" stroke="#82ca9d" />*/}
                </LineChart>
            </div>
        );
    }
}

export default NewUsersQuantity;