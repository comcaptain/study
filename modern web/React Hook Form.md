## Re-render only happens when REALLY necessary

- Re-render would not happen when simply typing in input box 
  - This is because un-controlled components are used
- Form changes would not cause re-render unless its changes are explicitly subscribed

Example:

(*NOTE: React.StrictMode would double re-render in dev mode, you can check [this](https://stackoverflow.com/a/65167384/2334320) for more detail*)

```tsx
interface HumanName
{
	firstName: string;
	lastName: string;
}

let rerenderCount = 0;
export default function BasicDemo()
{
  // If errors is not included, then re-render would not happen when validation error changes
  // I guess this is achieved by using getter
	const { register, handleSubmit, formState: { errors } } = useForm<HumanName>();
	rerenderCount++;
	return <form onSubmit={handleSubmit(formValues => console.info(formValues))} id="basic-demo-form">
		<div id="rerender-count">{rerenderCount}</div>
		<div className="form-item">First Name: <input {...register("firstName", { required: "Required" })} /></div>
		<div className="form-item">Last Name: <input {...register("lastName", { required: "Required" })} /></div>
		<input type="submit" />
	</form>;
}
```

## input, radiobox, checkbox and select

- React hook form works perfectly well with all these four field types without any special configuration
- If you do not assign value attribute of checkbox, then it can map a boolean-typed field

```tsx
interface Person
{
	name: string;
	isMale: boolean;
	labels: string[];
	nationality: string;
	favouriteAnimal: string;
}

export default function BasicDemo()
{
	const { register, handleSubmit } = useForm<Person>({defaultValues: {name: "Tony", isMale: true, nationality: "China"}});
	return <form onSubmit={handleSubmit(formValues => console.log("Submit values", formValues))} id="basic-demo-form">
		<div className="form-item">Name: <input {...register("name",)} /></div>
		<div className="form-item"><label><input type="checkbox" {...register("isMale")} /> Is Male</label></div>
		<div className="form-item">
			<label><input type="checkbox" {...register("labels")} value="student" /> Student</label>
			<label><input type="checkbox" {...register("labels")} value="rich" /> Rich</label>
			<label><input type="checkbox" {...register("labels")} value="tall" /> Tall</label>
		</div>
		<div className="form-item">
			<label><input type="radio" {...register("nationality")} value="China" /> China</label>
			<label><input type="radio" {...register("nationality")} value="USA" /> USA</label>
			<label><input type="radio" {...register("nationality")} value="UK" /> UK</label>
		</div>
		<div className="form-item">
			Favourite animal: <select {...register("favouriteAnimal")}>
				<option value="panda">Panda</option>
				<option value="tiger">Tiger</option>
				<option value="cat">Cat</option>
				<option value="dog">Dog</option>
			</select>
		</div>
		<input type="submit" />
	</form>;
}
```

## Wrapped input/radiobox/checkbox/select

This section explains how to integrate with custom form field, i.e. plain old form field is wrapped in custom component

- Since react hook form uses uncontrolled component, `forwardRef` is needed to pass ref into internal html form field
- Besides ref, following attributes should be set on html form field:
  - onChange
  - onBlur
  - name
- value is not needed because uncontrolled component is used
- After those are done, you can use it like native html form field

```tsx
import { forwardRef } from 'react';
import { useForm, UseFormRegister } from 'react-hook-form';

interface Person
{
	name: string;
}
type CustomTextInputProps = ReturnType<UseFormRegister<Person>> & { label: string };
// First generic type is the target that would hold the ref
// Second generic type is the component props
const CustomTextInput = forwardRef<HTMLInputElement, CustomTextInputProps>(({ onChange, onBlur, name, label }, ref) =>
{
	return <div>
		<label>{label}:</label>
		<input type="text" onChange={onChange} onBlur={onBlur} name={name} ref={ref} />
	</div>
});
let renderCount = 0;
export default function WrappedFieldsDemo()
{
	renderCount++;
	const { handleSubmit, register } = useForm<Person>();
	return <form onSubmit={handleSubmit(data => console.log(data))}>
		{renderCount}
		<CustomTextInput {...register("name")} label="Name" />
		<input type="submit" />
	</form>
}
```

