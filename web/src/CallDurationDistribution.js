/**
 * Created by tsengkasing on 12/17/2016.
 */
import React from 'react';
import {ScatterChart, Scatter, XAxis, YAxis, CartesianGrid, Tooltip} from 'recharts';

const styles = {
    Title : {
        textAlign:'center',
    },
    Graph : {
        left : '-50%',
    },
    Container : {
        textAlign:'center',
        // overflowX: 'scroll',
    },
};

const data = [
    {x: 1, y: 1000},
    {x: 2, y: 100},
    {x: 3, y: 40},
    {x: 4, y: 600},
    {x: 5, y: 300},
    {x: 10, y: 300},
    {x: 20, y: 700},
    {x: 25, y: 230},
    {x: 30, y: 250},
    {x: 35, y: 560},
    {x: 40, y: 250},
    {x: 45, y: 400},
    {x: 60, y: 280},
    ];

class CallDurationDistribution extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data : [],
        };
    }

    componentWillMount() {
        this.setState({
            data : data,
        });
    }


    render() {
        return (
            <div style={styles.Container}>
                <h3 style={styles.Title}>通话时长分布</h3>
                <ScatterChart
                    style={styles.Graph}
                    width={1024}
                    height={512}
                    margin={{top: 20, right: 20, bottom: 20, left: 20}}>
                    <XAxis label="通话时长/分钟" dataKey={'x'} name='通话时长' unit='分钟'/>
                    <YAxis label="通话数量/通" dataKey={'y'} name='通话数量' unit='通'/>
                    <Scatter name='通话时长分布' data={this.state.data} fill='#8884d8'/>
                    <CartesianGrid />
                    <Tooltip cursor={{strokeDasharray: '3 3'}}/>
                </ScatterChart>
            </div>
        );
    }
}

export default CallDurationDistribution;