import React, {Component} from 'react';
import './Sidebar.scss';
import ICategory from './ICategory'
import axios from "axios";
import {CONFIG} from "../../../config";
import IFilterParam from "../Task/IFilterParam";


interface IState {
    categoriesFromApi: IOption[],
    filterParams: IFilterParam
}

interface IOption {
    value: String,
    label: String
}


export class Sidebar extends Component<any, IState> {
    state = {
        categoriesFromApi: [],
        filterParams: {
            priority: "",
            order: "",
            category: "",
            criteria: ""
        }
    };

    componentDidMount() {

        let categories: IOption[] = [...this.state.categoriesFromApi];

        axios.get(CONFIG.apiServer + "categories/").then(res => {
            const newCategories = res.data as ICategory[];
            newCategories.forEach((item: ICategory) => {
                let itemOption: IOption = {
                    value: item.name,
                    label: item.name
                };
                categories.push(itemOption);
            });
            let filterParams = {...this.state.filterParams};
            this.setState({
                categoriesFromApi: categories,
                filterParams: filterParams
            });
        });
    }

    renderCategories = () => {
        const categories = [...this.state.categoriesFromApi];
        return categories.map((category: IOption, index: number) => (
                <option value={category.value.toString()} key={index}> {category.label}</option>
            )
        );
    };

    handleChange(event: React.FormEvent) {
        let unsafeSearchTypeValue = ((event.target) as any).value;
        let filterParams = {...this.state.filterParams};
        let nameOfFilterParam = ((event.target) as any).name;
        // @ts-ignore
        filterParams[nameOfFilterParam] = unsafeSearchTypeValue;
        this.setState({filterParams})
        };



    render() {
        return (<div className="Filter_block">
                <div className="container">
                    <form onSubmit={(event) => {
                        event.preventDefault();
                        this.props.setFilterParams(this.state.filterParams);
                    }}>
                        <div className="form-group">
                            <label className="label-icon" htmlFor="email-0.8513741888192583">
                                <i className="fas fa-search" aria-hidden="true"/>
                            </label>
                            <input className="searchParam form-control" type="text" onChange={event => this.handleChange(event)} value={this.state.filterParams.criteria} name="criteria" placeholder="Search"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="filterBlockCategoriesParam">Categories:</label>
                            <select className="form-control" name="category" value={this.state.filterParams.category}
                                    onChange={event => this.handleChange(event)}>
                                <option value=" ">All</option>
                                {this.renderCategories()}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="filterBlockOrderParam">Order:</label>
                            <select className="form-control" name="order" value={this.state.filterParams.order}
                                    onChange={event => this.handleChange(event)}>
                                <option value="desc">From New</option>
                                <option value="asc">From Old</option>
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="filterBlockPriorityParam">Priority:</label>
                            <select className="form-control" name="priority" value={this.state.filterParams.priority}
                                    onChange={event => this.handleChange(event)}>
                                <option value=" ">All</option>
                                <option value="low">Low</option>
                                <option value="medium">Medium</option>
                                <option value="high">High</option>
                                <option value="critical">Critical</option>
                            </select>
                        </div>
                        <div className="form-group"><input className="btn btn-primary" type="submit" value="Search"/>
                        </div>
                    </form>
                </div>
            </div>
        );
    }
}

export default Sidebar;
