# Tricentis Tosca - Visual Mindmaps

This document contains visual representations of Tosca Architecture, Workflows, and key concepts using Mermaid diagrams.

## 1. Tosca Architecture & Workspaces

```mermaid
graph TD
    User["User"] -->|Opens| TC[Tosca Commander]
    TC -->|Connects to| W[Workspace]
    
    subgraph "Workspace Types"
        SW[Single User Workspace]
        MW[Multi-User Workspace]
    end
    
    W -.-> SW
    W -.-> MW
    
    MW -->|Check-in / Check-out| CR[(Common Repository)]
    CR -->|Stores| Artifacts[Modules, TestCases, ExecutionLists]
    
    subgraph "Database Support"
        SQL[MS SQL Server]
        ORA[Oracle]
        SL[SQLite]
        DB2[DB2]
    end
    
    CR -.-> SQL
    CR -.-> ORA
    CR -.-> SL
    CR -.-> DB2
```

## 2. UI Automation Workflow

```mermaid
sequenceDiagram
    participant User
    participant App as Application Under Test
    participant XScan as Tosca XScan
    participant Module as Tosca Module
    participant TC as TestCase
    participant EL as ExecutionList

    User->>TC: Create Folder Structure
    User->>XScan: Right-click -> Scan -> Application
    XScan->>App: Identify Elements (Properties/Anchor)
    XScan-->>Module: Save & Close (Creates Module)
    User->>TC: Drag & Drop Module to Test Case
    TC->>TC: Enter TestStepValues (Input/Verify)
    User->>EL: Drag & Drop TestCase to ExecutionList
    EL->>App: Run (Executes Steps)
    App-->>EL: Return Result (Pass/Fail)
```

## 3. API Testing Workflow

```mermaid
flowchart LR
    Start([Start]) --> APIScan[Open API Scan]
    APIScan -->|Import| Def[Definition (URL/File)]
    Def --> Config[Configure Request]
    Config -->|Method, Auth, Payload| Send[Send Request]
    Send --> Verify{Verify Response?}
    Verify -- No --> Config
    Verify -- Yes --> Export[Export to Tosca]
    Export --> TC[Tosca Commander]
    
    subgraph "Tosca Commander"
        TC -->|Creates| APIMod[API Module]
        TC -->|Creates| APITC[API TestCase]
    end
    
    APITC -->|Add| Verifications[Verify Status/Body]
    APITC -->|Run| Exec[Execution]
```

## 4. Distributed Execution (DEX) Flow

```mermaid
graph TD
    Tester[Tester/CI] -->|Triggers Execution| TC[Tosca Commander / CI Client]
    TC -->|Sends ExecutionList| DS[Distribution Server]
    
    subgraph "Distribution Agents (DEX Agents)"
        A1[Agent 1 (Chrome)]
        A2[Agent 2 (Firefox)]
        A3[Agent 3 (API)]
    end
    
    DS -->|Distributes Tests| A1
    DS -->|Distributes Tests| A2
    DS -->|Distributes Tests| A3
    
    A1 -->|Updates Result| CR[(Common Repository)]
    A2 -->|Updates Result| CR
    A3 -->|Updates Result| CR
    
    CR -->|Syncs| TC
    TC -->|Shows| Results[Pass/Fail Report]
```

## 5. Identifying Objects Strategy

```mermaid
mindmap
  root((Object Identification))
    Properties
      ID
      Name
      InnerText
      ClassName
    Anchor
      Relative to Parent
      Relative to Sibling
    Image
      Pixel Matching
      Accuracy Adjustment
    Index
      Order in DOM
      (Least Stable)
```
