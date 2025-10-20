# GraphDraft

### Frontend

![Electron](https://img.shields.io/badge/Electron-47848F?style=for-the-badge&logo=electron&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![Node.js](https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=node.js&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white)

### Backend
![GraphQL](https://img.shields.io/badge/GraphQL-E10098?style=for-the-badge&logo=graphql&logoColor=white)
![Java](https://img.shields.io/badge/Java-F58025?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

### Integrations
![Ollama](https://img.shields.io/badge/Ollama-000000?style=for-the-badge&logo=ollama&logoColor=white)

## Description
Local AI agent that generates a draft GraphQL schema from an OpenAPI specification.

### Usage Details
GraphDraft can be interfaced using:
1. Web interface (web browser required)
2. Desktop interface (via Electron)
3. Command line interface

Each of these interfaces work similarly. The user will import their OpenAPI specification. Afterwards, a visual interface will be displayed that shows the progression of the AI agent converting from OpenAPI specification to GraphQL schema.

### Installation Details

#### Requirements
1. Ollama with an installed LLM
2. Java 17
3. Node.js 18

#### Running
1. Clone GraphDraft repository
2. Give permission to run `start.sh` script (`chmod +x start.sh`)
3. Run `start.sh` script
    - This will run an Ollama integration layer on port 8080 (configurable if port is used elsewhere)
    - This will run the React web interface on port 3000 (configurable if port is used elsewhere)

#### Notes
- For use of the Desktop Electron interface, navigate to the [releases]() tab on GitHub and download and install the version that corresponds to your operating system.
- For use of the CLI, refer to the [CLI README](/cli/README.md).

## Prompt process for generating GraphQL schema

All chunks for generating the schema will contain:
```json
{
    "role": "system",
    "content": "You are an expert in API design and GraphQL schema generation. Your job is to convert OpenAPI sections (in YAML or JSON) into equivalent GraphQL schema definitions. Follow these rules: 1. OpenAPI schemas represent GraphQL type, input, or enum definitions. 2. An HTTP GET request represents a GraphQL Query. 3. An HTTP POST/PUT/PATCH/DELETE represents a GraphQL Mutation. 4. OpenAPI component parameters represent GraphQL field arguments. 5. OpenAPI component responses represent GraphQL schema return types. 6. Use PascalCase for types, camelCase for fields. 7. Non-null OpenAPI fields will be non-nullable in GraphQL with an exclaimation mark. 8. Output only valid GraphQL SDL, no explanations."
}
```


### Step 1: Components
The first thing that will be done is the conversion of just the components to GraphQL types, inputs, and enums. This will typically be done with the schemas section. We will send just the components chunk to the AI with the prompt:

```json
[
    {
        "role": "user",
        "content": "Convert this OpenAPI components section into GraphQL types: <components portion of OpenAPI specification>"
    }
]
```

With the components, we will store the response within memory as it will be reused in the next portions of the conversion.

### Step 2: Endpoint conversion
We will do EACH endpoint ("/users/" would be an endpoint, including the GET, POST, PUT, DELETE, etc., it is usually under a "path"). These will convert one at a time. From here, we will store in memory each portion of the GraphQL schema. Then, we will send all of the parts (types, inputs, queries, and mutations) of the schema to get back a fully completed GraphQL schema. This schema will be written to a schema.graphqls file.

#### For each of the paths:
```json
    {
        "role": "assistant",
        "content": "Previously defined GraphQL schema: <Contents of currently converted GraphQL schema>"
    },
    {
        "role": "user",
        "content": "Continue generating GraphQL schemas from OpenAPI paths. Maintain consistency with previously defined types and queries. Convert this OpenAPI path section into GraphQL queries/mutations: <OpenAPI path chunk>"
    }
]
```

#### For final merge:
```json
[
    {
        "role": "system",
        "content": "You are a GraphQL schema auditor. Merge and harmonize the following GraphQL schema fragments into one valid, consistent schema. Remove duplicates, ensure types match, and merge Query and Mutation definitions cleanly."
    },
    {
        "role": "user",
        "content": "Here are all of the GraphQL schema fragments: <Contents of all converted GraphQL schema fragments>"
    }
]
```

### Step 3: Validation
We can use `buildSchema` and `validateSchema` from `require('graphql')` to validate the schema.

## Future Work
- Add GitHub actions workflow on main branch that will build and run tests.
- Implement configuration setting that will automatically open user's favorite text editor with the draft GraphQL schema file opened.
