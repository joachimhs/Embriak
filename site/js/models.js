Embriak.Adapter = Ember.RESTAdapter.extend({
    findMany: null
});


Embriak.BucketModel = Ember.Model.extend({
    id: Ember.attr(),
    bucketName: Ember.attr(),
    keys: Ember.hasMany("Embriak.KeyModel", {key: 'keyIds'})
});

Embriak.BucketModel.rootKey = 'bucket';
Embriak.BucketModel.collectionKey = 'buckets';
Embriak.BucketModel.adapter = Embriak.Adapter.create();
Embriak.BucketModel.url = "/json/buckets";


Embriak.KeyModel = Ember.Model.extend({
    id: Ember.attr(),
    keyName: Ember.attr(),
    value: Ember.attr()
});

Embriak.KeyModel.rootKey = 'keyValue';
Embriak.KeyModel.collectionKey = 'keyValues';
Embriak.KeyModel.adapter = Embriak.Adapter.create();
Embriak.KeyModel.url = "/json/keyValue";