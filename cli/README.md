# GraphDraft CLI
![Node.js](https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=node.js&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

This is the command line interface for GraphDraft.

## Usage
```
node graphdraft.js <args>

Draft a GraphQL schema:
    </path/to/open_api_specification>       
        -model <model_name>                     Use a specific installed model.
        -outdir </path/to/output_directory/>    Specifiy the output directory of the drafted schema.
        -fname <file_name>                      Specify the file name of the drafted schema.

Show current GraphDraft CLI version:
    -version
```

### Examples

#### Draft a GraphQL schema
```
node graphdraft.js /path/to/open_api_specification.yaml
```
This will output the draft GraphQL schema to the current working directory.

#### Draft with specific model
```
node graphdraft.js /path/to/open_api_specification.yaml -model <model_name>
```
This will output the draft GraphQL schema to the current working directory.

#### Draft with specific output directory
```
node graphdraft.js /path/to/open_api_specification.yaml -output </path/to/output_directory/>
```
This will output the draft GraphQL schema to the specified output directory.

## Configuration
GraphDraft configuration can be added at `$HOME/Documents/narlock/GraphDraft/config.properties`. Options for configuration are:
- `default.model`: The default LLM to use when no model is specified. 
  - If this is not set, GraphDraft will query for the installed models on Ollama. It will look for a model that contains "code" in its name. If none is found with a name that contains "code", it will fallback to use the first model that it finds in the list of installed models.
- `default.outdir`: The default output directory to use when no output directory is specified.
  - If this is not set, GraphDraft will output the generated draft schema to the current working directory.
- `default.filename`: The default file name of the generated draft schema.
  - If this is not set, GraphDraft will output the generated draft schema to `schema.graphqls`.