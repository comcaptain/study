# What's behind redux toolkit?

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

```bash
npm install redux
```

```javascript
import { combineReducers } from 'redux'

import todosReducer from './features/todos/todosSlice'
import filtersReducer from './features/filters/filtersSlice'

// combineReducers does nearly the same thing as the rootReducer in example above
const rootReducer = combineReducers({
	// Define a top-level state field named `todos`, handled by `todosReducer`
	todos: todosReducer,
	filters: filtersReducer
})
export default rootReducer
```

## [A simple store implemenation](https://redux.js.org/tutorials/fundamentals/part-4-store)

*Check [this](https://github.com/comcaptain/demo/commits/redux-fundamentals-example-app/redux-fundamentals-example-app) for demo commits*

A store usage sample:

```javascript
import { createStore } from 'redux'
import rootReducer from './reducer'

let preloadedState
const persistedTodosString = localStorage.getItem('todos')

if (persistedTodosString)
{
	preloadedState = {
		todos: JSON.parse(persistedTodosString)
	}
}

const store = createStore(rootReducer, preloadedState)

export default store;
```

And it would be used like this:

```javascript
import store from './store'
// Log the initial state
console.log('Initial state: ', store.getState())
// {todos: [....], filters: {status, colors}}

// Every time the state changes, log it
// Note that subscribe() returns a function for unregistering the listener
const unsubscribe = store.subscribe(() =>
	console.log('State after dispatch: ', store.getState())
)

// Now, dispatch some actions

store.dispatch({ type: 'todos/todoAdded', payload: 'Learn about actions' })
store.dispatch({ type: 'todos/todoToggled', payload: 1 })
store.dispatch({ type: 'filters/statusFilterChanged', payload: 'Active' })

store.dispatch({
	type: 'filters/colorFilterChanged',
	payload: { color: 'red', changeType: 'added' }
})

// Stop listening to state updates
unsubscribe()

// Dispatch one more action to see what happens

store.dispatch({ type: 'todos/todoAdded', payload: 'Try creating a store' })

```

We can replace `createStore` from redux from following implementation and the test code above would still work:

```javascript
function createStore(reducer, preloadedState)
{
	let state = preloadedState
	const listeners = []

	function getState()
	{
		return state
	}

	function subscribe(listener)
	{
		listeners.push(listener)
		return function unsubscribe()
		{
			const index = listeners.indexOf(listener)
			listeners.splice(index, 1)
		}
	}

	function dispatch(action)
	{
		state = reducer(state, action)
		listeners.forEach(listener => listener())
	}

	dispatch({ type: '@@redux/INIT' })

	return { dispatch, subscribe, getState }
}
```

## [Store enhancers & middleware](https://redux.js.org/tutorials/fundamentals/part-4-store)

*Check [this](https://github.com/comcaptain/demo/commits/redux-fundamentals-example-app/redux-fundamentals-example-app) for demo commits*

### What is Enhancer

- An enhancer can be passed as third parameter of redux's `createStore` method
- It's like a decorator in decorator design pattern. With it, you can override store's default methods. e.g. getState, dispatch

### Write an enhancer

#### Example 1: `console.log('Hi!')` when dispatching an event

```javascript
export const sayHiOnDispatch = (createStore) =>
{
	return (rootReducer, preloadedState, enhancers) =>
	{
		const store = createStore(rootReducer, preloadedState, enhancers)

		function newDispatch(action)
		{
			const result = store.dispatch(action)
			console.log('Hi!')
			return result
		}
		
		return { ...store, dispatch: newDispatch }
	}
}
```

#### Example 2: Add `meaningOfLife: 42` when `getState()`

```javascript
export const includeMeaningOfLife = (createStore) =>
{
	return (rootReducer, preloadedState, enhancers) =>
	{
		const store = createStore(rootReducer, preloadedState, enhancers)

		function newGetState()
		{
			return {
				...store.getState(),
				meaningOfLife: 42,
			}
		}

		return { ...store, getState: newGetState }
	}
}
```

#### Sample usage of enhancer

```javascript
import { sayHiOnDispatch } from './exampleAddons/enhancers'
const store = createStore(rootReducer, preloadedState, sayHiOnDispatch)
```

### Compose multiple enhancers

```javascript
import { createStore, compose } from 'redux'
import rootReducer from './reducer'
import
	{
		sayHiOnDispatch,
		includeMeaningOfLife
	} from './exampleAddons/enhancers'

const composedEnhancer = compose(sayHiOnDispatch, includeMeaningOfLife)

const store = createStore(rootReducer, undefined, composedEnhancer)

export default store
```

### What is middleware?

- It's like webfilter in backend mvc design

- It's a special "enhancer" that changes redux store's `dispatch` method behaviour

- Middleware is not a real enhancer, instead, we create a real enhancer using redux's `applyMiddleware`:

  ```javascript
  import { createStore, applyMiddleware } from 'redux'
  import rootReducer from './reducer'
  import { print1, print2, print3 } from './exampleAddons/middleware'
  
  const middlewareEnhancer = applyMiddleware(print1, print2, print3)
  
  // Pass enhancer as the second arg, since there's no preloadedState
  const store = createStore(rootReducer, middlewareEnhancer)
  
  export default store
  ```

### [Write a middleware](https://redux.js.org/tutorials/fundamentals/part-4-store#writing-custom-middleware)

#### Basic structure

```javascript
// This is the middleware itself
// storeAPI is an object containing the store's {dispatch, getState} functions
function exampleMiddleware(storeAPI)
{
    // next is:
    //   handleAction function of next middleware if current middleware is not the last one in the middleware chain
    //   dispatch function of the store
	return function wrapDispatch(next)
	{
		return function handleAction(action)
		{
			// Do anything here: pass the action onwards with next(action),
			// or restart the pipeline with storeAPI.dispatch(action)
			// Can also use storeAPI.getState() here

			return next(action)
		}
	}
}
```

And we can simplify it to:

```javascript
const anotherExampleMiddleware = storeAPI => next => action => {
  // Do something in here, when each action is dispatched

  return next(action)
}
```

#### A simple middleware

```javascript
const loggerMiddleware = storeAPI => next => action => {
  console.log('dispatching', action)
  let result = next(action)
  console.log('next state', storeAPI.getState())
  return result
}
const middlewareEnhancer = applyMiddleware(loggerMiddleware)
const store = createStore(rootReducer, middlewareEnhancer)
```

## Enable redux devtools extension

In browser side, install `Redux DevTools` plugin

In code side:

1. Install dependency

   ```bash
   npm install redux-devtools-extension
   ```

2. Use `composeWithDevTools` to wrap enhancer:

   ```javascript
   import { createStore, applyMiddleware } from 'redux'
   import { composeWithDevTools } from 'redux-devtools-extension'
   import rootReducer from './reducer'
   import { print1, print2, print3 } from './exampleAddons/middleware'
   
   const composedEnhancer = composeWithDevTools(
   	// EXAMPLE: Add whatever middleware you actually want to use here
   	applyMiddleware(print1, print2, print3)
   	// other store enhancers if any
   )
   
   const store = createStore(rootReducer, composedEnhancer)
   export default store
   ```

   

# Use redux toolkit

```bash
npm install @reduxjs/toolkit
# Following packages are no longer needed because they are dependency of @reduxjs/toolkit
# You still need react-redux
npm uninstall redux redux-thunk reselect redux-devtools-extension
```



## [Simplify rootReducer & store](https://redux.js.org/tutorials/fundamentals/part-8-modern-redux#store-setup)

Before:

```js
import { combineReducers } from 'redux'

import todosReducer from './features/todos/todosSlice'
import filtersReducer from './features/filters/filtersSlice'

const rootReducer = combineReducers({
  // Define a top-level state field named `todos`, handled by `todosReducer`
  todos: todosReducer,
  filters: filtersReducer
})

export default rootReducer
```

```js
import { createStore, applyMiddleware } from 'redux'
import thunkMiddleware from 'redux-thunk'
import { composeWithDevTools } from 'redux-devtools-extension'
import rootReducer from './reducer'

const composedEnhancer = composeWithDevTools(applyMiddleware(thunkMiddleware))

const store = createStore(rootReducer, composedEnhancer)
export default store
```

After:

```js
import { configureStore } from '@reduxjs/toolkit'

import todosReducer from './features/todos/todosSlice'
import filtersReducer from './features/filters/filtersSlice'

const store = configureStore({
  reducer: {
    // Define a top-level state field named `todos`, handled by `todosReducer`
    todos: todosReducer,
    filters: filtersReducer
  }
})

export default store
```

It does following things:

- Combine splitted reducer into root reducer
- Create store from root reducer
- Automatically add thunk middleware
- Automatically add more middleware to check for common mistakes
- Apply `composeWithDevTools` on enhancement internally to turn on redux browser extension support

## [createSlice](https://redux.js.org/tutorials/fundamentals/part-8-modern-redux#writing-slices)

### Introdcution

A slice is composed of 3 parts:

- reducer
  - This is default export
- action factory
- selector

`createSlice` would help to create reducer and non-async action factory

### Example

```js
import { createSlice } from "@reduxjs/toolkit"

const initialState = {
	status: StatusFilters.All,
	colors: [],
}

const filtersSlice = createSlice({
	name: 'filters',
	initialState,
	reducers: {
		statusFilterChanged(state, action)
		{
			state.status = action.payload;
		},
		colorFilterChanged: {
			prepare(color, changeType)
			{
				return {
					payload: { color, changeType }
				}
			},
			reducer(state, action)
			{
				const { color, changeType } = action.payload;
				if ('added' === changeType)
				{
					if (state.colors.includes(color)) return;
					state.colors.push(color)
				}
				else
				{
					state.colors = state.colors.filter($ => color !== $);
				}
			}
		}
	}
})

export default filtersSlice.reducer;
export const { statusFilterChanged, colorFilterChanged } = filtersSlice.actions;
```

- In reducer implementation, you can safely do state "mutation"
  - Because internally, library called [Immer](https://immerjs.github.io/immer/) is used
  - The library would create a proxy wrapper for state, record what's changed, and finally create an immutable state copy
- `name` field is prefix of action type
  - e.g. For `statusFilterChanged`, the action type would be `filters/statusFilterChanged`
- If action factory method accepts at most one parameter, then we can define its reducer like `statusFilterChanged`
- But if action factory method accepts more than one parameter, the reducer should have 2 functions:
  - `prepare` function has same parameters as action factory
  - Its return value would be set to `reducer` function's `action.payload`

## Create async action factory via `createAsyncThunk`

```js
// 1st parameter is action type
// Returned value itself is action factory for 'todos/saveNewTodo'
// Returned value also has 3 properties, they are all action factories:
// fetchTodos.pending: todos/fetchTodos/pending
// fetchTodos.fulfilled: todos/fetchTodos/fulfilled
// fetchTodos.rejected: todos/fetchTodos/rejected
export const saveNewTodo = createAsyncThunk('todos/saveNewTodo', async text => {
  const initialTodo = { text }
  const response = await client.post('/fakeApi/todos', { todo: initialTodo })
  return response.todo
})

// Then we can add handler for those 3 actions in createReducer:
const todosSlice = createSlice({
	name: 'todos',
	initialState,
	reducers: {
		// ...
	},
	extraReducers: builder => builder
    	// First parameter is action factory, 2nd function's parameter list is same as reducer
		.addCase(saveNewTodo.fulfilled, (state, action) =>
		{
			const todo = action.payload;
			state.entities[todo.id] = todo;
		})
})
```

## [createEntityAdapter](https://redux-toolkit.js.org/api/createEntityAdapter)

### Introduction

It provides selectors and CRUD operations for entities

### Details

This is used for following state structure:

```js
{
    // some other fields,
    entities: [] // Each entity must have a primary key
}
```

Internally, the state would be maintained in following structure:

```javascript
{
  // The unique IDs of each item. Must be strings or numbers
  ids: []
  // A lookup table mapping entity IDs to the corresponding entity objects
  entities: {
  }
}
```

Create empty adapter:

```js
import { createEntityAdapter } from '@reduxjs/toolkit'
// This method accepts an optional object parameter that has two optional properties:
// - selectId. Useful when entity primary key is not entity.id. e.g. book => book.bookId
// - sortComparer. If specified, then entities would be sorted. (a: T, b: T) => number
const todosAdapter = createEntityAdapter();
```

We can use the adapter to get initial state:

```js
const initialState = todosAdapter.getInitialState({ status: 'idle' });
```

And we can use the adapter to [CRUD](https://redux-toolkit.js.org/api/createEntityAdapter#crud-functions), which is similar to JPA

```js
todosAdapter.removeOne(state, action.payload)
todosAdapter.setAll(state, action.payload);
```

We can get selectors from the adapter and then export them:

```js
// The state here is root state
const todosSelector = todosAdapter.getSelectors(state => state.todos);
// selectAll & selectById are properties of todosSelector, and exported name would be selectTodos, selectTodoById
export const { selectAll: selectTodos, selectById: selectTodoById } = todosSelector;
```

