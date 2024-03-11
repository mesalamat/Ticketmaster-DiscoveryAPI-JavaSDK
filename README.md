
# Ticketmaster-DiscoveryAPI-JavaSDK

Simple Java Implementation of the [Ticketmaster DiscoveryAPI](https://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2/) using [OkHttp](https://square.github.io/okhttp/) & [GSON](https://github.com/google/gson) as well as Lombok for handling boilerplate code


## Usage/Examples


### Creating a new JavaTicketMaster Instance


If you want to use the standard "Ticketmaster JavaSDK" User-Agent
```java
JavaTicketMaster javaTicketMaster = new JavaTicketMaster("YourApiKey");
```
or if you want to use a Custom User Agent
```java
JavaTicketMaster javaTicketMaster = new JavaTicketMaster("YourApiKey", "CustomUserAgent");
```

## Handling Requests
The Layout of all Requests is basically the same. Some simply have more Parameters than others. All Parameters for each Requests can be found here: [Ticketmaster API Documentation
](https://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2/#overview)

To create a new Request, you have to create a new Instance of it via the Constructor

Event Search Request Generic Example:
```java

//You will need an already defined JavaTicketMaster Instance
EventsSearchRequest request = new EventsSearchRequest(javaTicketMaster);

//You can then add Parameters by using this method:
request.addParameter(EventsSearchRequest.EventRequestParameter.SEGMENT_NAME, "Music");
//This adds a new Parameter to the Search Query of the Request. It will now only search for Music Events

//Lets add another Parameter just to show how it works
request.addParameter(EventsSearchRequest.EventRequestParameter.SIZE, 3);
//Adding an Incompatible Type to the Parameter will throw an IncompatibleEventParameterException 

//To execute the request you can call:
EventsResponse response = request.request();
/*This will return an EventsResponse with all events that were found
The Search Responses all are Paged Responses, 
which means they can contain Information about Pages & Page Sizes. By using the EventRequestParameterPAGE you can specify the page based on your PageInfo.
It can be retrieved via:
*/
response.getPageInfo();

```

Event Details Example:
```java

//You will need an already defined JavaTicketMaster Instance & an event ID(this is not the name)
EventDetailsResponse response = new EventDetailsRequest(javaTicketMaster, eventId).request();
//You can also pack responses & requests in "one-liners"
```


### Request Types:

Search Requests  | Detail Requests
------------- | -------------
EventsSearchRequest | ClassificationDetailsRequest
VenueSearchRequest | EventDetailsRequest
ClassificationsSearchRequest| EventImagesRequest
AttractionsSearchRequest | GenreDetailsRequest
|                        | SegmentDetailsRequest
|                        | SubgenreDetailsRequest
|                        | VenueDetailsRequest

The main difference between them are as the name suggests, the Detail Requests are to retrieve details about certain Objects, rather than broad search. It is also why they only require "ID" as parameter.

## Handling Ratelimit

This Implementation also contains a Handler for Ticketmaster's Ratelimiting.
Every JavaTicketMaster Instance contains a Ratelimit Object which can be accessed as follows:

```java
JavaTicketMaster javaTicketMaster = new JavaTicketMaster("YourApiKey");
Ratelimit rateLimit = javaTicketMaster.getRateLimit();
```

The Ratelimit Object is pretty straightforward, all Ratelimiting is handled through the Request Objects, so no additional work is required.

You can retrieve the following variables:

```java
Ratelimit rateLimit = javaTicketMaster.getRateLimit();

rateLimit.getMaxRequests(); //The amount of maximum Requests until the Reset

rateLimit.getRemainingRequests(); //The amount of allowed Requests that remain until the Reset

rateLimit.getRateLimitOver(); //The amount of exceeded Requests (Above the Max Request Limit)

rateLimit.getRateLimitReset(); //The date for when the Ratelimit Resets

```

