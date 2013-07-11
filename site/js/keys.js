Embriak.KeysRoute = Ember.Route.extend({
    model: function(bucket) {
        return Embriak.BucketModel.find(bucket.bucket_id);
    }
});

Embriak.KeysController = Ember.ObjectController.extend({
    needs: 'keysKey',
    sortProperties: ['id'],

    deleteKey: function(key) {
        var bucket = this.get('content');
        console.log('deleting key: ' + key.get('id'));
        var promise = key.deleteRecord();
        promise.then(function() {
            console.log(bucket.get('keys').toString());
            var index = bucket.get('keys').indexOf(key);
            console.log('deleting item at index: ' + index);
            bucket.get('keys').removeAt(index);
        });
    }
});

Embriak.KeysLiView = Ember.View.extend({
    tagName: 'li',
    template: Ember.Handlebars.compile('' +
        '<button class="btn btn-mini btn-danger" style="margin-right: 5px; float: left;" {{action deleteKey this}}>X</button>' +
        '{{#linkTo keys.key this}}{{keyName}}{{/linkTo}}'),

    classNameBindings: 'active',

    active: function() {
        return this.get('content') === this.get('controller.controllers.keysKey.content');
    }.property('controller.controllers.keysKey.content.id')
});