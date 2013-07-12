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

##Installation

 - In the backend directory run ``mvn install``
 - Upload ``embriak-0.0.1.jar`` to a directory on you server
 - Copy the contents of the ``site`` directory to a directory on your server
 - Copy ``config.properties`` to the same directory as ``embriak-0.0.1.jar``
 - Edit ``config.properties`` to reflect the directory structure created above, as well as the hostname of your Riak instance

In order to start Riak, use ``java -jar embriak-0.0.1.jar``, or write a startup script that executes the jar file

Depending on your cluster size, increase Riaks memory by setting ``-Xmx1g`` (or higher depending on your requirements). 

##License

Licensed under the Apache Software License 2.0, see the LICENSE file

##Contributors

Joachim Haagen Skeie (http://haagen-software.no)
