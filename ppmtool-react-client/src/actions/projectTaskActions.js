import axios from "axios";
import { GET_ERRORS, GET_BACKLOG } from "./types";

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
  } catch (error) {}
};
