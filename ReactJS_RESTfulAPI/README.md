## Back-end and Front-end Applications 
* Run the Springboot application. It will run at http://localhost:8080/reactjs_restful/
* Open the React application from command prompt and enter "npm start". It will run on http://localhost:3000/

## Authentication and CORS
Client sends POST request with ``header[Authorization]="Basic base64encode(username + ':' + password)"`` to the Server with url
'/authorize'. Because the request originated from different domain, the browser first sends a ``pre-flight [OPTIONS]`` request to
the Server. Server has been configured in such a way that it by-passes authentication for ``pre-flight [OPTIONS]`` request. It makes
response to that request by attaching CORS to that response. Browser checks whether the Server will accept the POST request
or not by reading the CORS policy, and upon confirmation, browser sends the POST request to authorize the user. If user is authenticated,
server returns ``JWTResponse = {access_token: '', refresh_token: '', userId: '', role: ''}``
Else, server returns response with status ```'401 Unauthorized'``` and message
``{code: 401, status: 'UNAUTHORIZED', timestamp: '', message: ''}``

## Authorization
When client makes requests to access the resources, it must attach ``header[Authorization]="Bearer access_token"`` to the requests.
If token has expired, server returns response with status ``'401 Unauthorized'`` and message
``{code: 401, status: 'UNAUTHORIZED', timestamp: '', message: ''}``

## Expired access_token 
If access_token expires, client must send request to the server with ``header[Authorization]="Bearer refresh_token"`` at path ``'/renew_access_token'``
Because the request has originated from a different domain, browser again sends a ``pre-flight [OPTIONS]`` request to the Server.
Server has been configured in such a way that it does not require authentication for ``pre-flight [OPTIONS]`` request. It makes
response to that request by attaching CORS in that response. Browser checks whether the Server will accept the POST request
or not by reading the CORS policy, and if it matches, browser sends the POST request to renew access_token.
Once server receives the request, it checks for expiration of refresh_token and then generates new access_token with same
expiration time as previous access_token and returns JWTResponse.

```bash
JWTResponse = {
	access_token: 'new_token', refresh_token: 'old_token', userId: 'old_id', role: 'old_role'
}
```

Client updates the user object from localStorage and can send requests to the server using this new access_token.
Refresh_token once expired cannot be renewed. Client must send user-credentials again to authorize the user.

Interceptors intercepts every requests send from the client to the server and depending upon the request,
``header[Authorization]="Bearer access_token"`` or 
``header[Authorization]="Bearer refresh_token"`` is added to the request.