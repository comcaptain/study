## Basic

You can check following 2 links for detail:

- [Redux Fundamentals, Part 1: Redux Overview | Redux](https://redux.js.org/tutorials/fundamentals/part-1-overview)
- [Redux Fundamentals, Part 2: Concepts and Data Flow | Redux](https://redux.js.org/tutorials/fundamentals/part-2-concepts-data-flow)

### Big Picture

- store
- reducer
- action
- action dispatcher
- selector
- subscriber

And they work together like below:

- You must never directly modify or change the state that is kept inside the Redux store
- Instead, the only way to cause an update to the state is to create a plain **action** object that describes "something that happened in the application", and then **dispatch** the action to the store to tell it what happened.
- When an action is dispatched, the store runs the root **reducer** function, and lets it calculate the new state based on the old state and the action
- Finally, the store notifies **subscribers** that the state has been updated so the UI can be updated with the new data.

### A basic demo

First, add redux core dependency:

```html
<script src="https://unpkg.com/redux@latest/dist/redux.min.js"></script>
```

Declare an initial state:

```javascript
const initialState = {
  value: 0
};
```

Then create a reducer (i.e. event listener, it's like the controller in backend MVC framework):

```javascript
// Create a "reducer" function that determines what the new state
// should be when something happens in the app
function counterReducer(state = initialState, action) {
  // Reducers usually look at the type of action that happened
  // to decide how to update the state
  switch (action.type) {
    case "counter/incremented":
      return { ...state, value: state.value + 1 };
    case "counter/decremented":
      return { ...state, value: state.value - 1 };
    default:
      // If the reducer doesn't care about this action type,
      // return the existing state unchanged
      return state;
  }
}
```

Now create a store from reducer:

```javascript
const store = Redux.createStore(counterReducer);
```

Create a render method to render according to current state:

```javascript
const valueEl = document.getElementById("value");
function render() {
  const state = store.getState();
  valueEl.innerHTML = state.value.toString();
}
```

Do the initial rendering on start up and subsribe render method to store

```javascript
// Update the UI with the initial data
render();
// And subscribe to redraw whenever the data changes in the future
store.subscribe(render);
```

Then add event listener to dispatch the events:

```javascript
document
  .getElementById("increment")
  .addEventListener("click", function () {
    store.dispatch({ type: "counter/incremented" });
  });

document
  .getElementById("decrement")
  .addEventListener("click", function () {
    store.dispatch({ type: "counter/decremented" });
  });
```



### Real Pictures

![image-20210722203414701](.\images\image-20210722203414701-16269536588943.png)

![ReduxDataFlowDiagram](./images/ReduxDataFlowDiagram-49fa8c3968371d9ef6f2a1486bd40a26.gif)

### Component details

#### Action

- An action is a plain JS object that has a `type:string` property

- An action is like an event the describes what happened

- The typical format of action is `domain/eventName`, e.g. `todos/todoAdded`

- And usually it would have a payload field that contains extra information, e.g.

  ```javascript
  const addTodoAction = {
    type: 'todos/todoAdded',
    payload: 'Buy milk'
  }
  ```

#### Reduer

- A **reducer** is a function that receives the current `state` and an `action` object, and returns the new state: `(state, action) => newState`
- It's like a event listener in frontend tech stack or controller in backend mvc design
- It has following strict rules:
  - They should **only** calculate the new state value based on the `state` and `action` arguments
  - They are **not allowed to modify** the existing `state`. Instead, they must make *immutable updates*, by copying the existing `state` and making changes to the copied values.
  - They must not do any asynchronous logic, calculate random values, or cause other "side effects"

#### Store

- The center of redux that stores the application state
- You should only have **one** store instance of an application
- A store is created by passing in a reducer: `const store = Redux.createStore(counterReducer);`
- And you can invoke `store.getState()` to get current state

#### Dispatch

- Is a method on `store` that accepts an action as parameter: `store.dispatch({ type: 'counter/incremented' })`
- The only way to update state in the store is to call dispatch method
- Dispatch is like triggering an event

#### Selector

- Function that knows how to extract specific pieces of information from a store state value
- `const selectCounterValue = state => state.value`
- This can be helpful when system becomes complex

## [Reducer split & combine](https://redux.js.org/tutorials/fundamentals/part-3-state-actions-reducers)

Check [this](https://github.com/comcaptain/demo/commits/redux-fundamentals-example-app/redux-fundamentals-example-app) for demo commits

### Why split?

- As mentioned earlier, one application can only have one single store

- Corresponding to that, one application should have a single root reducer used to create the store

- Of course we can write a fat reducer that handles every single action, but you can image how bad it is:

  ```javascript
  export default function appReducer(state = initialState, action)
  {
  	// The reducer normally looks at the action type field to decide what happens
  	switch (action.type)
  	{
  		case 'todos/todoAdded': {
  			// return a copy of state with modified state.todos
  		}
  		case 'todos/todoToggled': {
  			// return a copy of state with modified state.todos
  		}
  		case 'filters/statusFilterChanged': {
  			// return a copy of state with modified state.filters
  		}
           ...
  	}
      return state
  }
  ```

  ### Split into smaller reducers

  - It's a convention to put code related to a specific feature to `features/<feature-name>/`xxxSlice.js

  - We can put our split reducer into the xxxSlice js file

  - For the example above, we can split it into `todosSlice.js`:

    ```javascript
    const initialState = [
    	{ id: 0, text: 'Learn React', completed: true },
    	{ id: 1, text: 'Learn Redux', completed: false, color: 'purple' },
    	{ id: 2, text: 'Build something fun!', completed: false, color: 'blue' }
    ]
    
    export default function todosReducer(state = initialState, action)
    {
    	switch (action.type)
    	{
    		case 'todos/todoAdded': {
    			// return a copy of todos with updated values
    		}
    		case 'todos/todoToggled': {
    			// return a copy of todos with updated values
    		}
    	}
        return state
    }
    ```

  - Similarly, we'll have `filtersSlice.js` which would export default reducer for `state.filters`

  ### Combine splitted reducers

  ```javascript
  import todosReducer from './features/todos/todosSlice'
  import filtersReducer from './features/filters/filtersSlice'
  
  export default function rootReducer(state = {}, action)
  {
  	// always return a new object for the root state
  	return {
  		// the value of `state.todos` is whatever the todos reducer returns
  		todos: todosReducer(state.todos, action),
  		// For both reducers, we only pass in their slice of the state
  		filters: filtersReducer(state.filters, action)
  	}
  }

### Simplify combining with `combineReducers` in redux

```javascript
import { combineReducers } from 'redux'

import todosReducer from './features/todos/todosSlice'
import filtersReducer from './features/filters/filtersSlice'

// combineReducers does nearly the same thing as the rootReducer in example above
export default rootReducer = combineReducers({
	// Define a top-level state field named `todos`, handled by `todosReducer`
	todos: todosReducer,
	filters: filtersReducer
})
```

