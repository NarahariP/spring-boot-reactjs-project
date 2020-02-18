import React, { Component } from "react";
import ProjectItem from "./project/ProjectItem";
import CreateProjectButton from "./project/CreateProjectButton";
import { connect } from "react-redux";
import { getProjects } from "../actions/projectActions";
import PropType from "prop-types";

class Dashboard extends Component {
  componentDidMount() {
    this.props.getProjects();
  }

  render() {
    const { projects } = this.props.project;
    return (
      <div>
        <div className="projects">
          <div className="container">
            <div className="row">
              <div className="col-md-12">
                <h1 className="display-4 text-center">Projects</h1>
                <br />
                <CreateProjectButton />
                <br />
                <hr />
                {projects.map(project => (
                  <ProjectItem key={project.id} project={project} />
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propType = {
  project: PropType.object.isRequired,
  getProjects: PropType.func.isRequired
};

const mapStateProps = state => ({
  project: state.project // 'state.project' is coming form projectReducer which is confifigured in index.js from reducer folder
});

export default connect(mapStateProps, { getProjects })(Dashboard);
