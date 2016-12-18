/**
 * Created by kevin on 12/10/2016.
 */
import React from 'react';
import SimpleDialog from './Dialog';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import {hashHistory } from 'react-router'
import $ from 'jquery';
import Auth from './Auth'
import API from './API'


const styles = {
    headline: {
        fontSize: 24,
        paddingTop: 16,
        marginBottom: 12,
        fontWeight: 400,
    },
    slide: {
        marginTop : '8%',
        padding: 10,
        verticalAlign: 'middle',
    },
};

class Sign extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            phone_number : null,
            password : null,

            error_phone_number : null,
            error_password : null
        };
    }

    checkUsername = (event) => {

    };

    inputUsername = (event) => {
        this.setState({
            phone_number : event.target.value,
            error_phone_number : null
        });
    };

    inputPassword = (event) => {
        this.setState({
            password : event.target.value,
            error_password : null
        });
    };

    redirect(type) {
        if(type === 'admin')
            hashHistory.push('/Admin');
        else
            hashHistory.push('/Home');
    };

    // 登录
    onSignIn = () => {

        /*
         * 为了测试而存在
         */
        if(this.state.phone_number === '10086'){

            if(this.state.password !== '10086') {
                this.setState({error_password : '密码错误！'});
                return;
            }

            this.refs.dialog.setContent('登录成功!', '点击确定后跳转到主页。', 'user');
            this.refs.dialog.handleOpen();
            //登录信息保存到本地
            window.localStorage.setItem('net', this.state.phone_number);
            Auth.phone_number = this.state.phone_number;
            Auth.admin = null;
            return;
        }else if(this.state.phone_number === 'admin') {
            if(this.state.password !== 'admin') {
                this.setState({error_password : '密码错误！'});
                return;
            }

            this.refs.dialog.setContent('登录成功!', '点击确定后跳转到主页。', 'admin');
            this.refs.dialog.handleOpen();
            //登录信息保存到本地
            window.localStorage.setItem('net', this.state.phone_number);
            Auth.admin = '管理员';
            Auth.Home = null;
            return;
        }




        const URL = API.SignIn;
        let callback = 'c'+Math.floor((Math.random()*100000000)+1);
        $.ajax({
            url : URL,
            type : 'POST',
            jsonpCallback: callback, //specify callback name
            contentType: 'application/json',
            dataType: 'jsonp', //specify jsonp
            data : {
                phone_number : this.state.phone_number,
                passwd : this.state.password
            },
            success : function(data, textStatus, jqXHR) {
                if(data.result){
                    this.refs.dialog.setContent('登录成功!', '点击确定后跳转到主页。');
                    this.refs.dialog.handleOpen();
                    //登录信息保存到本地
                    window.localStorage.setItem('net', this.state.phone_number);
                    Auth.username = this.state.phone_number;
                }
                else this.setState({error_password: 'password not matched'});
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    render() {
        return (
            <div style={styles.slide} className="alignCenter">
                    <h1>模拟电信</h1>
                    <p>查询你的电话信息</p>
                    <TextField hintText="电话号码"
                               floatingLabelText="电话号码"
                               type="text"
                               errorText={this.state.error_phone_number}
                               onBlur={this.checkUsername}
                               onChange={this.inputUsername}/>
                    <br/>
                    <TextField hintText="密码"
                               floatingLabelText="密码"
                               type="password"
                               errorText={this.state.error_password}
                               onChange={this.inputPassword}/>
                    <br/><br/>
                    <RaisedButton label="登录"
                                  primary={true}
                                  className="inputForm"
                                  onClick={this.onSignIn}/>
                    <SimpleDialog ref="dialog" onPress={this.redirect}/>
        </div>);
    }
}

export default Sign;