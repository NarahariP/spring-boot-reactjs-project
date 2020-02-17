import React from "react";
import { Link } from "react-router-dom";

const CreateProjectButton = () => {
  return (
    <div>
      <Link
        to="/addProject"
        href="ProjectForm.html"
        className="btn btn-lg btn-info"
      >
        Create Project
      </Link>
    </div>
  );
};

export default CreateProjectButton;
