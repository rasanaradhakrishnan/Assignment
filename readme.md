**Table of Contents**

- [Architecture](#markdown-header-architecture)
- [Paradigms and Patterns](#markdown-header-paradigms-and-patterns)
- [Project Organisation](#markdown-header-project-organisation)
- [Testing](#markdown-header-testing)
	- [Unit Testing](#markdown-header-unit-testing)
- [Dependencies](#markdown-header-dependencies)


## Architecture

The app is structured following the Clean Architecture architectural pattern.

The UI, presentation logic, business logic and data management logic are clearly separated in layers.
 Each layer is allowed to communicate only with the layer above and the layer below.
 All the layers communicate by exchanging the same Model objects, which are simple POKOs representing the involved real-world objects.


## Paradigms and Patterns

The design patterns includes:

 - Dependency Inversion pattern in order to achieve low coupling between the components and to simplify testing.
 - Model-View-Presenter pattern to achieve separation between presentation logic and domain logic.

## Project Organisation

The project is organised in three layers: Data, Domain and Presentation.

The Domain layer contains all of the classes that represent the real life objects managed by the app, classified as Models.
 These objects are passed between layers during internal communication. All of the models in this layer are POKOs.
 They are completely decoupled from the Android framework and have no responsibilities besides being a data representation.

The Data layer contains all of the objects responsible for storing and retrieving data used by the app.
 This includes: service APIs, local storage and databases. All of the objects that provide data implement the relevant Repository interfaces provided by the Domain layer.

The Presentation layer contains UI and Presentation logic, and it's organised following the Model-View-Presenter (MVP) pattern.


### Unit Testing

The majority of the unit tests are implemented in the Presentation and Domain layers of the app,
specifically targeting application business logic that is completely independent from Android framework dependencies.
 Tests in these areas are thus deemed to be of high value in preventing regressions.

UseCase classes should be annotation with ```@Mockable``` which defaults the class and all its functions to open so that the entire class is mockable through Mockito for unit testing.
This uses the underlying All-Open Kotlin framework library.
 UseCase classes do not need to be unit tested for simple proxy calls; i.e, the data is a straight pass through with no transformation from the data-layer response to the presentation layer.
Such UseCase classes should be added to the exclude list within the test.gradle file.

If the data is modified in some way or business rules are applied, then the UseCase needs to have unit tests written for it. If the class we are writing test cases for is in the exclude list from the report. We need to remove that class from the exclude list. See the excludes array within test.gradle for more details.

Data layer unit tests are the least common as most data layer classes are services which rely heavily on system frameworks to perform API or database calls
which are redundant to test and provide no benefit in proving are working. However any mapper classes written that transform from/to data-domain models must be tested.
We can write PACT tests to check the contrcat between servioce and the down stream API' s

All tests are located under the app/src/test/java folder.

Currently I have not written any test cases due to timeline constraints, but the above mentioned was my plan to do it.

## Dependencies

See app/build.gradle file for complete list of dependencies.
