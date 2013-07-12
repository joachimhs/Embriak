#Embriak
A Data-browser for Riak written in Ember.js and Netty

Currently the following features are supported:

 - List all buckets in the database
 - List all keys within a bucket
 - View Key/Value for a key
 - Edit a Key/Value
 - Delete a Key
 - Configuration of the host Riak is running on (config.properties)

##Future functionality

 - Inserting new Keys into a (new) bucket
 - Support for lazy loading Keys (they are now eagerly fetched which is an issue when there are many keys inside a bucket)
 - User login, so that Embriak can be exposed outside of the LAN, and accessable via the internet

##Screenshots

<b>Loading indicators while Buckets and Keys are loading:</b><br />
<img src="http://stuff.haagen.name/embriak1.png" /><br />

<b>Buckets and Keys loaded, Key/Value shown:</b><br />
<img src="http://stuff.haagen.name/embriak2.png" /><br />

##License

Licensed under the Apache Software License 2.0, see the LICENSE file

##Contributors

Joachim Haagen Skeie (http://haagen-software.no)
