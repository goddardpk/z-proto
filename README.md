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

SpringAlertClient1 has a main method to demonstrate (Spring) client running against Zrpe1 protocol
SpringAlertClient2 has a main method to demonstrate a client running against Zrpe2 protocol and still using the older zrep1 process to seed original content first.

The maven build will auto-generate the Avro (Java) artifacts and the remote client is protected from directly dealing with Avro internal details. 

The point is for the a client to essentailly request a service to build an Object by simply providing
1. A Generic PayLoad (Map)
2. A Type of object
3. A Type of builder.

In a nutshell...
    public T create(PayLoad payload) throws BuilderServiceException {
        return service.build(payload, builder);
    }
    
If you look carefully at project structure is safely partitions into:
1. A Safe Protocol Stack free from any framework artifacts (z-api,z-service,z-extract)
2. A 'House of Cards' Stack which is the versioned artifacts 
3. A service framework stack (Spring stuff for initial POC)

I jokingly say 'House of Cards' because protocol revisions will rest on prior revisions if protocol is to be backwards compatible.

Client API will have an out of the box way to 'test()' its environment by throwing a test payload into its' environment.
This test ping process can also be used to regression test against multiple target revisions.

The House of Cards also has a mavenized process to draw from the internal serialization framework's meta-data (in this case *.avpro: Avro Protocol Artifacts to generate Java source)

The basic design attempts to simplify some of the complexities in supporting a data extraction pipeline without coupling an implementation to any particular technology. In fact, the service api is agnostic to the internal meachanics on the objects it is creating and consuming.

This design also treats a schema as as an external entity seperated from any kind of data-store implementation.
This capacity allows better integrity to the extraction process by not coupling integration to back-end data-store semantics.
By seperating schema creationg and validation as a distinct process, the extraction process can reduce scope of concerns to first verifying if a PayLoad can be safely marshalled into a given Schema structure before the payload is actually committed.
This safer behavior will afford system to be more consistent in its capacity to transfer state from one source system to possibly many target systems. To 'wait' and attempt a commit to a target system to only find some contraint violation  is not a satisfactory process - especially when the direction of catalog extraction is to support ad-hoc changeset saves and extractions.

