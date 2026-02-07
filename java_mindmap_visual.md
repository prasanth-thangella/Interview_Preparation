# Java Core Concepts - Visual Mind Map
## Quick Reference for Interview Preparation

> [!TIP]
> Use this visual mind map to quickly recall Java concepts during interview preparation. Each section is color-coded and organized hierarchically for easy memory recall.

---

## Complete Java Mind Map

```mermaid
mindmap
  root((Java Core
    Concepts))
    Java Basics
      Data Types
        Primitive Types
          byte short int long
          float double
          char boolean
        Reference Types
          Classes
          Interfaces
          Arrays
        Wrapper Classes
          Integer Double
          Character Boolean
          Autoboxing Unboxing
      Access Modifiers
        public
        protected
        default package private
        private
      Keywords
        static Shared across instances
        final Constant immutable
        this Current object
        super Parent class
      Type Casting
        Widening Implicit
        Narrowing Explicit
    
    OOP Principles
      Encapsulation
        Data Hiding
        Private Variables
        Public Getters Setters
        Controlled Access
      Inheritance
        extends keyword
        super constructor
        IS-A Relationship
        Code Reusability
        Single Inheritance
        Multilevel Inheritance
        Hierarchical Inheritance
      Polymorphism
        Method Overloading
          Compile-time
          Same name different params
          Return type can vary
        Method Overriding
          Runtime
          Same signature
          @Override annotation
          Dynamic binding
      Abstraction
        Abstract Class
          abstract methods
          Concrete methods
          Constructor allowed
          Instance variables
        Interface
          All methods abstract
          public static final
          Multiple inheritance
          default methods Java 8
          static methods Java 8
    
    Collections Framework
      List Interface
        ArrayList
          Dynamic array
          Fast random access
          Slow insertion deletion
          Not synchronized
          Initial capacity 16
        LinkedList
          Doubly linked list
          Fast insertion deletion
          Slow random access
          Implements Deque
        Vector
          Synchronized ArrayList
          Legacy class
          Stack extends Vector
      Set Interface
        HashSet
          No duplicates
          Backed by HashMap
          No ordering
          One null allowed
        LinkedHashSet
          Insertion order
          Doubly linked list
        TreeSet
          Sorted order
          Red-Black tree
          No null values
          NavigableSet
      Map Interface
        HashMap
          Key-value pairs
          One null key
          Multiple null values
          Not synchronized
          O 1 access
          Load factor 0.75
          Initial capacity 16
        LinkedHashMap
          Insertion order
          Access order option
        TreeMap
          Sorted by keys
          Red-Black tree
          No null keys
        Hashtable
          Synchronized
          Legacy
          No null key value
        ConcurrentHashMap
          Thread-safe
          Segment locking
          No null key value
          Better than Hashtable
      Queue Interface
        PriorityQueue
          Heap based
          Natural ordering
        ArrayDeque
          Resizable array
          No null elements
    
    String Handling
      String Class
        Immutable
        String Pool
        Thread-safe
        Final class
      String Methods
        length charAt
        substring
        toUpperCase toLowerCase
        trim strip
        contains startsWith endsWith
        indexOf lastIndexOf
        replace replaceAll
        split join
        equals compareTo
      StringBuilder
        Mutable
        Not thread-safe
        Fast performance
        append insert delete
        reverse
      StringBuffer
        Mutable
        Thread-safe
        Synchronized
        Slower than StringBuilder
      Comparison
        == Reference equality
        equals Content equality
        compareTo Lexicographic
    
    Exception Handling
      Exception Hierarchy
        Throwable
          Error
            OutOfMemoryError
            StackOverflowError
          Exception
            Checked Exceptions
              IOException
              SQLException
              ClassNotFoundException
            Unchecked RuntimeException
              NullPointerException
              ArrayIndexOutOfBoundsException
              ArithmeticException
              IllegalArgumentException
      Try-Catch-Finally
        try block
        catch Multiple catches
        finally Always executes
        try-with-resources
      Throw vs Throws
        throw Explicit exception
        throws Method signature
      Custom Exceptions
        Extend Exception
        Extend RuntimeException
        Constructor with message
    
    Multithreading
      Thread Creation
        Extend Thread class
        Implement Runnable
        Lambda expression
        Callable Future
      Thread Lifecycle
        NEW
        RUNNABLE
        RUNNING
        BLOCKED
        WAITING TIMED_WAITING
        TERMINATED
      Synchronization
        synchronized method
        synchronized block
        Static synchronization
        Prevents race condition
      ThreadLocal
        Thread-local variables
        Each thread own copy
        WebDriver management
        set get remove
      ExecutorService
        newFixedThreadPool
        newCachedThreadPool
        newSingleThreadExecutor
        newScheduledThreadPool
        submit shutdown
      CompletableFuture
        Asynchronous computation
        supplyAsync
        thenApply thenAccept
        thenCombine
        exceptionally
    
    Java 8 Plus Features
      Lambda Expressions
        Anonymous functions
        Functional programming
        Syntax params arrow body
        Shorter code
      Functional Interfaces
        Single abstract method
        @FunctionalInterface
        Predicate test
        Function apply
        Consumer accept
        Supplier get
        BiFunction
      Streams API
        filter
        map
        reduce
        collect
        forEach
        sorted
        distinct
        limit skip
        anyMatch allMatch noneMatch
        findFirst findAny
        Collectors
          toList toSet toMap
          groupingBy
          partitioningBy
          joining
      Optional
        Avoid NullPointerException
        of ofNullable empty
        isPresent ifPresent
        orElse orElseGet
        orElseThrow
        map filter
      Method References
        Static ClassName staticMethod
        Instance object instanceMethod
        Arbitrary ClassName instanceMethod
        Constructor ClassName new
      Default Methods
        Interface default methods
        Backward compatibility
        Multiple inheritance
      Date Time API
        LocalDate
        LocalTime
        LocalDateTime
        ZonedDateTime
        Period Duration
        DateTimeFormatter
    
    Memory Management
      Memory Areas
        Heap
          Young Generation
            Eden Space
            Survivor S0 S1
          Old Generation Tenured
        Stack
          Method calls
          Local variables
          References
        Method Area
          Class metadata
          Static variables
        PC Registers
        Native Method Stack
      Garbage Collection
        Automatic memory management
        Unreferenced objects
        System.gc
        finalize deprecated
        Eligibility
          Nullifying reference
          Reassigning reference
          Out of scope
      Memory Leaks
        Unclosed resources
        Static collections
        Listeners not removed
        ThreadLocal not cleaned
```

