import { GET_PROJECTS } from "../actions/types";

const initialState = {
  projects: [], //List of projcts
  project: {} //Single Project
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_PROJECTS:
      return {
        ...state,
        projects: action.payload //assigning projects receving from server
      };
    default:
      return state;
  }
}
