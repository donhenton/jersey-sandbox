

Swagger api-docs: http://localhost:8090/jersey-sandbox/

Wadl: http://localhost:8090/jersey-sandbox/webapi/application.wadl

Schema: http://localhost:8090/jersey-sandbox/webapi/application.wadl/xsd0.xsd

Demo Service: http://localhost:8090/jersey-sandbox/webapi/demo/model


Jersey uses JUL, and so doesn't play nicely with SLF4J

http://blog.cn-consult.dk/2009/03/bridging-javautillogging-to-slf4j.html

see the logger_error error throw in GenericExceptionMapper. The slf4j
code in the TerminationListener is responsible for shifting Jersey logging
into the log4j file. Without it, jersey logging goes to console, not the file,
and then gets lost
also log4j should be set to go to console and file.

You can use the swagger endpoint to see 

