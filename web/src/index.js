import React from 'react';
import ReactDOM from 'react-dom';

import injectTapEventPlugin from 'react-tap-event-plugin';
import { Router, Route, IndexRoute, hashHistory } from 'react-router'
import App from './App';
import Auth from './Auth'
import Sign from './Sign'
import Home from './Home'
import RechargeRecord from './RechargeRecord'
import Record from './Record'
import Admin from './Admin'
import CallTotalTime from './CallTotalTime'
import CallTotalAmount from './CallTotalAmount'
import CallDurationDistribution from './CallDurationDistribution'
import NewUsersQuantity from './NewUsersQuantity'
import './index.css';

injectTapEventPlugin();
//
// ReactDOM.render(
//     <MuiThemeProvider>
//     <Sign />
//     </MuiThemeProvider>,
//   document.getElementById('root')
// );

ReactDOM.render((
    <Router history={hashHistory}>
        <Route path="/" component={App}>
            <IndexRoute component={Sign}/>
            <Route path="/Sign" component={Sign}/>
            <Route path="/Home" component={Home}
                   onEnter={()=>{if(Auth.phone_number == null) hashHistory.push('/Sign');}}>
                <Route path="/Home/Recharge" component={RechargeRecord}/>
                <Route path="/Home/Record" component={Record}/>
            </Route>
            <Route path="/Admin" component={Admin}
                   onEnter={()=>{if(Auth.admin == null) hashHistory.push('/Sign');}}>
                <Route path="/Admin/CallTotalTime" component={CallTotalTime}/>
                <Route path="/Admin/CallTotalAmount" component={CallTotalAmount}/>
                <Route path="/Admin/CallDurationDistribution" component={CallDurationDistribution}/>
                <Route path="/Admin/NewUsersQuantity" component={NewUsersQuantity}/>
            </Route>
        </Route>
    </Router>
), document.getElementById("root"));