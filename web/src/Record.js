/**
 * Created by kevin on 12/11/2016.
 */
import React from 'react';
import {Table, TableBody, TableFooter, TableHeader, TableHeaderColumn, TableRow, TableRowColumn}
    from 'material-ui/Table';
import FlatButton from 'material-ui/FlatButton';

const styles = {
    table : {
        minWidth: '835px',
        overflowX : 'scroll',
        overflowY : 'hidden',
        background : 'transparent',
    },
    tableBody : {
        minWidth: '835px',
        overflowX : 'auto',
        overflowY : 'hidden',
    }
};

const tableData_1 = [
    {
        caller: '18221087115',
        called: '18201987108',
        call_time:'2016/12/5 12:56:00',
        dur_time : '160',
        cost : '0.47',
    },
    {
        caller: '18221087115',
        called: '18201987108',
        call_time:'2016/12/5 12:56:00',
        dur_time : '50',
        cost : '0.19',
    },
    {
        caller: '18221087115',
        called: '18201987108',
        call_time:'2016/12/5 12:56:00',
        dur_time : '50',
        cost : '0.19',
    },
    {
        caller: '18221087115',
        called: '18201987108',
        call_time:'2016/12/5 12:56:00',
        dur_time : '120',
        cost : '0.38',
    },
    {
        caller: '18221087115',
        called: '18201987108',
        call_time:'2016/12/5 12:56:00',
        dur_time : '50',
        cost : '0.19',
    },
    {
        caller: '18221087115',
        called: '18201987108',
        call_time:'2016/12/5 12:56:00',
        dur_time : '50',
        cost : '0.19',
    },
];

const tableData_2 = [
    {
        caller: '18221087115',
        called: '18201987108',
        call_time:'2016/12/5 16:22:00',
        dur_time : '23',
        cost : '0.19',
    },
    {
        caller: '18221087115',
        called: '18201987108',
        call_time:'2016/12/5 13:44:00',
        dur_time : '78',
        cost : '0.19',
    },

];

export default class Record extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            current_page : 1,
            all_pages : 1,
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
                    selectable={false}
                    style={styles.table}
                    bodyStyle={styles.tableBody}
                >
                    <TableHeader
                        displaySelectAll={false}
                        adjustForCheckbox={false}
                    >
                        <TableRow>
                            <TableHeaderColumn colSpan="5" tooltip="通话记录" style={{textAlign: 'center'}}>
                                通话记录
                            </TableHeaderColumn>
                        </TableRow>
                        <TableRow>
                            <TableHeaderColumn tooltip="主叫">主叫</TableHeaderColumn>
                            <TableHeaderColumn tooltip="被叫">被叫</TableHeaderColumn>
                            <TableHeaderColumn tooltip="呼叫时间">呼叫时间</TableHeaderColumn>
                            <TableHeaderColumn tooltip="持续时间">持续时间</TableHeaderColumn>
                            <TableHeaderColumn tooltip="消费金额">消费金额</TableHeaderColumn>
                        </TableRow>
                    </TableHeader>
                    <TableBody
                        showRowHover={true}
                        displayRowCheckbox={false}>
                        {this.state.record.map((row, index) => (
                            <TableRow key={index}>
                                <TableRowColumn>{row.caller}</TableRowColumn>
                                <TableRowColumn>{row.called}</TableRowColumn>
                                <TableRowColumn>{row.call_time}</TableRowColumn>
                                <TableRowColumn>{row.dur_time}秒</TableRowColumn>
                                <TableRowColumn>{row.cost}元</TableRowColumn>
                            </TableRow>
                        ))}
                    </TableBody>
                    <TableFooter
                        adjustForCheckbox={false}
                    >
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