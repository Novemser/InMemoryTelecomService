/**
 * Created by kevin on 12/11/2016.
 */
import React from 'react';
import {Table, TableBody, TableFooter, TableHeader, TableHeaderColumn, TableRow, TableRowColumn}
    from 'material-ui/Table';
import FlatButton from 'material-ui/FlatButton';

const tableData_1 = [
    {
        money: '100元',
        time: '2016/12/27 12:56:00',
    },
    {
        money: '200元',
        time: '2016/11/05 11:23:00',
    },
    {
        money: '30元',
        time: '2016/10/15 14:34:00',
    },
    {
        money: '50元',
        time: '2016/09/05 12:45:00',
    },
    {
        money: '10元',
        time: '2016/08/21 21:33:00',
    },
    {
        money: '200元',
        time: '2016/07/03 22:34:00',
    },
    {
        money: '500元',
        time: '2016/05/05 10:09:00',
    },

];

const tableData_2 = [
    {
        money: '50元',
        time: '2016/04/12 08:32:00',
    },
    {
        money: '100元',
        time: '2016/03/08 17:34:00',
    },
    {
        money: '100元',
        time: '2016/02/23 15:40:00',
    },

];

export default class RechargeRecord extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            current_page : 1,
            all_pages : 2,

            record : [],

            disablePreviousButton : false,
            disableNextButton : false,
        };
    }

    componentWillMount() {
        let all_pages = 2;
        this.setState({
            current_page : 1,
            all_pages : all_pages,
            record : tableData_1,
            disablePreviousButton : true,
            disableNextButton : (all_pages <= 1),
        });
    };

    previousPage= () => {
        this.setState({
            current_page : parseInt(this.state.current_page, 10) - 1,
            record : tableData_1,
            disableNextButton: false,
            disablePreviousButton: ((parseInt(this.state.current_page, 10) - 1) === 1),
        });
    };

    nextPage = () => {
        this.setState({
            current_page : (parseInt(this.state.current_page, 10) + 1),
            record : tableData_2,
            disableNextButton: ((parseInt(this.state.current_page, 10) + 1) === parseInt(this.state.all_pages, 10)),
            disablePreviousButton: false,
        });
    };

    render() {
        return (
            <div>
                <Table
                    style={{background : 'transparent'}}
                    selectable={false}
                >
                    <TableHeader
                        displaySelectAll={false}
                        adjustForCheckbox={false}
                    >
                        <TableRow>
                            <TableHeaderColumn colSpan="2" tooltip="充值记录" style={{textAlign: 'center'}}>
                                充值记录
                            </TableHeaderColumn>
                        </TableRow>
                        <TableRow>
                            <TableHeaderColumn tooltip="充值时间">充值时间</TableHeaderColumn>
                            <TableHeaderColumn tooltip="充值金额" style={{textAlign: 'right'}}>充值金额</TableHeaderColumn>
                        </TableRow>
                    </TableHeader>
                    <TableBody
                        showRowHover={true}
                        displayRowCheckbox={false}
                    >
                        {this.state.record.map((row, index) => (
                            <TableRow key={index}>
                                <TableRowColumn>{row.time}</TableRowColumn>
                                <TableRowColumn style={{textAlign: 'right'}} >{row.money}</TableRowColumn>
                            </TableRow>
                        ))}
                    </TableBody>
                    <TableFooter
                        adjustForCheckbox={false}>
                        <TableRow>
                            <TableRowColumn colSpan="5" style={{textAlign: 'center'}}>
                                <FlatButton primary={true} label="上一页" onTouchTap={this.previousPage} disabled={this.state.disablePreviousButton} />
                                {this.state.current_page}/{this.state.all_pages}
                                <FlatButton primary={true} label="下一页" onTouchTap={this.nextPage} disabled={this.state.disableNextButton} />
                            </TableRowColumn>
                        </TableRow>
                    </TableFooter>
                </Table>
            </div>
        );
    }
}