---

## Quick Comparison Tables

### Collections Comparison

```mermaid
graph TD
    A[Need to store elements?] -->|Unique elements| B[Set]
    A -->|Duplicates allowed| C[List]
    A -->|Key-Value pairs| D[Map]
    
    B -->|No ordering| E[HashSet]
    B -->|Insertion order| F[LinkedHashSet]
    B -->|Sorted order| G[TreeSet]
    
    C -->|Fast access| H[ArrayList]
    C -->|Fast insert/delete| I[LinkedList]
    C -->|Thread-safe| J[Vector]
    
    D -->|No ordering| K[HashMap]
    D -->|Insertion order| L[LinkedHashMap]
    D -->|Sorted keys| M[TreeMap]
    D -->|Thread-safe| N[ConcurrentHashMap]
    
    style E fill:#90EE90
    style F fill:#87CEEB
    style G fill:#FFB6C1
    style H fill:#FFD700
    style I fill:#FF6347
    style J fill:#DDA0DD
    style K fill:#F0E68C
    style L fill:#98FB98
    style M fill:#FFA07A
    style N fill:#20B2AA
```

---

## Java 8 Features Flow

```mermaid
graph LR
    A[Java 8+ Features] --> B[Lambda Expressions]
    A --> C[Streams API]
    A --> D[Optional]
    A --> E[Method References]
    A --> F[Default Methods]
    
    B --> B1[Functional Programming]
    B --> B2[Concise Code]
    
    C --> C1[filter]
    C --> C2[map]
    C --> C3[reduce]
    C --> C4[collect]
    
    D --> D1[Avoid NPE]
    D --> D2[orElse]
    D --> D3[ifPresent]
    
    E --> E1[Static Method]
    E --> E2[Instance Method]
    E --> E3[Constructor]
    
    F --> F1[Interface Evolution]
    F --> F2[Backward Compatibility]
    
    style A fill:#FF6B6B
    style B fill:#4ECDC4
    style C fill:#45B7D1
    style D fill:#FFA07A
    style E fill:#98D8C8
    style F fill:#F7DC6F
```

---

## Exception Handling Flow

