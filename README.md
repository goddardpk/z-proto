# ZPlatform → Alert (Builder) API

Example of how an evolving Alert Builder API is produced.
z-api project: Supplies the following
  Builder: Which wraps the native framework builder
  BuilderPopulator: Seed Builder with content in Payload
  PayLoad: Contains the hierarchical data in generic property key-value format 
  PayLoadFactory: Helps create a PayLoad from any object
  Client: Client proxy to build service and test environment

z-service project: The z-api implementation classes

zplat-proto-zrpe1 project:
  Revision 1 of Zrpe protocol (derived from ecore/genmodel)

zplat-proto-zrpe2 project:
  Revision 2 of Zrpe protocol (added description2 to alert record)

AlertClient1 has a main method to demonstrate client running against Zrpe1 protocol
AlertClient2 has a main method to demonstrate a client running against Zrpe2 protocol and still using the older zrep1 process to seed original content first.

the maven build will auto-generate the Avro artifacts and the client is protected from directly dealing with Avro internal details. 

