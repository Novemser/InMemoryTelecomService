/**
 * Created by tsengkasing on 12/17/2016.
 */
import React from 'react';
import {Card, CardHeader} from 'material-ui/Card';
import {hashHistory } from 'react-router'
import RaisedButton from 'material-ui/RaisedButton';
// import FlatButton from 'material-ui/FlatButton';
import Auth from './Auth'

const styles = {
    button : {
        margin : 12,
    },
    group :{
        textAlign : 'center',
    },
};

class Admin extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            button_disable_status : [false, false, false, false],
        }
    }


    componentWillMount() {
        if(window.location.hash !== '/Admin')
            hashHistory.push('/Admin');
    };

    disableButton = (index) => {
        let status = [false, false, false, false];
        status[index] = true;
        this.setState({
            button_disable_status : status,
        })
    };


    displayCallTotalTime = () => {
        this.disableButton(0);
        hashHistory.push('/Admin/CallTotalTime');
    };

    displayCallTotalAmount = () => {
        this.disableButton(1);
        hashHistory.push('/Admin/CallTotalAmount');
    };

    displayCallDurationDistribution = () => {
        this.disableButton(2);
        hashHistory.push('/Admin/CallDurationDistribution');
    };

    displayNewUsersQuantity = () => {
        this.disableButton(3);
        hashHistory.push('/Admin/NewUsersQuantity');
    };


    render() {
        return (
            <div style={{maxWidth: '1024px', margin:'0 auto', textAlign:'center'}}>
                <Card containerStyle={{margin: 16}} style={{background : 'transparent'}}>
                    <CardHeader
                        title={Auth.admin}
                        subtitle={'后台管理中心'}
                        avatar="dynamic/img/avator.jpg"
                        actAsExpander={true}
                    />
                    <div style={styles.group}>
                    <RaisedButton label="查询通话量" primary={true} style={styles.button}
                                  onTouchTap={this.displayCallTotalTime} disabled={this.state.button_disable_status[0]} />
                    <RaisedButton label="查询通话总额" primary={true} style={styles.button}
                                  onTouchTap={this.displayCallTotalAmount} disabled={this.state.button_disable_status[1]} />
                    <RaisedButton label="查询通话时长分布" primary={true} style={styles.button}
                                  onTouchTap={this.displayCallDurationDistribution} disabled={this.state.button_disable_status[2]} />
                    <RaisedButton label="查询新增用户量" primary={true} style={styles.button}
                                  onTouchTap={this.displayNewUsersQuantity} disabled={this.state.button_disable_status[3]} />
                    </div>
                </Card>
                {this.props.children}
            </div>
        );
    }
}

export default Admin;