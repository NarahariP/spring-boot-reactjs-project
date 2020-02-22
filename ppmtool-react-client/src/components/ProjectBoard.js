import React, { Component } from "react";
import { Link } from "react-router-dom";
import BackLog from "./project/BackLog";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { getBacklog } from "../actions/projectTaskActions";

class ProjectBoard extends Component {
  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBacklog(id);
  }
  render() {
    const { id } = this.props.match.params;
    const { project_tasks } = this.props.backlog;
    return (
      <div className="container">
        <Link
          to={`/addProjectTask/${id}`}
          className="fas fa-plus-circle btn btn-info"
        >
          &nbsp; Create Project Task
        </Link>
        <hr />
        <BackLog projectTaskList={project_tasks} />
      </div>
    );
  }
}

ProjectBoard.propTypes = {
  backlog: PropTypes.object.isRequired,
  getBacklog: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  backlog: state.backlog
});

export default connect(mapStateToProps, { getBacklog })(ProjectBoard);
