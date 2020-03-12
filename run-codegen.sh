java -jar swagger-codegen-cli.jar generate \
  -i http://54.38.242.184:8080/v2/api-docs \
  -l java --library=retrofit \
  -D hideGenerationTimestamp=true \
  -o campusincident/app 
