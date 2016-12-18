/**
 * Created by tsengkasing on 12/17/2016.
 */
import React from 'react';
import {BarChart, Bar, Brush, ReferenceLine, XAxis, YAxis, CartesianGrid, Tooltip, Legend} from 'recharts';

const styles = {
    Title : {
        textAlign:'center',
    },
    Graph : {
        textAlign:'center',
    },
    Container : {
    },
};

const data = [
    {name: '2014-1', Amount: 300},
    {name: '2014-2', Amount: 145},
    {name: '2014-3', Amount: 100},
    {name: '2014-4', Amount: 8},
    {name: '2014-5', Amount: 100},
    {name: '2014-6', Amount: 9},
    {name: '2014-7', Amount: 53},
    {name: '2014-8', Amount: 252},
    {name: '2014-9', Amount: 79},
    {name: '2014-10', Amount: 294},
    {name: '2014-12', Amount: 43},
    {name: '2015-1', Amount: 74},
    {name: '2015-2', Amount: 89},
    {name: '2015-3', Amount: 117},
    {name: '2015-4', Amount: 186},
    {name: '2015-5', Amount: 16},
    {name: '2015-6', Amount: 125},
    {name: '2015-7', Amount: 222},
    {name: '2015-8', Amount: 372},
    {name: '2015-9', Amount: 182},
    {name: '2015-10', Amount: 164},
    {name: '2015-11', Amount: 316},
    {name: '2015-12', Amount: 131},
    {name: '2016-1', Amount: 291},
    {name: '2016-2', Amount: 47},
    {name: '2016-3', Amount: 415},
    {name: '2016-4', Amount: 182},
    {name: '2016-5', Amount: 93},
    {name: '2016-6', Amount: 99},
    {name: '2016-7', Amount: 52},
    {name: '2016-8', Amount: 154},
    {name: '2016-9', Amount: 205},
    {name: '2016-10', Amount: 70},
    {name: '2016-11', Amount: 25},
    {name: '2016-12', Amount: 59},
];

class CallTotalAmount extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data : [],
        }
    }

    componentWillMount() {
        this.setState({
            data : data,
        });
    }



    render() {
        return (
            <div style={styles.Container}>
                <h3 style={styles.Title}>通话总额</h3>
                <BarChart width={1024}
                          height={512}
                          style={styles.Graph}
                          data={this.state.data}
                          margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                    <XAxis label="时间/月" dataKey="name"/>
                    <YAxis label="通话总额/千万元"/>
                    <CartesianGrid strokeDasharray="3 3"/>
                    <Tooltip/>
                    <Legend verticalAlign="top" wrapperStyle={{lineHeight: '40px'}}/>
                    <ReferenceLine y={0} stroke='#000'/>
                    <Brush dataKey='name' height={30} stroke="#8884d8"/>
                    <Bar dataKey="Amount" fill="#8884d8" />
                </BarChart>
            </div>
        );
    }
}


export default CallTotalAmount;

