import axios from "axios";
import {
  GET_ERRORS,
  GET_BACKLOG,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK
} from "./types";

export const addProjectTask = (
  projectIdentifier,
  projectTask,
  history
) => async dispath => {
  try {
    await axios.post(
      `http://localhost:8080/backlog/${projectIdentifier}`,
      projectTask
    );
    history.push(`/projectBoard/${projectIdentifier}`);
    dispath({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispath({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const getBacklog = projectIdentifier => async dispath => {
  try {
    const res = await axios.get(
      `http://localhost:8080/backlog/${projectIdentifier}`
    );
    dispath({
      type: GET_BACKLOG,
      payload: res.data
    });
  } catch (error) {
    dispath({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const getProjectTask = (
  projectIdentifier,
  projectTaskId,
  history
) => async dispath => {
  try {
    const res = await axios.get(
      `http://localhost:8080/backlog/${projectIdentifier}/${projectTaskId}`
    );
    dispath({
      type: GET_PROJECT_TASK,
      payload: res.data
    });
  } catch (error) {
    history.push("/dashboard");
  }
};

export const updateProjectTask = (
  projectIdentifier,
  projectTask,
  history
) => async dispath => {
  try {
    await axios.post(
      `http://localhost:8080/backlog/${projectIdentifier}`,
      projectTask
    );
    history.push(`/projectBoard/${projectIdentifier}`);
    dispath({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispath({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const deleteProjectTask = (
  projectIdentifier,
  projectTaskId
) => async dispath => {
  if (
    window.confirm(
      `You are deleting Project Task ${projectTaskId}, this action cannot be undone.`
    )
  ) {
    await axios.delete(
      `http://localhost:8080/backlog/${projectIdentifier}/${projectTaskId}`
    );
    dispath({
      type: DELETE_PROJECT_TASK,
      payload: projectTaskId
    });
  }
};
