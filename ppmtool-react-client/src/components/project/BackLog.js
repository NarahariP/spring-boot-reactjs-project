import React, { Component } from "react";
import ProjectTask from "./ProjectTask";

class BackLog extends Component {
  render() {
    const { projectTaskList } = this.props;
    const tasks = projectTaskList.map(project_task => (
      <ProjectTask key={project_task.id} project_task={project_task} />
    ));
    let todoItems = [];
    let inprogreeItems = [];
    let completedItems = [];
    tasks.forEach(element => {
      console.log(element);
      if (element.props.project_task.status === "TO_DO") {
        todoItems.push(element);
      } else if (element.props.project_task.status === "IN_PROGRESS") {
        inprogreeItems.push(element);
      } else if (element.props.project_task.status === "DONE") {
        completedItems.push(element);
      }
    });
    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h4>TO DO</h4>
              </div>
            </div>
            {todoItems}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h4>In Progress</h4>
              </div>
            </div>
            {inprogreeItems}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h4>Done</h4>
              </div>
            </div>
            {completedItems}
          </div>
        </div>
      </div>
    );
  }
}

export default BackLog;
