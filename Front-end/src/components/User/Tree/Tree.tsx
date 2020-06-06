import React from 'react';
import tree from './tree.svg';
import './Tree.scss';
import Achievement from './Achievement/Achievement';
import IAchievement from './Achievement/IAchievement';

interface IProps {
    doneTasks: IAchievement[],
}

interface IState {
}

class Tree extends React.Component<IProps, IState> {

    renderAchievements() {
        return this.props.doneTasks.map((task : any) => {
            return <Achievement
                title={task.title}
                key={task.id}
                id={task.id}
                />
        });
    }

    render() {
        return (
            <div className="tree-block">
                <div className="tree-img">
                    <img className="tree-img-colorful" src={tree} alt="Tree" style={{height: (this.props.doneTasks.length * 10) + "%"}}/>
                    <img className="tree-img-grayscale" src={tree} alt="Tree"/>
                </div>
                <div className="achievements">
                    {this.renderAchievements()}
                </div>
            </div>

        );
    }
}


export default Tree;
