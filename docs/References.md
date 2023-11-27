# References

- [References](#references)
  - [Logger](#logger)
    - [info](#info)
    - [error](#error)
  - [SimpleStore](#simplestore)
    - [bind](#bind)
    - [getState](#getstate)
  - [Http](#http)
    - [createGetReq](#creategetreq)
    - [sendJSON](#sendjson)

## <a name="logger">Logger</a>
### info
``` java
Logger.info('App', 'Hello', 'World', '!')
```
output
```
[App][info]Helloworld!
```

### error
``` java
Logger.error('App', 'Error', 'Info', '!')
```
output
```
[App][info]ErrorInfo!
```

## <a name="simple_store">SimpleStore</a>

### bind
``` java
ArrayList<State> stateList = new ArrayList<State>() {
  {
    add(new SampleState());
    add(new SampleState2());
  }
};
Store.bind(stateList);
```

### getState
``` java
SampleState state = Store.getState(SampleState.class);
```


## <a name="http">Http</a>
### createGetReq
``` java
String response = HttpWrapper.createGetReq(url)
```

### sendJSON
``` java
String body = "{\"msg\": \"Hello\"}"
String response = HttpWrapper.sendJSON(body, url)
```
