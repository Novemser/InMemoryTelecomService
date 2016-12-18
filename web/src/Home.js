/**
 * Created by kevin on 12/11/2016.
 */
import React from 'react';
import {Card, CardHeader} from 'material-ui/Card';
import {hashHistory } from 'react-router'
import RaisedButton from 'material-ui/RaisedButton';
import {List, ListItem} from 'material-ui/List';
import FlatButton from 'material-ui/FlatButton';
import Dialog from 'material-ui/Dialog';
import {RadioButton, RadioButtonGroup} from 'material-ui/RadioButton'
import Auth from './Auth'

const styles = {
    button : {
        margin : 12,
    },
    radioButton: {
        marginTop: 16,
    },
};

const radios = [
    {
        key: '1',
        value: '10',
        label: '10元',
    },
    {
        key: '2',
        value: '20',
        label: '20元',
    },
    {
        key: '3',
        value: '30',
        label: '30元',
    },
    {
        key: '4',
        value: '50',
        label: '50元',
    },
    {
        key: '5',
        value: '100',
        label: '100元',
    },
    {
        key: '6',
        value: '200',
        label: '200元',
    },
    {
        key: '7',
        value: '300',
        label: '300元',
    },
    {
        key: '8',
        value: '500',
        label: '500元',
    },
    {
        key: '9',
        value: '1000000',
        label: '1,000,000元 [测试用]',
    },
];

class Home extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            open: false,
            money : '200.00',
            plan : '预付费本地接听免费套餐',
            recharge : '10',

            disableRechargeButton : false,
            disableCallingButton : false,
        };
    };

    componentWillMount() {
        // if(window.location.hash !== '/Home')
        //     hashHistory.push('/Home');
    };

    handleOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({
            open: false,
            money: (parseFloat(this.state.money) + parseFloat(this.state.recharge)).toFixed(2),
            disableRechargeButton : false,
            disableCallingButton  : false
        });
        hashHistory.push('/Home');
    };

    handleChange = (event) => {
        this.setState({recharge:event.target.value});
    };

    handleSearchRecharge = (event) => {
        this.setState({
            disableRechargeButton : true,
            disableCallingButton  : false
        });
        hashHistory.push('/Home/Recharge');
    };

    handleSearchCalling = (event) => {
        this.setState({
            disableCallingButton  : true,
            disableRechargeButton :false
        });
        hashHistory.push('/Home/Record');
    };

    render() {
        const actions = [
            <FlatButton
                label="充值"
                primary={true}
                keyboardFocused={true}
                onTouchTap={this.handleClose}
            />,
        ];



        return (
            <div style={{maxWidth: '1024px', margin:'0 auto'}}>
                <Card containerStyle={{margin: 16}} style={{background : 'transparent'}}>
                    <CardHeader
                        title={Auth.phone_number}
                        avatar="dynamic/img/avator.jpg"
                        actAsExpander={true}
                    />
                    <List>
                        <ListItem primaryText={'余额 : ' + this.state.money + '元'} rightIcon={<RaisedButton label="充值" primary={true} style={styles.button} onTouchTap={this.handleOpen} />} />
                        <ListItem primaryText={'套餐 : ' + this.state.plan} />
                    </List>
                    <RaisedButton label="查询充值记录" primary={true} style={styles.button} onTouchTap={this.handleSearchRecharge} disabled={this.state.disableRechargeButton} />
                    <RaisedButton label="查询流水" primary={true} style={styles.button} onTouchTap={this.handleSearchCalling} disabled={this.state.disableCallingButton} />
                </Card>
                <Dialog
                    title="选择充值金额"
                    actions={actions}
                    modal={false}
                    open={this.state.open}
                    onRequestClose={this.handleClose}
                    autoScrollBodyContent={true}
                >
                    <RadioButtonGroup name="feeRecharge" defaultSelected="1" valueSelected={this.state.recharge} onChange={this.handleChange}>
                        {radios.map((radio)=> (
                            <RadioButton
                                key={radio.key}
                                value={radio.value}
                                label={radio.label}
                                style={styles.radioButton}
                            />
                        ))}
                    </RadioButtonGroup>
                </Dialog>
                {this.props.children}
            </div>
        );
    }
}

export default Home;