# Java Core Concepts - Complete Interview Guide
## Theory + Examples + Interview Questions

> [!IMPORTANT]
> This guide covers all Java fundamentals expected for a 12-year experienced automation engineer. Each concept includes theory, practical examples, and interview questions.

---

## Table of Contents
1. [Java Basics & Fundamentals](#java-basics--fundamentals)
2. [Object-Oriented Programming (OOP)](#object-oriented-programming-oop)
3. [Java Collections Framework](#java-collections-framework)
4. [String Handling](#string-handling)
5. [Exception Handling](#exception-handling)
6. [Multithreading & Concurrency](#multithreading--concurrency)
7. [Java 8+ Features](#java-8-features)
8. [Memory Management](#memory-management)

---

# Java Basics & Fundamentals

## 1. Data Types & Variables

### Theory
Java has two categories of data types:
- **Primitive Types**: byte, short, int, long, float, double, char, boolean
- **Reference Types**: Classes, Interfaces, Arrays

### Examples
```java
// Primitive types
int age = 30;
double salary = 75000.50;
boolean isActive = true;
char grade = 'A';

// Reference types
String name = "John Doe";
Integer count = 100; // Wrapper class (autoboxing)

// Type conversion
int num = 10;
double decimal = num; // Implicit casting (widening)
int result = (int) 3.14; // Explicit casting (narrowing)
```

### Interview Questions
**Q: What's the difference between int and Integer?**
- `int` is primitive (4 bytes, default 0)
- `Integer` is wrapper class (object, default null, has methods)

**Q: What is autoboxing and unboxing?**
```java
// Autoboxing: primitive to wrapper
Integer obj = 10; // int to Integer

// Unboxing: wrapper to primitive
int num = obj; // Integer to int
```

---

## 2. Access Modifiers

### Theory
| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| public | ✅ | ✅ | ✅ | ✅ |
| protected | ✅ | ✅ | ✅ | ❌ |
| default | ✅ | ✅ | ❌ | ❌ |
| private | ✅ | ❌ | ❌ | ❌ |

### Examples
```java
public class AccessModifierDemo {
    public String publicVar = "Accessible everywhere";
    protected String protectedVar = "Accessible in package & subclasses";
    String defaultVar = "Accessible in package only";
    private String privateVar = "Accessible in class only";
    
    private void privateMethod() {
        // Only accessible within this class
    }
}
```

---

## 3. Static vs Non-Static

### Theory
- **Static**: Belongs to class, shared across all instances, loaded at class loading time
- **Non-static**: Belongs to object instance, separate copy for each object

### Examples
```java
public class Counter {
    static int staticCount = 0;  // Shared across all instances
    int instanceCount = 0;       // Separate for each instance
    
    public Counter() {
        staticCount++;
        instanceCount++;
    }
    
    // Static method - can only access static members
    public static void displayStaticCount() {
        System.out.println("Static count: " + staticCount);
        // System.out.println(instanceCount); // ERROR!
    }
    
    // Instance method - can access both static and instance members
    public void displayCounts() {
        System.out.println("Static: " + staticCount);
        System.out.println("Instance: " + instanceCount);
    }
}

// Usage
Counter c1 = new Counter(); // staticCount=1, c1.instanceCount=1
Counter c2 = new Counter(); // staticCount=2, c2.instanceCount=1
Counter c3 = new Counter(); // staticCount=3, c3.instanceCount=1
```

### Interview Questions
**Q: Can we override static methods?**
- No, static methods are hidden, not overridden (method hiding, not polymorphism)

**Q: Why is the main method static?**
- JVM can call it without creating an object instance

---

## 4. Final Keyword

### Theory
- **final variable**: Constant, cannot be reassigned
- **final method**: Cannot be overridden
- **final class**: Cannot be extended

### Examples
```java
// Final variable
final int MAX_SIZE = 100;
// MAX_SIZE = 200; // ERROR!

// Final method
class Parent {
    final void display() {
        System.out.println("Cannot override this");
    }
}

class Child extends Parent {
    // void display() {} // ERROR! Cannot override
}

// Final class
final class ImmutableClass {
    // Cannot be extended
}

// class SubClass extends ImmutableClass {} // ERROR!

// Blank final variable (initialized in constructor)
class Demo {
    final int value;
    
    Demo(int val) {
        this.value = val; // Must initialize in constructor
    }
}
```

### Interview Questions
**Q: Difference between final, finally, and finalize?**
- `final`: Keyword for constants, non-overridable methods, non-extendable classes
- `finally`: Block that always executes after try-catch
- `finalize()`: Method called by garbage collector before object destruction (deprecated in Java 9+)

---

# Object-Oriented Programming (OOP)

## 1. Encapsulation

### Theory
Bundling data (variables) and methods together, hiding internal implementation using access modifiers.

### Examples
```java
public class BankAccount {
    // Private variables (data hiding)
    private String accountNumber;
    private double balance;
    
    // Public constructor
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    // Public getters and setters (controlled access)
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

// Usage
BankAccount account = new BankAccount("ACC123", 1000);
account.deposit(500);
// account.balance = 10000; // ERROR! Cannot access private field
```

### Benefits
✅ Data hiding and security  
✅ Flexibility to change implementation  
✅ Validation and business logic in setters  
✅ Read-only or write-only properties  

---

## 2. Inheritance

### Theory
Mechanism where one class acquires properties and behaviors of another class.

### Examples
```java
// Parent class (Superclass)
class Vehicle {
    protected String brand;
    protected int year;
    
    public Vehicle(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }
    
    public void start() {
        System.out.println(brand + " is starting...");
    }
}

// Child class (Subclass)
class Car extends Vehicle {
    private int doors;
    
    public Car(String brand, int year, int doors) {
        super(brand, year); // Call parent constructor
        this.doors = doors;
    }
    
    @Override
    public void start() {
        System.out.println("Car " + brand + " is starting with key...");
    }
    
    public void honk() {
        System.out.println("Beep beep!");
    }
}

// Usage
Car myCar = new Car("Toyota", 2023, 4);
myCar.start(); // Overridden method
myCar.honk();  // Child-specific method
```

### Types of Inheritance in Java
```java
// Single Inheritance
class A {}
class B extends A {}

// Multilevel Inheritance
class A {}
class B extends A {}
class C extends B {}

// Hierarchical Inheritance
class A {}
class B extends A {}
class C extends A {}

// Multiple Inheritance (NOT supported with classes, use interfaces)
interface A {}
interface B {}
class C implements A, B {} // Allowed with interfaces
```

### Interview Questions
**Q: Why doesn't Java support multiple inheritance with classes?**
- Diamond problem: ambiguity when two parent classes have same method
- Java 8+ allows default methods in interfaces with conflict resolution

---

## 3. Polymorphism

### Theory
"Many forms" - ability of an object to take multiple forms.

### Types
1. **Compile-time Polymorphism** (Method Overloading)
2. **Runtime Polymorphism** (Method Overriding)

### Examples

#### Method Overloading (Compile-time)
```java
class Calculator {
    // Same method name, different parameters
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        return a + b + c;
    }
    
    // Different return type alone is NOT overloading
    // public double add(int a, int b) {} // ERROR!
}
```

#### Method Overriding (Runtime)
```java
class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Bark bark!");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow meow!");
    }
}

// Runtime polymorphism
Animal myPet = new Dog(); // Upcasting
myPet.makeSound(); // Output: Bark bark! (decided at runtime)

myPet = new Cat();
myPet.makeSound(); // Output: Meow meow!
```

### Interview Questions
**Q: Difference between overloading and overriding?**

| Overloading | Overriding |
|-------------|------------|
| Compile-time | Runtime |
| Same class | Different classes (inheritance) |
| Different parameters | Same signature |
| Return type can differ | Return type must be same/covariant |
| Static methods can be overloaded | Static methods cannot be overridden |

---

## 4. Abstraction

### Theory
Hiding implementation details and showing only essential features.

### Examples

#### Abstract Class
```java
abstract class Shape {
    protected String color;
    
    // Abstract method (no implementation)
    public abstract double calculateArea();
    
    // Concrete method
    public void setColor(String color) {
        this.color = color;
    }
}

class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private double length, width;
    
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    @Override
    public double calculateArea() {
        return length * width;
    }
}

// Usage
Shape shape1 = new Circle(5);
Shape shape2 = new Rectangle(4, 6);
System.out.println(shape1.calculateArea()); // 78.54
System.out.println(shape2.calculateArea()); // 24.0
```

#### Interface
```java
interface Drawable {
    // All methods are public abstract by default
    void draw();
    
    // Java 8+: Default method
    default void display() {
        System.out.println("Displaying shape");
    }
    
    // Java 8+: Static method
    static void info() {
        System.out.println("Drawable interface");
    }
}

interface Resizable {
    void resize(double factor);
}

// Multiple interface implementation
class Square implements Drawable, Resizable {
    private double side;
    
    @Override
    public void draw() {
        System.out.println("Drawing square");
    }
    
    @Override
    public void resize(double factor) {
        side *= factor;
    }
}
```

### Interview Questions
**Q: Abstract class vs Interface?**

| Abstract Class | Interface |
|----------------|-----------|
| Can have constructors | Cannot have constructors |
| Can have instance variables | Only constants (public static final) |
| Can have concrete methods | Only abstract (before Java 8) |
| Single inheritance | Multiple inheritance |
| Use: IS-A relationship | Use: CAN-DO relationship |

---

# Java Collections Framework

## Collections Hierarchy

```
Collection (Interface)
├── List (Interface)
│   ├── ArrayList
│   ├── LinkedList
│   └── Vector
│       └── Stack
├── Set (Interface)
│   ├── HashSet
│   ├── LinkedHashSet
│   └── SortedSet (Interface)
│       └── TreeSet
└── Queue (Interface)
    ├── PriorityQueue
    └── Deque (Interface)
        └── ArrayDeque

Map (Interface) - Not part of Collection
├── HashMap
├── LinkedHashMap
├── Hashtable
└── SortedMap (Interface)
    └── TreeMap
```

---

## 1. ArrayList

### Theory
- Resizable array implementation
- Maintains insertion order
- Allows duplicates and null values
- Not synchronized (not thread-safe)
- Fast random access (index-based)

### Examples
```java
import java.util.*;

// Creation
ArrayList<String> list = new ArrayList<>();
List<String> list2 = new ArrayList<>(Arrays.asList("A", "B", "C"));

// Adding elements
list.add("Apple");
list.add("Banana");
list.add(1, "Orange"); // Insert at index
list.addAll(Arrays.asList("Mango", "Grapes"));

// Accessing elements
String first = list.get(0);
int size = list.size();

// Updating
list.set(0, "Pineapple");

// Removing
list.remove(0); // By index
list.remove("Banana"); // By object
list.clear(); // Remove all

// Iteration
for (String fruit : list) {
    System.out.println(fruit);
}

// Using Iterator
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    System.out.println(it.next());
}

// Java 8 forEach
list.forEach(fruit -> System.out.println(fruit));

// Searching
boolean contains = list.contains("Apple");
int index = list.indexOf("Mango");

// Sorting
Collections.sort(list);
list.sort(Comparator.naturalOrder());
```

### Internal Working
- **Initial capacity**: 10
- **Growth**: New capacity = (oldCapacity * 3/2) + 1
- **Array copying**: When capacity exceeded, creates new array and copies elements

### Interview Questions
**Q: ArrayList vs Array?**
- Array: Fixed size, can store primitives
- ArrayList: Dynamic size, only objects, provides utility methods

---

## 2. LinkedList

### Theory
- Doubly linked list implementation
- Implements both List and Deque interfaces
- Better for insertion/deletion operations
- Slower random access than ArrayList

### Examples
```java
LinkedList<String> list = new LinkedList<>();

// List operations
list.add("First");
list.add("Second");
list.add("Third");

// Deque operations (both ends)
list.addFirst("Start");
list.addLast("End");
list.removeFirst();
list.removeLast();

// Queue operations
list.offer("Element"); // Add to end
String element = list.poll(); // Remove from front
String peek = list.peek(); // View front without removing
```

### Interview Questions
**Q: ArrayList vs LinkedList?**

| ArrayList | LinkedList |
|-----------|------------|
| Array-based | Doubly linked nodes |
| Fast random access O(1) | Slow random access O(n) |
| Slow insertion/deletion O(n) | Fast insertion/deletion O(1) |
| Less memory overhead | More memory (node pointers) |
| Use: Frequent access | Use: Frequent add/remove |

---

## 3. HashMap

### Theory
- Key-value pair storage
- Uses hashing for fast retrieval
- Allows one null key and multiple null values
- Not synchronized
- No ordering guarantee

### Examples
```java
HashMap<String, Integer> map = new HashMap<>();

// Adding elements
map.put("Apple", 10);
map.put("Banana", 20);
map.put("Orange", 15);
map.put("Apple", 25); // Updates existing key

// Accessing
int value = map.get("Apple"); // 25
int defaultValue = map.getOrDefault("Mango", 0); // 0

// Checking
boolean hasKey = map.containsKey("Apple");
boolean hasValue = map.containsValue(20);

// Removing
map.remove("Banana");
map.remove("Orange", 15); // Remove only if value matches

// Iteration
for (String key : map.keySet()) {
    System.out.println(key + " = " + map.get(key));
}

for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}

// Java 8 forEach
map.forEach((key, value) -> System.out.println(key + " = " + value));

// Java 8 compute methods
map.putIfAbsent("Grapes", 30);
map.computeIfAbsent("Mango", k -> 40);
map.computeIfPresent("Apple", (k, v) -> v + 10);
map.merge("Apple", 5, (oldVal, newVal) -> oldVal + newVal);
```

### Internal Working
```java
// HashMap structure
class Node<K,V> {
    final int hash;
    final K key;
    V value;
    Node<K,V> next; // For handling collisions (chaining)
}

// Process:
// 1. Calculate hash: hash = key.hashCode()
// 2. Calculate index: index = hash & (n-1) where n = array length
// 3. Store at index, handle collisions with linked list (or tree in Java 8+)
```

**Key Parameters:**
- **Initial capacity**: 16
- **Load factor**: 0.75 (rehashing when 75% full)
- **Threshold**: capacity * load factor

### Interview Questions
**Q: How does HashMap work internally?**
1. Calculates hash of key using `hashCode()`
2. Finds bucket index using `hash & (n-1)`
3. Stores entry in bucket
4. Handles collisions using linked list (Java 7) or balanced tree (Java 8+ when >8 entries)
5. Retrieves using same hash calculation and `equals()` comparison

**Q: What happens when two keys have same hashCode?**
- Collision occurs
- Both entries stored in same bucket as linked list
- Retrieval uses `equals()` to find correct entry

**Q: Why is HashMap not thread-safe?**
- Multiple threads can modify simultaneously
- Can cause data inconsistency or infinite loop (in Java 7)
- Use `ConcurrentHashMap` for thread safety

---

## 4. HashSet

### Theory
- Stores unique elements only
- Backed by HashMap internally
- No ordering guarantee
- Allows one null value

### Examples
```java
HashSet<String> set = new HashSet<>();

// Adding
set.add("Apple");
set.add("Banana");
set.add("Apple"); // Duplicate, not added

// Checking
boolean contains = set.contains("Apple");
int size = set.size();

// Removing
set.remove("Banana");

// Iteration
for (String item : set) {
    System.out.println(item);
}

// Set operations
HashSet<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4));
HashSet<Integer> set2 = new HashSet<>(Arrays.asList(3, 4, 5, 6));

// Union
HashSet<Integer> union = new HashSet<>(set1);
union.addAll(set2); // [1, 2, 3, 4, 5, 6]

// Intersection
HashSet<Integer> intersection = new HashSet<>(set1);
intersection.retainAll(set2); // [3, 4]

// Difference
HashSet<Integer> difference = new HashSet<>(set1);
difference.removeAll(set2); // [1, 2]
```

---

## 5. TreeMap & TreeSet

### Theory
- **TreeMap**: Sorted map (Red-Black tree)
- **TreeSet**: Sorted set (backed by TreeMap)
- Elements sorted by natural ordering or custom Comparator
- No null keys (TreeMap), no null values (TreeSet)

### Examples
```java
// TreeMap - sorted by keys
TreeMap<String, Integer> treeMap = new TreeMap<>();
treeMap.put("Charlie", 30);
treeMap.put("Alice", 25);
treeMap.put("Bob", 28);

// Automatically sorted: Alice=25, Bob=28, Charlie=30

// TreeSet - sorted elements
TreeSet<Integer> treeSet = new TreeSet<>();
treeSet.add(50);
treeSet.add(20);
treeSet.add(40);
treeSet.add(10);

// Automatically sorted: [10, 20, 40, 50]

// Custom comparator
TreeSet<String> customSet = new TreeSet<>((a, b) -> b.compareTo(a)); // Reverse order
customSet.add("Apple");
customSet.add("Banana");
customSet.add("Cherry");
// Result: [Cherry, Banana, Apple]

// NavigableMap/Set operations
Integer first = treeSet.first(); // 10
Integer last = treeSet.last(); // 50
Integer lower = treeSet.lower(40); // 20 (greatest element < 40)
Integer higher = treeSet.higher(20); // 40 (smallest element > 20)
```

---

## 6. ConcurrentHashMap

### Theory
- Thread-safe alternative to HashMap
- Better performance than Hashtable
- Allows concurrent reads and limited concurrent writes
- No null keys or values

### Examples
```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// Thread-safe operations
map.put("Key1", 100);
map.putIfAbsent("Key2", 200);

// Atomic operations
map.compute("Key1", (k, v) -> v == null ? 1 : v + 1);
map.merge("Key1", 10, (oldVal, newVal) -> oldVal + newVal);

// Bulk operations (Java 8+)
map.forEach(1, (k, v) -> System.out.println(k + " = " + v));
Integer sum = map.reduceValues(1, (v1, v2) -> v1 + v2);
```

### Interview Questions
**Q: HashMap vs ConcurrentHashMap vs Hashtable?**

| Feature | HashMap | ConcurrentHashMap | Hashtable |
|---------|---------|-------------------|-----------|
| Thread-safe | ❌ | ✅ | ✅ |
| Null key/value | ✅ | ❌ | ❌ |
| Performance | Fast | Good | Slow |
| Locking | None | Segment-level | Method-level |
| Legacy | No | No | Yes (avoid) |

---

# String Handling

## 1. String Basics

### Theory
- **Immutable**: Once created, cannot be changed
- **String Pool**: JVM maintains pool of string literals for memory efficiency
- **Thread-safe**: Due to immutability

### Examples
```java
// String creation
String str1 = "Hello"; // String literal (stored in pool)
String str2 = "Hello"; // Points to same object in pool
String str3 = new String("Hello"); // New object in heap

// Comparison
System.out.println(str1 == str2); // true (same reference)
System.out.println(str1 == str3); // false (different objects)
System.out.println(str1.equals(str3)); // true (same content)

// Immutability demonstration
String original = "Java";
original.concat(" Programming"); // Creates new string, doesn't modify original
System.out.println(original); // Still "Java"

String modified = original.concat(" Programming");
System.out.println(modified); // "Java Programming"
```

### Common String Methods
```java
String str = "Hello World";

// Length and character access
int length = str.length(); // 11
char ch = str.charAt(0); // 'H'

// Substring
String sub = str.substring(0, 5); // "Hello"
String sub2 = str.substring(6); // "World"

// Case conversion
String upper = str.toUpperCase(); // "HELLO WORLD"
String lower = str.toLowerCase(); // "hello world"

// Trimming
String padded = "  Hello  ";
String trimmed = padded.trim(); // "Hello"
String stripped = padded.strip(); // "Hello" (Java 11+, handles Unicode)

// Searching
boolean contains = str.contains("World"); // true
boolean starts = str.startsWith("Hello"); // true
boolean ends = str.endsWith("World"); // true
int index = str.indexOf("World"); // 6
int lastIndex = str.lastIndexOf("o"); // 7

// Replacement
String replaced = str.replace("World", "Java"); // "Hello Java"
String regex = str.replaceAll("\\s+", "-"); // "Hello-World"

// Splitting
String csv = "Apple,Banana,Orange";
String[] fruits = csv.split(","); // ["Apple", "Banana", "Orange"]

// Joining (Java 8+)
String joined = String.join(", ", fruits); // "Apple, Banana, Orange"

// Comparison
int compare = "Apple".compareTo("Banana"); // Negative (Apple < Banana)
boolean equals = str.equals("Hello World"); // true
boolean equalsIgnoreCase = str.equalsIgnoreCase("hello world"); // true

// Empty check
boolean isEmpty = "".isEmpty(); // true
boolean isBlank = "  ".isBlank(); // true (Java 11+)

// Conversion
char[] chars = str.toCharArray();
byte[] bytes = str.getBytes();

// Formatting
String formatted = String.format("Name: %s, Age: %d", "John", 30);
```

---

## 2. StringBuilder

### Theory
- **Mutable**: Can be modified without creating new objects
- **Not thread-safe**: Faster than StringBuffer
- **Use case**: String manipulation in single-threaded environment

### Examples
```java
StringBuilder sb = new StringBuilder("Hello");

// Appending
sb.append(" World"); // "Hello World"
sb.append(" ").append(2024); // "Hello World 2024"

// Inserting
sb.insert(5, " Java"); // "Hello Java World 2024"

// Deleting
sb.delete(5, 10); // "Hello World 2024"
sb.deleteCharAt(5); // "HelloWorld 2024"

// Replacing
sb.replace(0, 5, "Hi"); // "Hi World 2024"

// Reversing
sb.reverse(); // "4202 dlroW iH"

// Capacity management
int capacity = sb.capacity(); // Default: 16 + string length
sb.ensureCapacity(50);

// Convert to String
String result = sb.toString();
```

---

## 3. StringBuffer

### Theory
- **Mutable**: Like StringBuilder
- **Thread-safe**: Synchronized methods
- **Use case**: String manipulation in multi-threaded environment

### Examples
```java
StringBuffer sbf = new StringBuffer("Thread");

// All StringBuilder methods available
sbf.append(" Safe");
sbf.insert(0, "Is ");
sbf.reverse();

// Thread-safe operations
synchronized (sbf) {
    sbf.append(" String");
}
```

---

## 4. String Comparison

### Interview Questions
**Q: String vs StringBuilder vs StringBuffer?**

| Feature | String | StringBuilder | StringBuffer |
|---------|--------|---------------|--------------|
| Mutability | Immutable | Mutable | Mutable |
| Thread-safe | Yes | No | Yes |
| Performance | Slow (for concat) | Fast | Moderate |
| Memory | More (new objects) | Less | Less |
| Use case | Fixed strings | Single-thread manipulation | Multi-thread manipulation |

**Q: Why is String immutable?**
1. **Security**: Prevents modification of sensitive data (passwords, URLs)
2. **Thread-safety**: No synchronization needed
3. **Caching**: String pool for memory efficiency
4. **HashCode caching**: Immutable hashcode for HashMap keys

---

# Exception Handling

## 1. Exception Hierarchy

```
Throwable
├── Error (Unchecked)
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── VirtualMachineError
└── Exception
    ├── RuntimeException (Unchecked)
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── ArithmeticException
    │   └── IllegalArgumentException
    └── Checked Exceptions
        ├── IOException
        ├── SQLException
        └── ClassNotFoundException
```

### Theory
- **Checked Exceptions**: Must be handled or declared (compile-time)
- **Unchecked Exceptions**: Runtime exceptions, optional handling
- **Errors**: Serious problems, usually not recoverable

---

## 2. Try-Catch-Finally

### Examples
```java
// Basic try-catch
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
}

// Multiple catch blocks
try {
    String str = null;
    System.out.println(str.length());
    int[] arr = new int[5];
    arr[10] = 50;
} catch (NullPointerException e) {
    System.out.println("Null pointer: " + e.getMessage());
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Array index: " + e.getMessage());
} catch (Exception e) {
    System.out.println("General exception: " + e.getMessage());
}

// Multi-catch (Java 7+)
try {
    // Code
} catch (IOException | SQLException e) {
    System.out.println("IO or SQL exception");
}

// Finally block (always executes)
try {
    // Code
} catch (Exception e) {
    // Handle
} finally {
    // Cleanup code (closes resources)
    System.out.println("Always executes");
}

// Try-with-resources (Java 7+)
try (FileReader fr = new FileReader("file.txt");
     BufferedReader br = new BufferedReader(fr)) {
    String line = br.readLine();
} catch (IOException e) {
    e.printStackTrace();
}
// Resources automatically closed
```

---

## 3. Throw vs Throws

### Examples
```java
// throw - to throw an exception explicitly
public void validateAge(int age) {
    if (age < 18) {
        throw new IllegalArgumentException("Age must be 18 or above");
    }
}

// throws - to declare exceptions in method signature
public void readFile(String path) throws IOException {
    FileReader fr = new FileReader(path);
    // Code
}

// Calling method with throws
public void processFile() {
    try {
        readFile("data.txt");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

---

## 4. Custom Exceptions

### Examples
```java
// Custom checked exception
class InvalidTestDataException extends Exception {
    public InvalidTestDataException(String message) {
        super(message);
    }
    
    public InvalidTestDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Custom unchecked exception
class ElementNotFoundException extends RuntimeException {
    private String locator;
    
    public ElementNotFoundException(String message, String locator) {
        super(message);
        this.locator = locator;
    }
    
    public String getLocator() {
        return locator;
    }
}

// Usage in automation framework
public WebElement findElement(String locator) {
    try {
        return driver.findElement(By.xpath(locator));
    } catch (NoSuchElementException e) {
        throw new ElementNotFoundException(
            "Element not found on page", locator);
    }
}
```

### Interview Questions
**Q: Checked vs Unchecked exceptions?**

| Checked | Unchecked |
|---------|-----------|
| Compile-time checking | Runtime checking |
| Must handle or declare | Optional handling |
| Extends Exception | Extends RuntimeException |
| Example: IOException | Example: NullPointerException |

---

# Multithreading & Concurrency

## 1. Thread Creation

### Examples
```java
// Method 1: Extending Thread class
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}

MyThread t1 = new MyThread();
t1.start();

// Method 2: Implementing Runnable interface
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable running: " + Thread.currentThread().getName());
    }
}

Thread t2 = new Thread(new MyRunnable());
t2.start();

// Method 3: Lambda expression (Java 8+)
Thread t3 = new Thread(() -> {
    System.out.println("Lambda thread: " + Thread.currentThread().getName());
});
t3.start();

// Method 4: Callable with ExecutorService
ExecutorService executor = Executors.newFixedThreadPool(2);
Callable<Integer> task = () -> {
    return 42;
};
Future<Integer> future = executor.submit(task);
Integer result = future.get(); // Blocks until result available
executor.shutdown();
```

---

## 2. Thread Lifecycle

```
NEW → RUNNABLE → RUNNING → TERMINATED
         ↓           ↓
      BLOCKED    WAITING/TIMED_WAITING
```

### Examples
```java
Thread thread = new Thread(() -> {
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
});

System.out.println(thread.getState()); // NEW
thread.start();
System.out.println(thread.getState()); // RUNNABLE
Thread.sleep(100);
System.out.println(thread.getState()); // TIMED_WAITING
thread.join(); // Wait for thread to complete
System.out.println(thread.getState()); // TERMINATED
```

---

## 3. Synchronization

### Theory
Prevents multiple threads from accessing shared resource simultaneously.

### Examples
```java
// Synchronized method
class Counter {
    private int count = 0;
    
    public synchronized void increment() {
        count++;
    }
    
    public synchronized int getCount() {
        return count;
    }
}

// Synchronized block
class BankAccount {
    private double balance = 0;
    
    public void deposit(double amount) {
        synchronized (this) {
            balance += amount;
        }
    }
}

// Static synchronization (class-level lock)
class Utility {
    private static int counter = 0;
    
    public static synchronized void incrementCounter() {
        counter++;
    }
}
```

---

## 4. ThreadLocal (Critical for Selenium)

### Theory
Provides thread-local variables - each thread has its own copy.

### Examples
```java
// WebDriver management in parallel execution
public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }
    
    public static WebDriver getDriver() {
        return driver.get();
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}

// Usage in parallel tests
@BeforeMethod
public void setup() {
    WebDriver driver = new ChromeDriver();
    DriverManager.setDriver(driver);
}

@Test
public void test1() {
    DriverManager.getDriver().get("https://example.com");
}

@AfterMethod
public void teardown() {
    DriverManager.quitDriver();
}
```

---

## 5. ExecutorService

### Examples
```java
// Fixed thread pool
ExecutorService executor = Executors.newFixedThreadPool(5);

for (int i = 0; i < 10; i++) {
    executor.submit(() -> {
        System.out.println("Task executed by: " + Thread.currentThread().getName());
    });
}

executor.shutdown();
executor.awaitTermination(1, TimeUnit.MINUTES);

// Cached thread pool (creates threads as needed)
ExecutorService cachedExecutor = Executors.newCachedThreadPool();

// Single thread executor
ExecutorService singleExecutor = Executors.newSingleThreadExecutor();

// Scheduled executor
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
scheduler.schedule(() -> System.out.println("Task"), 5, TimeUnit.SECONDS);
scheduler.scheduleAtFixedRate(() -> System.out.println("Periodic"), 0, 10, TimeUnit.SECONDS);
```

---

## 6. CompletableFuture (Java 8+)

### Examples
```java
// Asynchronous computation
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // Long-running task
    return "Result";
});

// Chaining operations
future.thenApply(result -> result.toUpperCase())
      .thenAccept(result -> System.out.println(result))
      .thenRun(() -> System.out.println("Completed"));

// Combining multiple futures
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");

CompletableFuture<String> combined = future1.thenCombine(future2, 
    (s1, s2) -> s1 + " " + s2);

System.out.println(combined.get()); // "Hello World"

// Exception handling
CompletableFuture.supplyAsync(() -> {
    if (true) throw new RuntimeException("Error");
    return "Success";
}).exceptionally(ex -> {
    return "Handled: " + ex.getMessage();
}).thenAccept(System.out::println);
```

---

# Java 8+ Features

## 1. Lambda Expressions

### Theory
Anonymous functions that enable functional programming.

### Syntax
```
(parameters) -> expression
(parameters) -> { statements; }
```

### Examples
```java
// No parameters
Runnable r = () -> System.out.println("Hello");

// Single parameter (parentheses optional)
Consumer<String> consumer = s -> System.out.println(s);
Consumer<String> consumer2 = (s) -> System.out.println(s);

// Multiple parameters
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;

// Multiple statements
Comparator<String> comparator = (s1, s2) -> {
    int result = s1.compareTo(s2);
    return result;
};

// Practical examples
List<String> names = Arrays.asList("John", "Jane", "Bob");

// Before Java 8
for (String name : names) {
    System.out.println(name);
}

// With lambda
names.forEach(name -> System.out.println(name));

// Sorting with lambda
names.sort((s1, s2) -> s1.compareTo(s2));
names.sort(String::compareTo); // Method reference
```

---

## 2. Functional Interfaces

### Theory
Interface with exactly one abstract method (can have default/static methods).

### Built-in Functional Interfaces
```java
// Predicate<T> - boolean test(T t)
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4)); // true

// Function<T, R> - R apply(T t)
Function<String, Integer> length = s -> s.length();
System.out.println(length.apply("Hello")); // 5

// Consumer<T> - void accept(T t)
Consumer<String> print = s -> System.out.println(s);
print.accept("Hello");

// Supplier<T> - T get()
Supplier<Double> random = () -> Math.random();
System.out.println(random.get());

// BiFunction<T, U, R> - R apply(T t, U u)
BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
System.out.println(multiply.apply(5, 3)); // 15

// Custom functional interface
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
    
    // Default method allowed
    default void display() {
        System.out.println("Calculator");
    }
}

