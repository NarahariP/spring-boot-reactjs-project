import React, { Component } from "react";
import { Link } from "react-router-dom";

export default class ProjectTask extends Component {
  render() {
    const { project_task } = this.props;
    let priorityString;
    let priorityClass;
    if (project_task.priority === 1) {
      priorityString = "High";
      priorityClass = "bg-danger text-light";
    }
    if (project_task.priority === 2) {
      priorityString = "Medium";
      priorityClass = "bg-warning text-light";
    }
    if (project_task.priority === 3) {
      priorityString = "Low";
      priorityClass = "bg-info text-light";
    }
    return (
      <div className="card mb-1 bg-light">
        <div className={`card-header text-primary ${priorityClass}`}>
          ID: {project_task.projectSequence} -- Priority: {priorityString}
        </div>
        <div className="card-body bg-light">
          <h5 className="card-title">{project_task.summary}</h5>
          <p className="card-text text-truncate ">
            {project_task.acceptanceCriteria}
          </p>
          <Link to="" className="fal fa-trash-alt btn btn-primary">
            &nbsp;View / Update
          </Link>
          <button className="fas fa-minus-circle btn btn-danger ml-2">
            &nbsp;Delete
          </button>
        </div>
      </div>
    );
  }
}
