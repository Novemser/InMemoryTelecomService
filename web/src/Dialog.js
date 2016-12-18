/**
 * Created by kevin on 12/10/2016.
 */
import React from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';

/**
 * Dialog with action buttons. The actions are passed in as an array of React objects,
 * in this example [FlatButtons](/#/components/flat-button).
 *
 * You can also close this dialog by clicking outside the dialog, or with the 'Esc' key.
 */
export default class SimpleDialog extends React.Component {

    state = {
        open: false,
        title : "Sign Success!",
        description : "Press OK to redirect to Home.",
        type : 'user',
    };

    handleOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
         if(this.props.onPress) {
             this.props.onPress(this.state.type);
         }
    };

    setContent = (title, description, type) => {
        this.setState({
            title : title,
            description : description,
            type : type,
        });
    };

    render() {
        const actions = [
            <FlatButton
                label="确定"
                primary={true}
                keyboardFocused={true}
                onTouchTap={this.handleClose}
            />,
        ];

        return (
            <div>
                <Dialog
                    title={this.state.title}
                    actions={actions}
                    modal={false}
                    open={this.state.open}
                    onRequestClose={this.handleClose}
                >
                    {this.state.description}
                </Dialog>
            </div>
        );
    }
}