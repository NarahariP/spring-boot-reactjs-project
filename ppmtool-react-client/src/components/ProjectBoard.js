import React, { Component } from "react";
import { Link } from "react-router-dom";
import BackLog from "./project/BackLog";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { getBacklog } from "../actions/projectTaskActions";

class ProjectBoard extends Component {
  constructor() {
    super();
    this.state = {
      errors: {}
    };
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBacklog(id);
  }

  render() {
    const { id } = this.props.match.params;
    const { project_tasks } = this.props.backlog;
    const { errors } = this.props;
    let BoardContent;
    const boardAlgorithm = (errors, projectTasks) => {
      if (projectTasks.length < 1) {
        if (errors.message) {
          return (
            <div class="alert alert-danger text-center" role="alert">
              {errors.message}
            </div>
          );
        }
      } else {
        return <BackLog projectTaskList={project_tasks} />;
      }
    };

    BoardContent = boardAlgorithm(errors, project_tasks);
    return (
      <div className="container">
        <Link
          to={`/addProjectTask/${id}`}
          className="fas fa-plus-circle btn btn-info"
        >
          &nbsp; Create Project Task
        </Link>
        <hr />
        {BoardContent}
      </div>
    );
  }
}

ProjectBoard.propTypes = {
  backlog: PropTypes.object.isRequired,
  getBacklog: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  backlog: state.backlog,
  errors: state.errors
});

export default connect(mapStateToProps, { getBacklog })(ProjectBoard);
