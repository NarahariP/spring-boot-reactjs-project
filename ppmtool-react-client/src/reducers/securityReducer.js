import { SET_CUTRRENT_USER } from "../actions/types";

const initialState = {
  user: {},
  validToken: false
};

const booleanActivePayload = payload => {
  if (payload) {
    return true;
  } else {
    return false;
  }
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SET_CUTRRENT_USER:
      return {
        ...state,
        validToken: booleanActivePayload(action.payload),
        user: action.payload
      };
    default:
      return state;
  }
}
