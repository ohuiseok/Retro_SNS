import store from "@/store/store";
import axios from "axios";

export const SERVER_URL = "https://xyz-gen.com/backend/api";
const state = store.getState();

export const axiosInstance = axios.create({
  baseURL: SERVER_URL,
  headers: {
    Authorization: state.auth.accessToken,
  }, // 나중에 토큰 넣어주기
  withCredentials: true,
});

export const axiosFileInstance = axios.create({
  baseURL: SERVER_URL,
  headers: {
    "Content-type": "multipart/form-data",
    Authorization: state.auth.accessToken,
  }, // 나중에 토큰 넣어주기
  withCredentials: true,
});