```mermaid
graph TD
    A[Exception Occurs] --> B{Checked or Unchecked?}
    B -->|Checked| C[Must handle or declare]
    B -->|Unchecked| D[Optional handling]
    
    C --> E[try-catch]
    C --> F[throws in signature]
    
    D --> G[RuntimeException]
    
    E --> H[catch block]
    H --> I[Handle exception]
    
    E --> J[finally block]
    J --> K[Always executes]
    
    F --> L[Caller handles]
    
    style A fill:#FF6B6B
    style C fill:#FFA07A
    style D fill:#FFD93D
    style E fill:#6BCF7F
    style J fill:#4ECDC4
```

---

## Thread Lifecycle Diagram

```mermaid
stateDiagram-v2
    [*] --> NEW: Thread created
    NEW --> RUNNABLE: start() called
    RUNNABLE --> RUNNING: Scheduler picks
    RUNNING --> TERMINATED: run() completes
    RUNNING --> BLOCKED: Waiting for lock
    RUNNING --> WAITING: wait() called
    RUNNING --> TIMED_WAITING: sleep() called
    BLOCKED --> RUNNABLE: Lock acquired
    WAITING --> RUNNABLE: notify() called
    TIMED_WAITING --> RUNNABLE: Time expires
    TERMINATED --> [*]
```

---

## HashMap Internal Working

```mermaid
graph TD
    A[Put key, value] --> B[Calculate hashCode]
    B --> C[Calculate index: hash & n-1]
    C --> D{Bucket empty?}
    D -->|Yes| E[Store entry]
    D -->|No| F{Collision!}
    F --> G[Compare keys with equals]
    G -->|Same key| H[Update value]
    G -->|Different key| I[Add to linked list/tree]
    
    style A fill:#4ECDC4
    style B fill:#45B7D1
    style C fill:#96CEB4
    style E fill:#90EE90
    style H fill:#FFD93D
    style I fill:#FFA07A
```

---

## OOP Principles Visual

```mermaid
graph TB
    OOP[OOP Principles] --> ENC[Encapsulation]
    OOP --> INH[Inheritance]
    OOP --> POLY[Polymorphism]
    OOP --> ABS[Abstraction]
    
    ENC --> ENC1[Data Hiding]
    ENC --> ENC2[Getters/Setters]
    ENC --> ENC3[Access Modifiers]
    
    INH --> INH1[Code Reusability]
    INH --> INH2[IS-A Relationship]
    INH --> INH3[extends keyword]
    
    POLY --> POLY1[Overloading<br/>Compile-time]
    POLY --> POLY2[Overriding<br/>Runtime]
    
    ABS --> ABS1[Abstract Class]
    ABS --> ABS2[Interface]
    
    style OOP fill:#FF6B6B
    style ENC fill:#4ECDC4
    style INH fill:#45B7D1
    style POLY fill:#FFA07A
    style ABS fill:#F7DC6F
```

---

## Memory Management Visual

```mermaid
graph TB
    JVM[JVM Memory] --> HEAP[Heap Memory]
    JVM --> STACK[Stack Memory]
    JVM --> METHOD[Method Area]
    
    HEAP --> YOUNG[Young Generation]
    HEAP --> OLD[Old Generation]
    
    YOUNG --> EDEN[Eden Space]
    YOUNG --> S0[Survivor S0]
    YOUNG --> S1[Survivor S1]
    
    STACK --> FRAME[Stack Frames]
    FRAME --> LOCAL[Local Variables]
    FRAME --> REF[Object References]
    
    METHOD --> META[Class Metadata]
    METHOD --> STATIC[Static Variables]
    
    style JVM fill:#FF6B6B
    style HEAP fill:#4ECDC4
    style STACK fill:#45B7D1
    style METHOD fill:#FFA07A
    style YOUNG fill:#90EE90
    style OLD fill:#FFD93D
```

---

## String Immutability Concept

```mermaid
graph LR
    A[String str1 = Hello] --> B[String Pool]
    C[String str2 = Hello] --> B
    D[String str3 = new String Hello] --> E[Heap Memory]
    
    B --> F[Single Hello object]
    E --> G[New Hello object]
    
    H[str1.concat World] --> I[New Hello World object]
    A -.->|Still points to| F
    
    style B fill:#90EE90
    style E fill:#FFD93D
    style F fill:#4ECDC4
    style G fill:#FFA07A
    style I fill:#FF6B6B
```

---

## Quick Reference Cheat Sheet

