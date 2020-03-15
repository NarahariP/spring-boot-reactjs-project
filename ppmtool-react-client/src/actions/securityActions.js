import axios from "axios";
import { GET_ERRORS, SET_CUTRRENT_USER } from "./types";
import setJWToken from "../securityUtils/setJWToken";
import jwt_decode from "jwt-decode";

export const CreateUser = (newUser, history) => async dispatch => {
  try {
    await axios.post("http://localhost:8080/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const login = loginRequest => async dispatch => {
  //after login -> extract Token and store in localStorage and ser that in Header ***
  try {
    const res = await axios.post(
      "http://localhost:8080/users/login",
      loginRequest
    );
    const { token } = res.data;
    localStorage.setItem("jwtToken", token);
    setJWToken(token);
    //for decode we need to install jwt-decode > npm install jwt-decode
    const decoded = jwt_decode(token);
    //setting current user
    dispatch({
      type: SET_CUTRRENT_USER,
      payload: decoded
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const logout = () => dispatch => {
  localStorage.removeItem("jwtToken");
  setJWToken(false);
  dispatch({
    type: SET_CUTRRENT_USER,
    payload: {}
  });
};
