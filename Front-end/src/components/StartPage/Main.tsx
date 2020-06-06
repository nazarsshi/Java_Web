import React, {Component} from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import Sidebar from './Sidebar/Sidebar';
import './Main.scss';
import axios from 'axios';
import Task from './Task/Task';
import ITask from './Task/ITask';
import {CONFIG} from "../../config";
import IFilterParam from "./Task/IFilterParam";
import DateFormat from "../../utils/DateFormat/DateFormat";

interface IState {
    tasksFromApi: ITask[],
    offset: number,
    limit: number,
    hasMoreTasks: boolean
    filterParams: IFilterParam
}

export class Main extends Component<any, IState> {

    state = {
        tasksFromApi: [],
        offset: 0,
        limit: 20,
        hasMoreTasks: true,
        filterParams: {
            priority: "",
            order: "",
            category: "",
            criteria: ""
        }
    };

    componentDidMount() {
        this.loadNewTasks(this.state.filterParams);
    }

    setFilterParams = (params: IFilterParam) => {
        this.loadNewTasks(params, true);
    };

    loadNewTasks = (filterParams: IFilterParam, clearAll: boolean = false) => {
        let tasks: ITask[] = [];
        let offset = 0;
        if (!clearAll) {
            tasks = [...this.state.tasksFromApi];
            offset = this.state.offset;
        }

        let url = CONFIG.apiServer + "tasks/?offset=" + offset + "&limit=" + this.state.limit + "&category=" + filterParams.category + "&order=" + filterParams.order + "&priority=" + filterParams.priority + "&criteria=" + filterParams.criteria;

        axios.get(url).then(res => {
            const newTasks = res.data.entities as ITask[];

            newTasks.forEach((item: ITask) => {
                let creatorData = item.creator;
                item.creator = creatorData.firstName + " " + (creatorData.lastName !== null ? creatorData.lastName : '');
                item.category = item.category.name;
                tasks.push(item);
            });
            this.setState({
                tasksFromApi: tasks,
                offset: tasks.length,
                filterParams: filterParams,
                hasMoreTasks: res.data.entitiesLeft !== 0
            });
        }).catch(error => {
            this.setState({
                hasMoreTasks: false,
                tasksFromApi: clearAll ? [] : this.state.tasksFromApi
            });
        });
    };

    renderTasks = () => {
        const tasks = [...this.state.tasksFromApi];

        return tasks.map((task: ITask, index: number) => (
                <Task
                    id={task.id}
                    key={index}
                    title={task.title}
                    creator={task.creator}
                    description={task.description}
                    approvedParticipants={task.approvedParticipants}
                    numberOfParticipants={task.numberOfParticipants}
                    priority={task.priority}
                    category={task.category}
                    endDate={task.endDate}
                    photos={task.photos}
                    creationDate={new DateFormat().getDefaultDateFormat(task.creationDate)}
                />
            )
        );

    };


    render() {
        return (
            <div className='main_page'>
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-4 col-sm-12">
                            <Sidebar setFilterParams={this.setFilterParams}/>
                        </div>
                        <div className="col-md-8 col-sm-12">
                            <InfiniteScroll
                                dataLength={this.state.tasksFromApi.length}
                                next={() => {this.loadNewTasks(this.state.filterParams)}}
                                hasMore={this.state.hasMoreTasks}
                                loader={<p style={{textAlign: 'center'}}>Loading...</p>}
                                endMessage={
                                    <p style={{textAlign: 'center', color: 'gray'}}>No more tasks!</p>
                                }>
                                {this.renderTasks()}
                            </InfiniteScroll>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Main;
