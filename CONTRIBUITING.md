> [!IMPORTANT]
> Rules are not hard to follow, and even though there are not many of them, it is important they are followed to keep a consistent codebase.

- **Tabs** are used for indentation, not spaces.
- The **tab length** shall be 4 for all languages (Java, JS, etc.).
- For **Java** and **JS**, function/method naming shall be done using `camelCase` (e.g., `exampleFunction()`).
- For **Java** and **JS**, class naming shall be done using `PascalCase` (e.g., `ExampleClass`).
- For **Java** and **JS**, variable naming shall be done using `camelCase` (e.g., `exampleVariable`).
- **Spacing:** Please use spaces around operators and assignments for readability:

```java
final int variable = 69;  // yes
final int variable=69;    // nop
```

```java
if (variable == 69) // yes
if (variable==69)   // nop
```

- Brace style will be ![B&K](https://gist.github.com/jesseschalken/0f47a2b5a738ced9c845)
```java
class Name {
	// yes
}

class Name
{
	// nop
}
```

- Naming files: when a file has more than one word, use name it with a `-` in between, for example `example-file.ex` rather than `exampleFile.ex`

That's it :)
