Embriak.KeysRoute = Ember.Route.extend({
    model: function(bucket) {
        return Embriak.BucketModel.find(bucket.bucket_id);
    }
});

Embriak.KeysController = Ember.ObjectController.extend({
    needs: 'keysKey',
    sortProperties: ['id']
});

Embriak.KeysLiView = Ember.View.extend({
    tagName: 'li',
    template: Ember.Handlebars.compile('{{#linkTo keys.key this}}{{keyName}}{{/linkTo}}'),

    classNameBindings: 'active',

    active: function() {
        return this.get('content') === this.get('controller.controllers.keysKey.content');
    }.property('controller.controllers.keysKey.content.id')
});