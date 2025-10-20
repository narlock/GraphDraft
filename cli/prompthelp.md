# Prompt process for generating GraphQL schema

All chunks will contain:
```json
{
    "role": "system",
    "content": "TODO"
}
```


## Step 1: Components
The first thing that will be done is the conversion of just the components to GraphQL types, inputs, and enums. This will typically be done with the schemas section. We will send just the components.schema chunk to the AI with the prompt:

```json
[
    {
        "role": "system",
        "content": ""
    },
    {
        "role": "user",
        "content": ""
    }
]
```

With the components, we will store the response within memory as it will be reused in the next portions of the conversion.

## Step 2: Endpoint conversion
We will do EACH endpoint ("/users/" would be an endpoint, including the GET, POST, PUT, DELETE, etc., it is usually under a "path"). These will convert one at a time. From here, we will store in memory each portion of the GraphQL schema. Then, we will send all of the parts (types, inputs, queries, and mutations) of the schema to get back a fully completed GraphQL schema. This schema will be written to a schema.graphqls file.

### For each of the paths:
```json
    {
        "role": "system",
        "content": ""
    },
    {
        "role": "assistant",
        "content": ""
    },
    {
        "role": "user",
        "content": ""
    }
]
```

### For final merge:
```json
[
    {
        "role": "system",
        "content": ""
    },
    {
        "role": "user",
        "content": ""
    }
]
```

## Step 3: Validation
We can use buildSchema and validateSchema from require('graphql') to validate the schema.