### ArrayList vs LinkedList
| Feature | ArrayList | LinkedList |
|---------|-----------|------------|
| Structure | Dynamic array | Doubly linked list |
| Access | O(1) | O(n) |
| Insert/Delete | O(n) | O(1) |
| Memory | Less overhead | More (node pointers) |
| Use when | Frequent access | Frequent add/remove |

### HashMap vs ConcurrentHashMap vs Hashtable
| Feature | HashMap | ConcurrentHashMap | Hashtable |
|---------|---------|-------------------|-----------|
| Thread-safe | ❌ | ✅ | ✅ |
| Null key/value | ✅ | ❌ | ❌ |
| Performance | Fast | Good | Slow |
| Locking | None | Segment-level | Method-level |

### String vs StringBuilder vs StringBuffer
| Feature | String | StringBuilder | StringBuffer |
|---------|--------|---------------|--------------|
| Mutability | Immutable | Mutable | Mutable |
| Thread-safe | ✅ | ❌ | ✅ |
| Performance | Slow (concat) | Fast | Moderate |
| Use when | Fixed text | Single-thread | Multi-thread |

### Checked vs Unchecked Exceptions
| Checked | Unchecked |
|---------|-----------|
| Compile-time | Runtime |
| Must handle/declare | Optional |
| Extends Exception | Extends RuntimeException |
| IOException, SQLException | NullPointerException, ArithmeticException |

### Method Overloading vs Overriding
| Overloading | Overriding |
|-------------|------------|
| Compile-time polymorphism | Runtime polymorphism |
| Same class | Different classes (inheritance) |
| Different parameters | Same signature |
| Return type can vary | Return type same/covariant |

---

## Interview Memory Tricks

### Remember Collections Hierarchy
**"List Set Queue Map"** - Main interfaces

**ArrayList LinkedList Vector** - List implementations  
**HashSet LinkedHashSet TreeSet** - Set implementations  
**HashMap LinkedHashMap TreeMap** - Map implementations

### Remember OOP Principles
**"APIE"** - Abstraction, Polymorphism, Inheritance, Encapsulation

### Remember Java 8 Features
**"LOSMD"** - Lambda, Optional, Streams, Method references, Default methods

### Remember Access Modifiers (Most to Least restrictive)
**"PPPP"** - Private, Package (default), Protected, Public

### Remember Thread States
**"NEW RUN BLOCK WAIT TERM"** - NEW, RUNNABLE, BLOCKED, WAITING, TERMINATED

---

## Code Pattern Templates

### Singleton Pattern (Thread-safe)
```java
public class Singleton {
    private static volatile Singleton instance;
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

### Immutable Class
```java
public final class Immutable {
    private final String name;
    private final List<String> items;
    
    public Immutable(String name, List<String> items) {
        this.name = name;
        this.items = new ArrayList<>(items); // Defensive copy
    }
    
    public String getName() { return name; }
    public List<String> getItems() {
        return new ArrayList<>(items); // Return copy
    }
}
```

### ThreadLocal for WebDriver
```java
public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static void setDriver(WebDriver d) {
        driver.set(d);
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
```

---

## Top 10 Most Asked Interview Questions

1. **Explain HashMap internal working**
   - Hash calculation → Index calculation → Bucket storage → Collision handling

2. **Why is String immutable?**
   - Security, Thread-safety, Caching (String pool), HashCode caching

3. **ArrayList vs LinkedList - when to use which?**
   - ArrayList: Frequent access, LinkedList: Frequent insertion/deletion

4. **Difference between == and equals()?**
   - ==: Reference comparison, equals(): Content comparison

5. **What is ThreadLocal and its use in Selenium?**
   - Thread-local variables, WebDriver management in parallel execution

6. **Explain Java 8 Stream API with example**
   - filter, map, reduce, collect operations on collections

7. **Abstract class vs Interface?**
   - Abstract: Partial implementation, Interface: Contract only (before Java 8)

8. **How does garbage collection work?**
   - Automatic memory management, unreferenced objects reclaimed

9. **Checked vs Unchecked exceptions?**
   - Checked: Compile-time, must handle, Unchecked: Runtime, optional

10. **What is polymorphism? Types?**
    - Compile-time (overloading), Runtime (overriding)

---

> [!IMPORTANT]
> **Study Strategy:**
> 1. Review this mind map daily
> 2. Practice code examples from the main guide
> 3. Explain concepts aloud without looking
> 4. Draw diagrams on whiteboard
> 5. Connect concepts to your automation framework

**Master these visuals and you'll ace the Java interview!** 🎯
