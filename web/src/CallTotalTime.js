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
    {name: '2014-1', Time: 300},
    {name: '2014-2', Time: 145},
    {name: '2014-3', Time: 100},
    {name: '2014-4', Time: 8},
    {name: '2014-5', Time: 100},
    {name: '2014-6', Time: 9},
    {name: '2014-7', Time: 53},
    {name: '2014-8', Time: 252},
    {name: '2014-9', Time: 79},
    {name: '2014-10', Time: 294},
    {name: '2014-12', Time: 43},
    {name: '2015-1', Time: 74},
    {name: '2015-2', Time: 89},
    {name: '2015-3', Time: 117},
    {name: '2015-4', Time: 186},
    {name: '2015-5', Time: 16},
    {name: '2015-6', Time: 125},
    {name: '2015-7', Time: 222},
    {name: '2015-8', Time: 372},
    {name: '2015-9', Time: 182},
    {name: '2015-10', Time: 164},
    {name: '2015-11', Time: 316},
    {name: '2015-12', Time: 131},
    {name: '2016-1', Time: 291},
    {name: '2016-2', Time: 47},
    {name: '2016-3', Time: 415},
    {name: '2016-4', Time: 182},
    {name: '2016-5', Time: 93},
    {name: '2016-6', Time: 99},
    {name: '2016-7', Time: 52},
    {name: '2016-8', Time: 154},
    {name: '2016-9', Time: 205},
    {name: '2016-10', Time: 70},
    {name: '2016-11', Time: 25},
    {name: '2016-12', Time: 59},
];

class CallTotalTime extends React.Component {

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
        return(
            <div style={styles.Container}>
                <h3 style={styles.Title}>通话总量</h3>
                <BarChart width={1024}
                          height={512}
                          style={styles.Graph}
                          data={this.state.data}
                          margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                    <XAxis label="时间/月" dataKey="name"/>
                    <YAxis label="通话总时长/分钟"/>
                    <CartesianGrid strokeDasharray="3 3"/>
                    <Tooltip/>
                    <Legend verticalAlign="top" wrapperStyle={{lineHeight: '40px'}}/>
                    <ReferenceLine y={0} stroke='#000'/>
                    <Brush dataKey='name' height={30} stroke="#82ca9d"/>
                    <Bar dataKey="Time" fill="#82ca9d" />
                </BarChart>
            </div>
        );
    }
}

export default CallTotalTime;