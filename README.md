# CustomSecurity

Points to note:
<ul>
  <li>Run the project</li>
  <li>Open POSTMAN or use cUrl</li>
  <li>POST request to http://localhost:8080/authenticate with username (erik) and password (magneto). Refer AppUserRepo for more credential</li>
  <li>Server return the token, copy the token</li>
  <li>In POSTMAN/cUrl, GET request "http://localhost:8080/products/all" with header "Authorization":copied token</li>
</ul>
