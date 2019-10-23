import axios from "axios";
import { GET_ERRORS, GET_BACLKLOG } from './types';

export const addProjectTask = (backlog_id, project_task, history) => async dispatch => {

    try {
        await axios.post(`/api/backlog/${backlog_id}`, project_task);
        history.push(`/projectBoard/${backlog_id}`);
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};

export const getBacklog = backlog_id => async dispatch => {
    try {
        const res = await axios.get(`/api/backlog/${backlog_id}`)
        dispatch({
            type: GET_BACLKLOG,
            payload: res.data
        })
    } catch (err) {

    }
}