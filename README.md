Generic Builder API

Example of how an evolving Alert Schema and a generic Builder API can produce a valid Object instance.

Z-API is free of 3rd party frameworks

Z-API project: Supplies the following

  Builder: Which wraps the native framework builder
  
  BuilderPopulator: Seed Builder with content in Payload
  
  PayLoad: Contains the hierarchical data in generic property key-value format 
  
  PayLoadFactory: Helps create a PayLoad from any object
  
  Client: Client proxy to build service and test environment

Z-SERVICE project: The z-api implementation classes

Z-PLAT-PROTO-ZRPE1 project:
  Revision 1 of Zrpe protocol (which can be derived from anything: in this case, transformed from ecore/genmodel)

Z-PLAT-PROTO-ZRPE2 project:
  Revision 2 of Zrpe protocol (Exammple of Schema change: added description2 to alert record)

Toy remoting service:
SpringAlertClient1 has a main method to demonstrate (Spring) client running against Zrpe1 protocol
SpringAlertClient2 has a main method to demonstrate a client running against Zrpe2 protocol and still using the older zrep1 process to seed original content first.

The maven build will auto-generate the Avro (Java) artifacts and the remote client is protected from directly dealing with Avro internal details. The Avro.avpr artifacts can be a produced by another upstream process but in this example you can modify to your delight.

The point is for the a client to essentailly request a service to build an Object by simply providing
1. A Generic PayLoad (Map)
2. A Type of object
3. A Type of builder.

In a nutshell...
    public T create(PayLoad payload) throws BuilderServiceException {
        return service.build(payload, builder);
    }
    
If you look carefully look at project structure is safely partitions into:
1. A Safe Protocol Stack free from any framework artifacts (z-api,z-service,z-extract)
2. A 'House of Cards' Stack which is the versioned artifacts 
3. A service framework stack (Spring stuff for initial POC)

I jokingly say 'House of Cards' because protocol revisions will rest on prior revisions if protocol is to be backwards compatible. If you review the BuilderPopulators, you can see how revised populators can leverage previous revisons of older builder populators. Point is, if a schema is backwards compatible, then newer implementation can keep using using legacy populator. This facility keeps the system honest to system contraints across revisions.

Client API will have an out of the box way to 'test()' its environment by throwing a test payload into its' environment.
This test ping process can also be used to regression test against multiple target revisions.

The House of Cards also has a mavenized process to draw from the internal serialization framework's meta-data (in this case *.avpro: Avro Protocol Artifacts to generate Java source) These Java artifacts go to z-proto/z-proto-revisions/z-proto-revision1/z-generated-zrpe-source1target/generated-sources.

The revision pathing is pretty verbose (by nesting revisions in this way)...

proj/proj-revisions/proj-revision1

This (verbose) 'style' keeps the structure orthogonal and manageable over time. This structure will also maintain revisions as immutable locations for their correspoding schema revisions.
I also attempted to implement this pattern with service implementations as well so the cognitive load is reduced for developer when trying to navigate revision details.

The basic design attempts to simplify some of the complexities in supporting a data extraction pipeline without coupling an implementation to any particular technology. In fact, the service api is agnostic to the internal meachanics on the objects it is creating and consuming.

I aslo tried to follow a strict naming style for revisioned artifacts by appending their revision to their filename.
This convention will clearly demarcate that a coded artifact and avoid confusions about where an artifact belongs.
The idea is that a generic utility can seemlessly derive a revision of an artifact by simply looking at its name and grabbing the revision number off the end of the filename.

This design also treats a schema as as an external entity seperated from any kind of data-store implementation.
This capacity allows better integrity to the extraction process by not coupling integration to back-end data-store semantics.
By seperating schema creationg and validation as a distinct process, the extraction process can reduce scope of concerns to first verifying if a PayLoad can be safely marshalled into a given Schema structure before the payload is actually committed.
This safer behavior will afford system to be more consistent in its capacity to transfer state from one source system to possibly many target systems. To 'wait' and attempt a commit to a target system to only find some contraint violation  is not a satisfactory process - especially when the direction of catalog extraction is to support ad-hoc changeset saves and extractions.

The ultimate objective of having this safer extraction flow is to formualate a process where a catalog 'changeset' can transition into an 'exportable changeset'. In other words, a changeset save  action (internally in a catalog system) can be identified as exportable to a set of target systems. This inital project makes no claims that target system is in a consistent or valid state. The degree of safety in this current design is just around identifying if a given 'payload' can be marshalled to a given target schema revision. Once this phase has been completed, next phase is determining if the changes align with target system state.

In reviewing Zrpe1.avpr and Zrpe2.avpro the package naming convention is to keep the object names the same but append a revision number to their avro package. So zrpe1.avpr will be 'com.zafin.models.avro1'. This convention allow client code to appear more agnotic to model revisions although imports will clearly indicate that they allign with their corresponding  revision:

Config2:
public class AlertSpringConfig2<T,B> 
Imports com.zafin.models.avro2.Alert;

While Config1:
public class AlertSpringConfig1<T, B> 
Imports com.zafin.models.avro1.Alert

This project originally used a small Scala project to tranlsate ecore/genmodel artifacts to zrep1.avpr
This process of translating ecore artifacts is currently not executing in this project. This could be re-instated but
the point of this POC is to allow reviewers to inspect how an avro protocol can be modified and tested.

Another high value phase is to implement other remoting technologies like Spring reactive to review high volume transations using circuit breaking behavior. This should just involve only minor additions to the Spring core project to verify that this solution is honest to its claim that it is agnostic to service framework implementations. Another possible hook is just integrationg existing extraction process and get some performance comparisons on newer (modern) IDL solutions.  
