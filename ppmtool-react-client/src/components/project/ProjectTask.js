import React, { Component } from "react";
import { Link } from "react-router-dom";
import { deleteProjectTask } from "../../actions/projectTaskActions";
import { connect } from "react-redux";
import { PropTypes } from "prop-types";

class ProjectTask extends Component {
  onDeleteClick(projectIdentifier, projectTaskId) {
    this.props.deleteProjectTask(projectIdentifier, projectTaskId);
  }
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
        <div
          className={`card-header text-primary text-center ${priorityClass}`}
        >
          {project_task.projectSequence} -- {priorityString}
        </div>
        <div className="card-body bg-light">
          <h5 className="card-title">{project_task.summary}</h5>
          <p className="card-text text-truncate ">
            {project_task.acceptanceCriteria}
          </p>
          <Link
            to={`/updateProjectTask/${project_task.projectIdentifier}/${project_task.projectSequence}`}
            className="fal fa-edit btn btn-primary"
          >
            <span className="pl-1">View / Update</span>
          </Link>
          <button
            className="fas fa-minus-circle btn btn-danger ml-2"
            onClick={this.onDeleteClick.bind(
              this,
              project_task.projectIdentifier,
              project_task.projectSequence
            )}
          >
            <span className="pl-1">Delete</span>
          </button>
        </div>
      </div>
    );
  }
}

ProjectTask.propTypes = {
  deleteProjectTask: PropTypes.func.isRequired
};

export default connect(null, { deleteProjectTask })(ProjectTask);
