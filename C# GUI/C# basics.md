## [C# Crash Course for Java Developers](https://nerdparadise.com/programming/csharpforjavadevs)

### About .NET

.NOT was created as a pure marketing term, and it has nothing to do with the internet. Now it's giant term that includes a collection of Microsoft technologies related to their developer tools

### C# project file structure

- **Solution (.sln) files** Content is list of related `.csproj` files
- **Project (.csproj) files** Smallest individual compilation unit
  - A csproj file will get compiled into a `.exe` or `.dll` file
  - They can also have dependencies on other project files (which don't necessarily have to be C#)
  - A `.csproj` file supports most of the compiler options, like a ant file
- **Code (.cs) files** 
  - It can have any name. Unlike java, the name does not matter
  - Unlike java, you can put cs file in any directory and it won't affect compilation
  - Namespaces (like package in java) can be whatever you want regardless of where the file is located

### Style conventions

- Classes are always capitalized, just like Java
- Methods are always capitalized, unlike Java
- Interfaces are always prefixed with an uppercase I
- Opening curly braces go on the next line
- Variables use **camelCase**, just like Java. Not **road_kill_case** like C++.
- However, unlike Java, Enum members are **PascalCase**, not upper **ROAD_KILL_CASE**.

### Term differences

- Java's package private class/method has a keyword in C# called `internal`. `internal` means only visible to other code within the same csproj file
- In C#, a final class is a `sealed`. And a final field is `readonly`
- `extends` and `implements` are placed by `:` in C#

An example:

```c#
public sealed class MyThing : AbstractMyThing, IThing
{
	private readonly int value;
	public MyThing(int value)
	{
		this.value = value;
	}
}
```

### static inner class

In C#, all inner classes are static and can only be static. When you add `static` to a class in C#, it means all fields and methods in the class are to be declared as static

### Type differences

- `boolean` -> `bool`
- `String` -> `string`. And you can safely use `==` for comparision
- `Object` -> `object`
- `List` interface -> `IList`
- `ArrayList` -> `List`
- `HashMap` -> `Dictionary`
- `Map` -> `IDictionary`

- `byte`: -128 to 127
- `ubyte`: 0 to 255
- `uint`: 0 to $2^{32} - 1$
- `ulong`: 0 to $2^{64} - 1$

 ### Properties

You can easily add getter/setter in C# like below:

```C#
class Foo
{
  public int Value { get; internal set; } // project-scoped setter
  
  public Foo(int value)
  {
    this.Value = value;
  }
}
...
Foo foo = new Foo(42);
foo.Value = -1;
int newValue = foo.Value;
```

With properties, you can even bypass the constructor arguments altogether and use the following syntax to set properties...

```c#
class Foo
{
  public int Value { get; internal set; }
}

Foo foo = new Foo() { Value = 42 };
```

And of course you can customize get and set method:

```c#
public class Foo
{
  private int value;
  public int Value
  {
    get
    {
      return this.value;
    }
    set
    {
      // the word "value" here that you're setting to the field
      // "this.value" is actually a C# keyword available in 
      // all setters.
      this.value = value; 
    }
  }

  public bool IsEven { get { return this.value % 2 == 0; } }
}
```





