Calculator add = (a, b) -> a + b;
Calculator subtract = (a, b) -> a - b;
```

---

## 3. Streams API

### Theory
Process collections in a functional style with operations like filter, map, reduce.

### Examples
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Filter - select elements
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());
// Result: [2, 4, 6, 8, 10]

// Map - transform elements
List<Integer> squares = numbers.stream()
    .map(n -> n * n)
    .collect(Collectors.toList());
// Result: [1, 4, 9, 16, 25, ...]

// Reduce - combine elements
int sum = numbers.stream()
    .reduce(0, (a, b) -> a + b);
// Result: 55

// Chaining operations
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

List<String> result = names.stream()
    .filter(name -> name.length() > 3)
    .map(String::toUpperCase)
    .sorted()
    .collect(Collectors.toList());
// Result: [ALICE, CHARLIE, DAVID]

// Count
long count = names.stream()
    .filter(name -> name.startsWith("A"))
    .count();

// Any/All/None match
boolean anyMatch = numbers.stream().anyMatch(n -> n > 5);
boolean allMatch = numbers.stream().allMatch(n -> n > 0);
boolean noneMatch = numbers.stream().noneMatch(n -> n < 0);

// Find first/any
Optional<Integer> first = numbers.stream()
    .filter(n -> n > 5)
    .findFirst();

// Distinct
List<Integer> duplicates = Arrays.asList(1, 2, 2, 3, 3, 4);
List<Integer> unique = duplicates.stream()
    .distinct()
    .collect(Collectors.toList());

// Limit and Skip
List<Integer> limited = numbers.stream()
    .limit(5)
    .collect(Collectors.toList());

List<Integer> skipped = numbers.stream()
    .skip(5)
    .collect(Collectors.toList());

// Sorting
List<String> sorted = names.stream()
    .sorted()
    .collect(Collectors.toList());

List<String> reverseSorted = names.stream()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());

// Grouping
Map<Integer, List<String>> groupedByLength = names.stream()
    .collect(Collectors.groupingBy(String::length));

// Partitioning
Map<Boolean, List<Integer>> partitioned = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n > 5));

// Joining strings
String joined = names.stream()
    .collect(Collectors.joining(", "));
// Result: "Alice, Bob, Charlie, David"

// Statistics
IntSummaryStatistics stats = numbers.stream()
    .mapToInt(Integer::intValue)
    .summaryStatistics();
System.out.println("Max: " + stats.getMax());
System.out.println("Min: " + stats.getMin());
System.out.println("Average: " + stats.getAverage());
```

