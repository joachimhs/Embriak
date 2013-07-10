Embriak.BucketsRoute = Ember.Route.extend({
    model: function() {
        return Embriak.BucketModel.findAll();
    }
});

Embriak.BucketsController = Ember.ArrayController.extend({
    sortProperties: ['id']
});