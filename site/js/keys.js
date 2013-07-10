Embriak.KeysRoute = Ember.Route.extend({
    model: function(bucket) {
        return Embriak.BucketModel.find(bucket.bucket_id);
    }
});

Embriak.KeysController = Ember.ObjectController.extend({
    sortProperties: ['id']
});