---

## 4. Optional

### Theory
Container object to avoid NullPointerException.

### Examples
```java
// Creating Optional
Optional<String> optional = Optional.of("Hello");
Optional<String> nullable = Optional.ofNullable(null);
Optional<String> empty = Optional.empty();

// Checking presence
if (optional.isPresent()) {
    System.out.println(optional.get());
}

// Better approach with ifPresent
optional.ifPresent(value -> System.out.println(value));

// Default value
String value = nullable.orElse("Default");
String value2 = nullable.orElseGet(() -> "Computed Default");
String value3 = nullable.orElseThrow(() -> new RuntimeException("Value not present"));

// Transforming
Optional<Integer> length = optional.map(String::length);

// Filtering
Optional<String> filtered = optional.filter(s -> s.length() > 3);

// Chaining
String result = Optional.ofNullable(getUserName())
    .filter(name -> name.length() > 0)
    .map(String::toUpperCase)
    .orElse("ANONYMOUS");
```

---

## 5. Method References

### Theory
Shorthand for lambda expressions calling a specific method.

### Types
```java
// 1. Static method reference
// ClassName::staticMethod
Function<String, Integer> parse = Integer::parseInt;
int num = parse.apply("123");

// 2. Instance method reference of particular object
// instance::instanceMethod
String str = "Hello";
Supplier<Integer> length = str::length;

// 3. Instance method reference of arbitrary object
// ClassName::instanceMethod
Function<String, String> upper = String::toUpperCase;

// 4. Constructor reference
// ClassName::new
Supplier<ArrayList<String>> listSupplier = ArrayList::new;
ArrayList<String> list = listSupplier.get();

// Practical examples
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Lambda
names.forEach(name -> System.out.println(name));

// Method reference
names.forEach(System.out::println);

// Lambda
names.sort((s1, s2) -> s1.compareTo(s2));

// Method reference
names.sort(String::compareTo);
```

