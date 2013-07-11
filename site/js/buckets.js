Embriak.BucketsRoute = Ember.Route.extend({
    model: function() {
        return Embriak.BucketModel.findAll();
    }
});

Embriak.BucketsController = Ember.ArrayController.extend({
    needs: ['keys'],
    sortProperties: ['id']
});

Embriak.BucketLiView = Ember.View.extend({
    tagName: 'li',
    template: Ember.Handlebars.compile('{{#linkTo keys this}}{{id}}{{/linkTo}}'),

    classNameBindings: 'active',

    active: function() {
        return this.get('content.id') === this.get('controller.controllers.keys.content.id');
    }.property('controller.controllers.keys.content.id')
});
