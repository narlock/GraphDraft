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

## Future Work
- Add GitHub actions workflow on main branch that will build and run tests.
- Implement configuration setting that will automatically open user's favorite text editor with the draft GraphQL schema file opened.