---

# Memory Management

## 1. Memory Areas

### Theory
```
JVM Memory
├── Heap (Objects, Instance variables)
│   ├── Young Generation
│   │   ├── Eden Space
│   │   └── Survivor Spaces (S0, S1)
│   └── Old Generation (Tenured)
├── Stack (Method calls, Local variables, References)
├── Method Area (Class metadata, Static variables)
└── PC Registers & Native Method Stack
```

---

## 2. Garbage Collection

### Theory
Automatic memory management - reclaims memory from unreferenced objects.

### Making Objects Eligible for GC
```java
// 1. Nullifying reference
Person p = new Person();
p = null; // Object eligible for GC

// 2. Reassigning reference
Person p1 = new Person();
p1 = new Person(); // First object eligible for GC

// 3. Object created inside method
public void createObject() {
    Person p = new Person();
} // p goes out of scope, object eligible for GC
```

---

## Interview Quick Reference

### Top 20 Java Interview Questions

1. **Explain JVM, JRE, and JDK**
2. **Difference between == and equals()**
3. **Why is String immutable?**
4. **HashMap internal working**
5. **ArrayList vs LinkedList**
6. **Interface vs Abstract class**
7. **Method overloading vs overriding**
8. **Checked vs Unchecked exceptions**
9. **final, finally, finalize**
10. **Static vs Non-static**
11. **Singleton design pattern**
12. **Multithreading - synchronized keyword**
13. **ThreadLocal usage**
14. **Java 8 features (Lambda, Streams, Optional)**
15. **Functional interfaces**
16. **Garbage collection**
17. **Memory leaks in Java**
18. **Immutable class creation**
19. **Serialization**
20. **Reflection API**

---

> [!TIP]
> **Interview Success Tips:**
> - Always explain with examples
> - Mention real-world use cases from automation
> - Discuss trade-offs and alternatives
> - Be ready to write code on whiteboard/IDE
> - Connect concepts to your framework design

**Good luck with your interview preparation!** 🚀
