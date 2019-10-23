import {
    GET_BACLKLOG,
    GET_PROJECT_TASK,
    DELETE_PROJECT_TASK
} from "../actions/projectActions";

const initialState = {
    project_tasks: [],
    project_task: {}
}

export default function (state = initialState, action) {
    switch (action.type) {

        case GET_BACLKLOG:
            return {
                ...state,
                project_tasks: action.payload
            }

        case GET_PROJECT_TASK:
            return {
                ...state,
                project_task: action.payload
            }

        case DELETE_PROJECT_TASK:
            return {
                ...state,
                // TO_DO
            }

        default:
            return state;

    }
}