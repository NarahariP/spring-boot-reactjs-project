import React from "react";
import { Link } from "react-router-dom";

const CreateProjectButton = () => {
  return (
    <div>
      <Link to="/addProject" className="fas fa-plus-circle btn btn-lg btn-info">
        &nbsp;Create Project
      </Link>
    </div>
  );
};

export default CreateProjectButton;
