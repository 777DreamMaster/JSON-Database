# JSON-Database

## Details 
JSON database is a single file database that stores information in the form of JSON. Program uses java sockets to get a jsons from clients. JSON-files are stored on server. Server is parallelized, so that every request is parsed and handled in a separate executor's task.  
The user can use the set, get, or, delete commands.  

There are available client arguments:
- -t - type of server operation - get, set, delete, exit
- -k - key for json storing
- -v - values of json
- -in - read json with properties from file

## Example

```
> java Main -t set -k 1 -v "Hello world!" 
Client started!
Sent: {"type":"set","key":"1","value":"Hello world!"}
Received: {"response":"OK"}
```
```
> java Main -in setFile.json 
Client started!
Sent:
{
   "type":"set",
   "key":"person",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster",
         "year":"2018"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"87"
      }
   }
}
Received: {"response":"OK"}
```
```
> java Main -in getFile.json 
Client started!
Sent: {"type":"get","key":["person","name"]}
Received: {"response":"OK","value":"Elon Musk"}
```
```
> java Main -in updateFile.json 
Client started!
Sent: {"type":"set","key":["person","rocket","launches"],"value":"88"}
Received: {"response":"OK"}
```
```
> java Main -in secondGetFile.json 
Client started!
Sent: {"type":"get","key":["person"]}
Received:
{
   "response":"OK",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster",
         "year":"2018"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"88"
      }
   }
}
```
```
> java Main -in deleteFile.json 
Client started!
Sent: {"type":"delete","key":["person","car","year"]}
Received: {"response":"OK"}
```
```
> java Main -in secondGetFile.json 
Client started!
Sent: {"type":"get","key":["person"]}
Received:
{
   "response":"OK",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"88"
      }
   }
}
```
```
> java Main -t exit 
Client started!
Sent: {"type":"exit"}
Received: {"response":"OK"}
```
