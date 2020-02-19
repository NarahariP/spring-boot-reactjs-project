import axios from "axios";
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT } from "./types";

export const CreateProject = (project, history) => async dispath => {
  try {
    const res = await axios.post(
      "http://localhost:8080/projects/add-project",
      project
    );
    history.push("/dashboard");
  } catch (error) {
    dispath({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const getProjects = () => async dispath => {
  const res = await axios.get("http://localhost:8080/projects");
  dispath({
    type: GET_PROJECTS,
    payload: res.data
  });
};

export const getProject = (id, history) => async dispath => {
  try {
    const res = await axios.get(
      `http://localhost:8080/projects/get-project/${id}`
    );
    dispath({
      type: GET_PROJECT,
      payload: res.data
    });
  } catch (error) {
    history.push("/dashboard");
  }
};
