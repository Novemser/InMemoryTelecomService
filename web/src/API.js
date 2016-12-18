/**
 * Created by kevin on 12/10/2016.
 */
import React from 'react'

let URL = 'http://192.168.31.230:8081';

//后端API
class API extends React.Component{

    static SignIn = URL + '/signin';


    static render() {
        return null;
    }
}

export default API;